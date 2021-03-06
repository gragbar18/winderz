package com.via.android.winderzproject.ui;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.via.android.winderzproject.R;
import com.via.android.winderzproject.data.Session;

import java.util.List;

public class SessionAdapter extends RecyclerView.Adapter<SessionAdapter.ViewHolder> {

    private final List<Session> mSessions;
    private final OnListItemClickListener mOnListItemClickListener;
    SessionDataViewModel sessionDataViewModel;

    SessionAdapter(List<Session> sessions, OnListItemClickListener listener, SessionDataViewModel viewModel) {
        mSessions = sessions;
        mOnListItemClickListener = listener;
        sessionDataViewModel = viewModel;
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.session_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SessionAdapter.ViewHolder viewHolder, int position) {
        Session session = mSessions.get(position);

        viewHolder.title.setText(session.getTitle());
        viewHolder.windOrientation.setText(session.getWindOrientation());
        viewHolder.windSpeed.setText(String.valueOf(session.getWindSpeed()));
        viewHolder.date.setText(session.getDate());
        viewHolder.hour.setText(session.getHour());
        viewHolder.waveSize.setText(session.getWaveSize());
        viewHolder.wavePeriod.setText(String.valueOf(session.getWavePeriod()));

        if (session.getMinSession() < 10) {
            viewHolder.minSession.setText("0" + String.valueOf(session.getMinSession()));
        } else {
            viewHolder.minSession.setText(String.valueOf(session.getMinSession()));
        }
        if (session.getMinSession() < 10) {
            viewHolder.hourSession.setText("0" + String.valueOf(session.getHourSession()));
        } else {
            viewHolder.hourSession.setText(String.valueOf(session.getHourSession()));
        }


        switch (session.getWindOrientation()) {
            case "North":
                viewHolder.windOrientation.setText("N");
                break;
            case "Northeast":
                viewHolder.windOrientation.setText("NE");
                break;
            case "East":
                viewHolder.windOrientation.setText("E");
                break;
            case "Southeast":
                viewHolder.windOrientation.setText("SE");
                break;
            case "South":
                viewHolder.windOrientation.setText("S");
                break;
            case "Southwest":
                viewHolder.windOrientation.setText("SW");
                break;
            case "West":
                viewHolder.windOrientation.setText("W");
                break;
            case "Northwest":
                viewHolder.windOrientation.setText("NW");
                break;
            default:
                viewHolder.windOrientation.setText("Not Specified");
                viewHolder.windOrientation.setTextSize(2, 15);
                break;
        }


        if (session.isFavorite()) {
            viewHolder.favoriteCheckbox.setChecked(true);
        }
        //Pass the session object to the viewholder
        viewHolder.session = session;

    }

    @Override
    public int getItemCount() {
        return mSessions.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener, OnMapReadyCallback {

        TextView title;
        TextView description;
        TextView windOrientation;
        TextView windSpeed;
        TextView date;
        TextView hour;
        TextView hourSession;
        TextView minSession;
        TextView waveSize;
        TextView wavePeriod;
        CheckBox favoriteCheckbox;
        Button popupButton;
        Session session;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.session_title);
            windOrientation = itemView.findViewById(R.id.session_windOrientation);
            windSpeed = itemView.findViewById(R.id.session_windSpeed);
            date = itemView.findViewById(R.id.session_date);
            hour = itemView.findViewById(R.id.session_hour);
            hourSession = itemView.findViewById(R.id.hourSession);
            minSession = itemView.findViewById(R.id.minSession);
            waveSize = itemView.findViewById(R.id.session_waveSize);
            wavePeriod = itemView.findViewById(R.id.session_wavePeriod);
            favoriteCheckbox = itemView.findViewById(R.id.favoriteCheckbox);
            popupButton = itemView.findViewById(R.id.popupButton);
            popupButton.setOnClickListener(this::showPopup);
            itemView.setOnClickListener(this);
            favoriteCheckbox.setOnClickListener(view -> sessionDataViewModel.updateFavoriteSession(session.getKey(), favoriteCheckbox.isChecked()));

            MapView map = (MapView) itemView.findViewById(R.id.embedded_map);
            map.onCreate(null);
            map.onResume();
            map.getMapAsync(this);

        }

        // Get a handle to the GoogleMap object and display marker.
        @Override
        public void onMapReady(GoogleMap googleMap) {
            if (session.getLat() != null && session.getLng() != null) {
                LatLng location = new LatLng(session.getLat(), session.getLng());
                googleMap.addMarker(new MarkerOptions()
                        .position(location)
                        .icon(BitmapDescriptorFactory.defaultMarker(329.9f))
                        .title(session.getTitle()));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 10));
            }
        }

        public void showPopup(View view) {
            PopupMenu popup = new PopupMenu(view.getContext(), view);
            popup.setOnMenuItemClickListener(this);
            popup.inflate(R.menu.choice_menu);
            popup.show();
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            Context context = title.getContext();
            switch (item.getItemId()) {
                case R.id.modify:
                    Intent intent = new Intent(context, UpdateSessionActivity.class);
                    sessionDataViewModel.saveCurrentSession(session);
                    context.startActivity(intent);
                    return true;
                case R.id.delete:
                    sessionDataViewModel.deleteSession(session.getKey());
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onClick(View v) {
            mOnListItemClickListener.onListItemClick(getAdapterPosition());
        }
    }

    public interface OnListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }


}

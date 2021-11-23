package com.via.android.winderzproject.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.via.android.winderzproject.R;
import com.via.android.winderzproject.data.Session;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class SessionAdapter extends RecyclerView.Adapter<SessionAdapter.ViewHolder> {

    private final List<Session> mSessions;
    private final OnListItemClickListener mOnListItemClickListener;

    SessionAdapter(List<Session> sessions, OnListItemClickListener listener){
        mSessions = sessions;
        mOnListItemClickListener = listener;
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.session_list_item, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SessionAdapter.ViewHolder viewHolder, int position) {
        Session session = mSessions.get(position);
        viewHolder.title.setText(session.getTitle());
        viewHolder.description.setText(session.getDescription());
        viewHolder.windOrientation.setText(session.getWindOrientation());
        viewHolder.windSpeed.setText(String.valueOf(session.getWindSpeed()) + " knots");
        viewHolder.date.setText(session.getDate());
        viewHolder.hour.setText(session.getHour());
        viewHolder.hourSession.setText(String.valueOf(session.getHourSession()) + " hours");
        viewHolder.minSession.setText(String.valueOf(session.getMinSession()) + " min");
        viewHolder.waveSize.setText(session.getWaveSize());
        viewHolder.wavePeriod.setText(String.valueOf(session.getWavePeriod()) + " sec btw waves");
        if(session.getFavorite()) {
            viewHolder.favoriteCheckbox.setChecked(true);
        }
        //Pass the session object to the viewholder
        viewHolder.session = session;

    }

    @Override
    public int getItemCount() { return mSessions.size();}

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

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

        ViewHolder(View itemView){
            super(itemView);
            title=itemView.findViewById(R.id.session_title);
            description=itemView.findViewById(R.id.session_description);
            windOrientation=itemView.findViewById(R.id.session_windOrientation);
            windSpeed=itemView.findViewById(R.id.session_windSpeed);
            date=itemView.findViewById(R.id.session_date);
            hour=itemView.findViewById(R.id.session_hour);
            hourSession=itemView.findViewById(R.id.hourSession);
            minSession=itemView.findViewById(R.id.minSession);
            waveSize=itemView.findViewById(R.id.session_waveSize);
            wavePeriod=itemView.findViewById(R.id.session_wavePeriod);
            favoriteCheckbox = itemView.findViewById(R.id.favoriteCheckbox);
            popupButton = itemView.findViewById(R.id.popupButton);
            popupButton.setOnClickListener(this::showPopup);
            itemView.setOnClickListener(this);
            favoriteCheckbox.setOnClickListener(view -> {
                HomeFragment.updateFavoriteSession(session.getKey(), favoriteCheckbox.isChecked());
            });
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
            switch (item.getItemId()){
                case R.id.modify :
                    Intent intent = new Intent(context, UpdateSessionActivity.class);
                    intent.putExtra("session", session);
                    context.startActivity(intent);
                    return true;
                case R.id.delete :
                    HomeFragment.deleteSession(session.getKey());
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

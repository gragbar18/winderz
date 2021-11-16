package com.via.android.winderzproject.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.via.android.winderzproject.R;
import com.via.android.winderzproject.data.Session;

import java.util.ArrayList;
import java.util.List;

public class SessionAdapter extends RecyclerView.Adapter<SessionAdapter.ViewHolder> {

    private List<Session> mSessions;
    final private OnListItemClickListener mOnListItemClickListener;

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
        viewHolder.title.setText(mSessions.get(position).getTitle());
        viewHolder.description.setText(mSessions.get(position).getDescription());
        viewHolder.windOrientation.setText(mSessions.get(position).getWindOrientation());
        viewHolder.windSpeed.setText(mSessions.get(position).getWindSpeed());
    }

    @Override
    public int getItemCount() { return mSessions.size();}

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView title;
        TextView description;
        TextView windOrientation;
        TextView windSpeed;

        ViewHolder(View itemView){
            super(itemView);
            title=itemView.findViewById(R.id.session_title);
            description=itemView.findViewById(R.id.session_description);
            windOrientation=itemView.findViewById(R.id.session_windOrientation);
            windSpeed=itemView.findViewById(R.id.session_windSpeed);
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

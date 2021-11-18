package com.via.android.winderzproject.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.via.android.winderzproject.R;
import com.via.android.winderzproject.data.Session;

import org.w3c.dom.Text;

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
        viewHolder.date.setText(mSessions.get(position).getDate());
        viewHolder.hour.setText(mSessions.get(position).getHour());
        viewHolder.time.setText(mSessions.get(position).getTime());
        viewHolder.waveSize.setText(mSessions.get(position).getWaveSize());
        viewHolder.waveFrequency.setText(mSessions.get(position).getWaveFrequency());


    }

    @Override
    public int getItemCount() { return mSessions.size();}

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView title;
        TextView description;
        TextView windOrientation;
        TextView windSpeed;
        TextView date;
        TextView hour;
        TextView time;
        TextView waveSize;
        TextView waveFrequency;

        ViewHolder(View itemView){
            super(itemView);
            title=itemView.findViewById(R.id.session_title);
            description=itemView.findViewById(R.id.session_description);
            windOrientation=itemView.findViewById(R.id.session_windOrientation);
            windSpeed=itemView.findViewById(R.id.session_windSpeed);
            date=itemView.findViewById(R.id.session_date);
            hour=itemView.findViewById(R.id.session_hour);
            time=itemView.findViewById(R.id.session_time);
            waveSize=itemView.findViewById(R.id.session_waveSize);
            waveFrequency=itemView.findViewById(R.id.session_waveFrequency);

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

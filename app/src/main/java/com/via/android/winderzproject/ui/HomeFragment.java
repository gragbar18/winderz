package com.via.android.winderzproject.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.via.android.winderzproject.R;
import com.via.android.winderzproject.data.Session;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements SessionAdapter.OnListItemClickListener {

    static SessionDataViewModel sessionDataViewModel;
    RecyclerView mSessionList;
    SessionAdapter mSessionAdapter;
    List<Session> displayedSessions = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionDataViewModel = new ViewModelProvider(this).get(SessionDataViewModel.class);
        sessionDataViewModel.init();

        mSessionAdapter = new SessionAdapter(displayedSessions,this);
        sessionDataViewModel.getSessions().observe(this, sessions -> {
            //Add all the sessions in our list that is displayed
            displayedSessions.clear();
            displayedSessions.addAll(sessions);
            mSessionAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        mSessionList = getView().findViewById(R.id.rv);

    }

    @Override
    public void onPause() {
        super.onPause();
        displayedSessions.clear();
    }

    @Override
    public void onResume() {
        super.onResume();
        mSessionList.setAdapter(mSessionAdapter);

        mSessionList.hasFixedSize();
        mSessionList.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Session session = displayedSessions.get(clickedItemIndex);
        saveCurrentSession(session);
        Intent intent = new Intent(this.getContext(), DetailsActivity.class);
        this.getContext().startActivity(intent);
    }

    public static void deleteSession(String keySession){
        sessionDataViewModel.deleteSession(keySession);
    }

    public static void updateFavoriteSession(String keySession, Boolean isChecked){
        sessionDataViewModel.updateFavoriteSession(keySession, isChecked);
    }

    public static void saveCurrentSession(Session session) {
        Log.d("test", "session saved from Home Fragment " + session.toString());
        sessionDataViewModel.saveCurrentSession(session);
    }
}
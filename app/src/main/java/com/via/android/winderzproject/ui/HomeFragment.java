package com.via.android.winderzproject.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.via.android.winderzproject.R;
import com.via.android.winderzproject.data.Session;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class HomeFragment extends Fragment implements SessionAdapter.OnListItemClickListener {

    SessionDataViewModel sessionDataViewModel;
    RecyclerView mSessionList;
    SessionAdapter mSessionAdapter;
    List<Session> displayedSessions = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionDataViewModel = new ViewModelProvider(this).get(SessionDataViewModel.class);
        sessionDataViewModel.init();

        mSessionAdapter = new SessionAdapter(displayedSessions, this, sessionDataViewModel);
        sessionDataViewModel.getSessions().observe(this, this::onDataChanged);

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
        mSessionList.setAdapter(mSessionAdapter);
        mSessionList.hasFixedSize();
        mSessionList.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onPause() {
        super.onPause();
        displayedSessions.clear();
        Log.d("test", "data cleared");
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Session session = displayedSessions.get(clickedItemIndex);
        saveCurrentSession(session);
        Intent intent = new Intent(this.getContext(), DetailsActivity.class);
        this.getContext().startActivity(intent);
    }

    public void saveCurrentSession(Session session) {
        Log.d("test", "session saved from Home Fragment " + session.toString());
        sessionDataViewModel.saveCurrentSession(session);
    }

    private void onDataChanged(List<Session> sessions) {
        Log.d("test", "data changed");
        displayedSessions.clear();
        displayedSessions.addAll(sessions);
        mSessionAdapter.notifyDataSetChanged();
    }
}
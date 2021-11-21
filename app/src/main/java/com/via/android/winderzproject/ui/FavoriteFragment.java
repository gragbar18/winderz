package com.via.android.winderzproject.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.via.android.winderzproject.R;
import com.via.android.winderzproject.data.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FavoriteFragment extends Fragment implements SessionAdapter.OnListItemClickListener {

    static SessionDataViewModel sessionDataViewModel;
    RecyclerView mSessionList;
    SessionAdapter mSessionAdapter;
    List<Session> displayedFavoriteSession = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionDataViewModel=new ViewModelProvider(this).get(SessionDataViewModel.class);
        sessionDataViewModel.init();
        mSessionAdapter = new SessionAdapter(displayedFavoriteSession,this);
        sessionDataViewModel.getSessions().observe(this, sessions -> {
            //Add all the sessions in our list that is displayed
            displayedFavoriteSession.clear();
            for(Session session : sessions){
                if (session.getFavorite()){
                    displayedFavoriteSession.add(session);
                }
            }
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
        mSessionList.setAdapter(mSessionAdapter);

        mSessionList.hasFixedSize();
        mSessionList.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Session session = displayedFavoriteSession.get(clickedItemIndex);
        Toast.makeText(getContext(), session.toString() , Toast.LENGTH_SHORT).show();
    }

    public static void deleteSession(String keySession){
        sessionDataViewModel.deleteSession(keySession);
    }

    public static void updateFavoriteSession(String keySession, Boolean isChecked){
        sessionDataViewModel.updateFavoriteSession(keySession, isChecked);
    }
}



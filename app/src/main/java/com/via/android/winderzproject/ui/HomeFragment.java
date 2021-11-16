package com.via.android.winderzproject.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.via.android.winderzproject.R;
import com.via.android.winderzproject.data.Session;

import java.util.ArrayList;


public class HomeFragment extends Fragment implements SessionAdapter.OnListItemClickListener {

    SessionDataViewModel sessionDataViewModel;
    RecyclerView mSessionList;
    SessionAdapter mSessionAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //sessionDataViewModel=new ViewModelProvider(this).get(SessionDataViewModel.class);



        ArrayList<Session>sessions = new ArrayList<>();
        sessions.add(new Session("Session 1","description 1","20","North","1","10",true));
        sessions.add(new Session("Session 2","description 2","15","North-Ouest","1","10",true));

        mSessionAdapter = new SessionAdapter(/*sessionDataViewModel.getSessions().getValue()*/sessions,this);
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
        int sessionNumber = clickedItemIndex + 1;
        Toast.makeText(getContext(),"Session Number: " + sessionNumber, Toast.LENGTH_SHORT).show();
    }
}
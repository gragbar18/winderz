package com.via.android.winderzproject.ui;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.via.android.winderzproject.R;


public class HomeFragment extends Fragment {
    SessionDataViewModel sessionDataViewModel;
    CardView mSessionList;
    SessionAdapter mSessionAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionDataViewModel=new ViewModelProvider(this).get(SessionDataViewModel.class);
        setContentView(R.layout.fragment_home);

        mSessionList = mSessionList.findViewById();
        mSessionList.hasFixedSize();
        mSessionList.setLayoutManager(new LinearLayoutManager(this));

        mSessionAdapter = new SessionAdapter(R., this);
        mSessionList.setAdapter(mSessionAdapter);
    }


}
package com.via.android.winderzproject.ui;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.via.android.winderzproject.R;
import com.via.android.winderzproject.data.User;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProfileFragment extends Fragment {
    MainActivityViewModel viewModel;
    Button signOutButton;
    TextView firstNameTextView;
    TextView lastNameTextView;
    TextView emailTextView;
    ImageView imageView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
       }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        signOutButton = getView().findViewById(R.id.signOutButton);
        signOutButton.setOnClickListener(view -> viewModel.signOut());

        firstNameTextView = getView().findViewById(R.id.firstName);
        lastNameTextView = getView().findViewById(R.id.lastName);
        emailTextView = getView().findViewById(R.id.email);
        imageView = getView().findViewById(R.id.imageView);

        viewModel.getCurrentUser().observe(this, firebaseUser -> {
            if(firebaseUser != null){
                String[] name = firebaseUser.getDisplayName().split(" ");
                Uri uriImage = firebaseUser.getPhotoUrl();
                firstNameTextView.setText(name[0]);
                lastNameTextView.setText(name[1]);
                emailTextView.setText(firebaseUser.getEmail());

                if (uriImage != null){
                    Glide.with(this).load(uriImage).into(imageView);
                }
            }
        });

    }
}
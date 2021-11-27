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

import com.bumptech.glide.Glide;
import com.via.android.winderzproject.R;

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
        View view = getView();
        signOutButton = view.findViewById(R.id.signOutButton);
        signOutButton.setOnClickListener(v -> viewModel.signOut());

        firstNameTextView = view.findViewById(R.id.firstName);
        lastNameTextView = view.findViewById(R.id.lastName);
        emailTextView = view.findViewById(R.id.email);
        imageView = view.findViewById(R.id.imageView);

        viewModel.getCurrentUser().observe(this, firebaseUser -> {
            if (firebaseUser != null) {
                String firebaseUserName = firebaseUser.getDisplayName();
                String[] name = firebaseUserName != null ? firebaseUserName.split(" ") : new String[0];
                Uri uriImage = firebaseUser.getPhotoUrl();
                firstNameTextView.setText(name[0]);
                if (name.length != 1) //Case if the user entered an username instead of a name and a lastname
                    lastNameTextView.setText(name[1]);
                emailTextView.setText(firebaseUser.getEmail());

                if (uriImage != null) {
                    Glide.with(this).load(uriImage).into(imageView);
                }
            }
        });

    }
}
package com.via.android.winderzproject.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.via.android.winderzproject.R;
import com.via.android.winderzproject.data.Session;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity{
    MainActivityViewModel viewModel;
    BottomNavigationView bottomNavigationView;
    FloatingActionButton addSessionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        checkIfSignedIn();
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setBackground(null);

        NavController navController = Navigation.findNavController(this, R.id.fragmentContainerView);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        //Go to the AddSessionFragment if we click on the plus button
        addSessionButton = findViewById(R.id.addSessionButton);
        addSessionButton.setOnClickListener(v -> navController.navigate(R.id.addSession));
    }

    private void checkIfSignedIn() {
        viewModel.getCurrentUser().observe(this, user -> {
                if (user == null)
                    startLoginActivity();
            }
        );
    }

    private void startLoginActivity() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
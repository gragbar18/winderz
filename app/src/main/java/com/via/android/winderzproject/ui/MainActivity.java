package com.via.android.winderzproject.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.via.android.winderzproject.R;
import com.via.android.winderzproject.data.Session;


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
        addSessionButton.setOnClickListener(v -> {
            navController.navigate(R.id.addSession);
        });
    }

    private void checkIfSignedIn() {
        viewModel.getCurrentUser().observe(this, user -> {
            if (user == null)
                starLoginActivity();
            }
        );
    }

    private void starLoginActivity() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
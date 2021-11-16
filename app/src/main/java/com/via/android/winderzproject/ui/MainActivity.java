package com.via.android.winderzproject.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.via.android.winderzproject.R;


public class MainActivity extends AppCompatActivity {
    MainActivityViewModel viewModel;
    BottomNavigationView bottomNavigationView;
    FloatingActionButton addSessionButton;
    Button signOutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        checkIfSignedIn();
        setContentView(R.layout.activity_main);

        signOutButton = findViewById(R.id.signOutButton);
        signOutButton.setOnClickListener(v -> {

        });

        bottomNavigationView=findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setBackground(null);

        NavController navController = Navigation.findNavController(this, R.id.fragmentContainerView);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        //Go to the AddSessionFragment if we click on the plus button
        addSessionButton = findViewById(R.id.addSessionButton);
        addSessionButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddSession.class);
            startActivity(intent);
        });
    }

    private void checkIfSignedIn() {
        viewModel.getCurrentUser().observe(this, user -> {
            if(user != null) {
                Toast.makeText(this, "Welcome " + user.getDisplayName(), Toast.LENGTH_SHORT).show();
            }else
                starLoginActivity();
        });
    }

    private void starLoginActivity() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    public void signOut(View view) {
        viewModel.signOut();
    }
}
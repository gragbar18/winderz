package com.via.android.winderzproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FloatingActionButton addSessionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView=findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setBackground(null);

        /*AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.homeFragment, R.id.mapFragment, R.id.addSessionFragment, R.id.favoriteFragment, R.id.profileFragment)
                .build();*/

        NavController navController = Navigation.findNavController(this, R.id.fragmentContainerView);
        //App crashes when next line is uncomment
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        //Go to the AddSessionFragment if we click on the plus button
        addSessionButton = findViewById(R.id.addSessionButton);
        addSessionButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddSession.class);
            startActivity(intent);
        });
    }
}
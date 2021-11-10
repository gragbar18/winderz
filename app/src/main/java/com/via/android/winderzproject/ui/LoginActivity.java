package com.via.android.winderzproject.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.via.android.winderzproject.R;

public class LoginActivity extends AppCompatActivity {

    Button buttonToHomeActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        buttonToHomeActivity = findViewById(R.id.buttonToHomeActivity);

        buttonToHomeActivity.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }
}
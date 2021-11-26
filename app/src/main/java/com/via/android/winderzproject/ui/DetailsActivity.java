package com.via.android.winderzproject.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.service.controls.templates.TemperatureControlTemplate;
import android.util.Log;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.via.android.winderzproject.R;
import com.via.android.winderzproject.data.Session;

import java.io.IOException;

public class DetailsActivity extends AppCompatActivity {

    DetailViewModel detailViewModel;
    Session session;

    Button updateFromDetails;
    Button deleteFromDetails;
    Button arrowBackButton;
    CheckBox favoriteCheckboxDetails;


    TextView titleDetails;
    TextView descriptionDetails;
    TextView windSpeedDetails;
    TextView wavePeriodDetails;
    TextView hourSessionDetails;
    TextView minSessionDetails;

    ImageView windOrientationDetails;
    ImageView waveSizeDetails;

    ImageView ImageActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailViewModel = new ViewModelProvider(this).get(DetailViewModel.class);
        setContentView(R.layout.activity_details);

        updateFromDetails = findViewById(R.id.updateFromDetails);
        deleteFromDetails = findViewById(R.id.deleteFromDetails);
        arrowBackButton = findViewById(R.id.arrowBackButton);
        favoriteCheckboxDetails = findViewById(R.id.favoriteCheckboxDetails);

        titleDetails = findViewById(R.id.titleDetails);
        descriptionDetails = findViewById(R.id.descriptionDetails);
        windSpeedDetails = findViewById(R.id.windSpeedDetails);
        windOrientationDetails = findViewById(R.id.windOrientationDetails);
        wavePeriodDetails = findViewById(R.id.wavePeriodDetails);
        waveSizeDetails = findViewById(R.id.waveSizeDetails);
        hourSessionDetails = findViewById(R.id.hourSessionDetails);
        minSessionDetails = findViewById(R.id.minSessionDetails);
        ImageActivity = findViewById(R.id.ImageActivity);
    }

    @Override
    protected void onStart() {
        super.onStart();
        session = detailViewModel.getCurrentSession();
        Log.d("test", "session claimed by details activity " + session.toString());

        titleDetails.setText(session.getTitle());
        descriptionDetails.setText(session.getDescription());
        windSpeedDetails.setText(String.valueOf(session.getWindSpeed()));
        wavePeriodDetails.setText(String.valueOf(session.getWavePeriod()));
        hourSessionDetails.setText(String.valueOf(session.getHourSession()));
        minSessionDetails.setText(String.valueOf(session.getMinSession()));

        if(session.getUri() !=null) {
            ImageActivity.setImageURI(Uri.parse(session.getUri()));
        }

        switch (session.getWaveSize()){
            case "Flat":
                waveSizeDetails.setImageResource(R.drawable.wave1);
                break;
            case "Choppy":
                waveSizeDetails.setImageResource(R.drawable.wave2);
                break;
            case "Small waves":
                waveSizeDetails.setImageResource(R.drawable.wave3);
                break;
            case "Big waves":
                waveSizeDetails.setImageResource(R.drawable.wave4);
                break;
            default:
                break;
        }
        switch (session.getWindOrientation()){
            case "North":
                windOrientationDetails.setRotation(0);
                break;
            case "Northeast":
                windOrientationDetails.setRotation(45);
                break;
            case "East":
                windOrientationDetails.setRotation(90);
                break;
            case "Southeast":
                windOrientationDetails.setRotation(135);
                break;
            case "South":
                windOrientationDetails.setRotation(180);
                break;
            case "Southwest":
                windOrientationDetails.setRotation(225);
                break;
            case "West":
                windOrientationDetails.setRotation(270);

                break;
            case "Northwest":
                windOrientationDetails.setRotation(315);
                break;
            default:
                break;
        }

        favoriteCheckboxDetails.setChecked(session.getFavorite());
        favoriteCheckboxDetails.setOnClickListener(view -> {
            session.setFavorite(favoriteCheckboxDetails.isChecked());
            HomeFragment.updateFavoriteSession(session.getKey(), favoriteCheckboxDetails.isChecked());
        });

        updateFromDetails.setOnClickListener(view -> {
            Intent intent = new Intent(this, UpdateSessionActivity.class);
            detailViewModel.saveCurrentSession(session);
            startActivity(intent);
        });

        deleteFromDetails.setOnClickListener(view -> {
            detailViewModel.deleteSession(session.getKey());
            finish();
        });

        arrowBackButton.setOnClickListener(view -> {
            finish();
        });
    }
}
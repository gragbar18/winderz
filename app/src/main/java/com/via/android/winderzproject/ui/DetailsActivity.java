package com.via.android.winderzproject.ui;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.via.android.winderzproject.R;
import com.via.android.winderzproject.data.Session;


import java.text.DateFormat;

public class DetailsActivity extends AppCompatActivity implements OnMapReadyCallback {

    DetailViewModel detailViewModel;
    Session session;

    Button updateFromDetails;
    Button deleteFromDetails;
    Button arrowBackButton;
    Button shareActivity;

    CheckBox favoriteCheckboxDetails;

    TextView titleDetails;
    TextView descriptionDetails;
    TextView windSpeedDetails;
    TextView wavePeriodDetails;
    TextView hourSessionDetails;
    TextView minSessionDetails;

    ImageView windOrientationDetails;
    ImageView waveSizeDetails;
    ImageView imageActivity;


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
        imageActivity = findViewById(R.id.ImageActivity);
        shareActivity = findViewById(R.id.shareButton);

        MapView map = (MapView) findViewById(R.id.detail_map);
        map.onCreate(null);
        map.onResume();
        map.getMapAsync(this);

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

        if (session.getMinSession() < 10) {
            minSessionDetails.setText("0" + String.valueOf(session.getMinSession()));
        } else {
            minSessionDetails.setText(String.valueOf(session.getMinSession()));
        }

        if (session.getUri() != null) {
            imageActivity.setImageURI(Uri.parse(session.getUri()));
        }

        switch (session.getWaveSize()) {
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
                waveSizeDetails.setImageResource(R.drawable.nowave);
                break;
        }

        windOrientationDetails.setImageResource(R.drawable.compass);

        switch (session.getWindOrientation()) {
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
                windOrientationDetails.setImageResource(R.drawable.ic_explore_off);
                break;
        }

        favoriteCheckboxDetails.setChecked(session.isFavorite());
        favoriteCheckboxDetails.setOnClickListener(view -> {
            boolean favorite = favoriteCheckboxDetails.isChecked();
            session.setFavorite(favorite);
            detailViewModel.updateFavoriteSession(session.getKey(), favorite);
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

        arrowBackButton.setOnClickListener(view -> finish());

        shareActivity.setOnClickListener(v -> {
            session = detailViewModel.getCurrentSession();

            String message = "Hi ! \nHere is some info about my session on the " + session.getDate() + " in this location : " + "\nLatitude : " + session.getLat() + " and Longitude : " + session.getLng() + "\nI spent " + session.getHourSession() + "h and " + session.getMinSession() + "min on the water with " + session.getWaveSize() + " sea conditions and " + session.getWindSpeed() + " knots of wind coming from the " + session.getWindOrientation() + ".\nSee you soon on the water ;)";


            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, "Hi this is my WP Activity!");
            intent.putExtra(Intent.EXTRA_TEXT, message);
            startActivity(intent);
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (session.getLat() != null && session.getLng() != null) {
            LatLng location = new LatLng(session.getLat(), session.getLng());
            googleMap.addMarker(new MarkerOptions()
                    .position(location)
                    .title(session.getTitle()));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 10));
        }
    }

}
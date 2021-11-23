package com.via.android.winderzproject.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.service.controls.templates.TemperatureControlTemplate;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.via.android.winderzproject.R;
import com.via.android.winderzproject.data.Session;

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
    TextView windOrientationDetails;
    TextView wavePeriodDetails;
    TextView waveSizeDetails;
    TextView hourSessionDetails;
    TextView minSessionDetails;



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
    }

    @Override
    protected void onStart() {
        super.onStart();
        session = detailViewModel.getCurrentSession();
        Log.d("test", "session claimed by details activity " + session.toString());

        titleDetails.setText(session.getTitle());
        descriptionDetails.setText(session.getDescription());
        windSpeedDetails.setText(String.valueOf(session.getWindSpeed()));
        windOrientationDetails.setText(session.getWindOrientation());
        wavePeriodDetails.setText(String.valueOf(session.getWavePeriod()));
        waveSizeDetails.setText(session.getWaveSize());
        hourSessionDetails.setText(String.valueOf(session.getHourSession()));
        minSessionDetails.setText(String.valueOf(session.getMinSession()));


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
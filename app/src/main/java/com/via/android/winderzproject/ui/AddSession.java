package com.via.android.winderzproject.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;

import com.via.android.winderzproject.R;
import com.via.android.winderzproject.data.Session;

import java.text.DateFormat;
import java.util.Date;

public class AddSession extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    /*

    A SUPPRIMER

     */
    AddSessionViewModel addSessionViewModel;

    Spinner windOrientationSpinner;
    EditText titleEdit;
    EditText descriptionEdit;
    EditText windSpeedEdit;
    EditText waveSizeEdit;
    EditText waveFrequencyEdit;
    EditText timeEdit;
    Switch favoriteSwitch;
    ImageView imageView;

    String title;
    String description;
    String windSpeed;
    String windOrientation;
    String waveSize;
    String waveFrequency;
    String date;
    String time;
    Boolean favorite;
    String hour;

    Button cancelButton;
    Button addButton;
    Button photoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addSessionViewModel = new ViewModelProvider(this).get(AddSessionViewModel.class);
        addSessionViewModel.init();
        setContentView(R.layout.fragment_add_session);

        cancelButton = findViewById(R.id.cancelButton);
        addButton = findViewById(R.id.addButton);
        titleEdit = findViewById(R.id.titleEdit);
        descriptionEdit = findViewById(R.id.descriptionEdit);
        windSpeedEdit = findViewById(R.id.WindSpeedEdit);
        waveSizeEdit = findViewById(R.id.waveSizeEdit);
        waveFrequencyEdit = findViewById(R.id.WaveFrequencyEdit);
        favoriteSwitch = findViewById(R.id.Favorite_switch);
        timeEdit = findViewById(R.id.timeEdit);

        //image part
        imageView = findViewById(R.id.activityImage);
        photoButton = findViewById(R.id.takePhotoButton);

        //add part
        addButton.setOnClickListener(view -> {
            title = titleEdit.getText().toString();
            description = descriptionEdit.getText().toString();
            windSpeed = windSpeedEdit.getText().toString();
            waveSize = waveSizeEdit.getText().toString();
            waveFrequency = waveFrequencyEdit.getText().toString();
            time = timeEdit.getText().toString();

            //date part
            Date now = new Date();
            DateFormat dateformatter = DateFormat.getDateInstance(DateFormat.SHORT);
            date = dateformatter.format(now);
            DateFormat timeformatter = DateFormat.getTimeInstance(DateFormat.SHORT);
            hour = timeformatter.format(now);

            if (title != null) {
                addSessionViewModel.addSession(new Session(title, description, windSpeed, windOrientation, waveSize, waveFrequency, favorite, date, hour, time));
                time = timeformatter.format(now);
            }
            finish();
        });

        //delete part
        cancelButton.setOnClickListener(view -> finish());

        //Spinner part
        windOrientationSpinner = findViewById(R.id.WindOrientationSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.windOrientation, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        windOrientationSpinner.setAdapter(adapter);
        windOrientationSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        windOrientation = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        windOrientation = "North";
    }

}
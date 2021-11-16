package com.via.android.winderzproject.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.via.android.winderzproject.R;
import com.via.android.winderzproject.data.Session;

public class AddSession extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    AddSessionViewModel addSessionViewModel;

    Spinner windOrientationSpinner;
    EditText titleEdit;
    EditText descriptionEdit;
    EditText windSpeedEdit;
    EditText waveSizeEdit;
    EditText waveFrequencyEdit;

    String title;
    String description;
    String windSpeed;
    String windOrientation;
    String waveSize;
    String waveFrequency;

    Button cancelButton;
    Button addButton;

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

        //add part
        addButton.setOnClickListener(view -> {
            title = titleEdit.getText().toString();
            description = descriptionEdit.getText().toString();
            windSpeed = windSpeedEdit.getText().toString();
            waveSize = waveSizeEdit.getText().toString();
            waveFrequency = waveFrequencyEdit.getText().toString();
            if(title != null){
                addSessionViewModel.addSession(new Session(title, description, windSpeed, windOrientation, waveSize, waveFrequency, false));
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
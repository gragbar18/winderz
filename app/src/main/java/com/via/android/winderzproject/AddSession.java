package com.via.android.winderzproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;

public class AddSession extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

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

    Button discardButton;
    Button addButton;

    Spinner WindOrientationSpinner;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_session);

        discardButton = findViewById(R.id.discardButton);
        addButton = findViewById(R.id.addButton);

        //add part
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = titleEdit.getText().toString();
                description = descriptionEdit.getText().toString();
                windSpeed = windSpeedEdit.getText().toString();
                waveSize = waveSizeEdit.getText().toString();
                waveFrequency = waveFrequencyEdit.getText().toString();
                finish();
            }
        });

        //discard part
        discardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //Spinner part
        WindOrientationSpinner = findViewById(R.id.WindOrientationSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.windOrientation, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        WindOrientationSpinner.setAdapter(adapter);
        WindOrientationSpinner.setOnItemSelectedListener(this);
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
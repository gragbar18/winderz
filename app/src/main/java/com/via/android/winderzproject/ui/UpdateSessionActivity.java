package com.via.android.winderzproject.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Switch;

import com.via.android.winderzproject.R;
import com.via.android.winderzproject.data.Session;

public class UpdateSessionActivity extends AppCompatActivity {
    UpdateSessionViewModel updateSessionViewModel;

    Spinner windOrientationSpinner;
    EditText titleEdit;
    EditText descriptionEdit;
    EditText windSpeedEdit;
    EditText waveSizeEdit;
    EditText waveFrequencyEdit;
    NumberPicker hourPicker;
    NumberPicker minPicker;
    Switch favoriteSwitch;
    String windOrientation;
    Button cancelButton;
    Button updateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_session);
        updateSessionViewModel = new ViewModelProvider(this).get(UpdateSessionViewModel.class);
        updateSessionViewModel.init();

        updateButton = findViewById(R.id.updateButton);
        cancelButton = findViewById(R.id.cancelButton);

        Session session = (Session) getIntent().getSerializableExtra("session");
        titleEdit = findViewById(R.id.titleEdit);
        titleEdit.setText(session.getTitle());

        descriptionEdit = findViewById(R.id.descriptionEdit);
        descriptionEdit.setText(session.getDescription());

        windSpeedEdit = findViewById(R.id.WindSpeedEdit);
        windSpeedEdit.setText(session.getWindSpeed());

        waveSizeEdit = findViewById(R.id.waveSizeEdit);
        waveSizeEdit.setText(session.getWaveSize());

        waveFrequencyEdit = findViewById(R.id.WaveFrequencyEdit);
        waveFrequencyEdit.setText(session.getWaveFrequency());

        favoriteSwitch = findViewById(R.id.Favorite_switch);
        favoriteSwitch.setChecked(session.getFavorite());

        hourPicker = findViewById(R.id.hourPicker);
        hourPicker.setMinValue(0);
        hourPicker.setMaxValue(23);

        minPicker = findViewById(R.id.minPicker);
        minPicker.setMinValue(0);
        minPicker.setMaxValue(59);

        windOrientationSpinner = findViewById(R.id.WindOrientationSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.windOrientation, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        windOrientationSpinner.setAdapter(adapter);
        windOrientationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                windOrientation = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                windOrientation = "North";
            }
        });

        updateButton.setOnClickListener(view -> {
            session.setTitle(titleEdit.getText().toString());
            session.setDescription(descriptionEdit.getText().toString());
            session.setWindSpeed(windSpeedEdit.getText().toString());
            session.setWaveSize(waveSizeEdit.getText().toString());
            session.setWaveFrequency(waveFrequencyEdit.getText().toString());
            session.setFavorite(favoriteSwitch.isChecked());
            session.setHourSession(hourPicker.getValue()+" hours");
            session.setHourSession(minPicker.getValue() + " minutes");

            if(session.getTitle() != null){
                updateSessionViewModel.updateSession(session);
                finish();
            }
        });

        cancelButton.setOnClickListener(view -> finish());
    }
}
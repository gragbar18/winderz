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
    Spinner waveSizeSpinner;
    EditText titleEdit;
    EditText descriptionEdit;
    NumberPicker hourPicker;
    NumberPicker minPicker;
    NumberPicker windSpeedPicker;
    NumberPicker wavePeriodPicker;
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

        favoriteSwitch = findViewById(R.id.Favorite_switch);
        favoriteSwitch.setChecked(session.getFavorite());

        hourPicker = findViewById(R.id.hourPicker);
        hourPicker.setMinValue(0);
        hourPicker.setMaxValue(23);

        minPicker = findViewById(R.id.minPicker);
        minPicker.setMinValue(0);
        minPicker.setMaxValue(59);

        descriptionEdit = findViewById(R.id.descriptionEdit);
        descriptionEdit.setText(session.getDescription());

        windSpeedPicker = findViewById(R.id.windSpeedPicker);
        windSpeedPicker.setMinValue(0);
        windSpeedPicker.setMaxValue(200);

        wavePeriodPicker = findViewById(R.id.wavePeriodPicker);
        wavePeriodPicker.setMinValue(0);
        wavePeriodPicker.setMaxValue(50);

        windOrientationSpinner = findViewById(R.id.windOrientationSpinner);
        ArrayAdapter<CharSequence> adapterWindOrientation = ArrayAdapter.createFromResource(this, R.array.windOrientation, android.R.layout.simple_spinner_item);
        adapterWindOrientation.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        windOrientationSpinner.setAdapter(adapterWindOrientation);
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

        waveSizeSpinner = findViewById(R.id.waveSizeSpinner);
        ArrayAdapter<CharSequence> adapterWaveSize = ArrayAdapter.createFromResource(this, R.array.waveSize, android.R.layout.simple_spinner_item);
        adapterWaveSize.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        waveSizeSpinner.setAdapter(adapterWaveSize);
        waveSizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                windOrientation = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                windOrientation = "flat";
            }
        });

        updateButton.setOnClickListener(view -> {
            session.setTitle(titleEdit.getText().toString());
            session.setDescription(descriptionEdit.getText().toString());
            session.setFavorite(favoriteSwitch.isChecked());
            session.setWindSpeed(windSpeedPicker.getValue()+" km/h");
            session.setWavePeriod(wavePeriodPicker.getValue()+" wave/s");
            session.setHourSession(hourPicker.getValue()+" hours");
            session.setMinSession(minPicker.getValue() + " minutes");

            if(session.getTitle() != null){
                updateSessionViewModel.updateSession(session);
                finish();
            }
        });

        cancelButton.setOnClickListener(view -> finish());
    }
}
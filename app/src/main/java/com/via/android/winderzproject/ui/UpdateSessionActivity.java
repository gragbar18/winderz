package com.via.android.winderzproject.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Switch;

import com.via.android.winderzproject.R;
import com.via.android.winderzproject.data.Session;

import java.io.IOException;

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


    String windOrientation;
    String waveSize;

    Button cancelButton;
    Button updateButton;

    Switch favoriteSwitch;
    Uri uri;
    ImageView detailImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_session);
        updateSessionViewModel = new ViewModelProvider(this).get(UpdateSessionViewModel.class);
        updateSessionViewModel.init();

        updateButton = findViewById(R.id.updateButton);
        cancelButton = findViewById(R.id.cancelButton);

        Session session = updateSessionViewModel.getCurrentSession();
        Log.d("test", "session claimed by update activity " + session.toString());

        detailImage = findViewById(R.id.DetailImage);
        if (session.getUri() != null)
            detailImage.setImageURI(Uri.parse(session.getUri()));

        titleEdit = findViewById(R.id.titleEdit);
        titleEdit.setText(session.getTitle());

        favoriteSwitch = findViewById(R.id.Favorite_switch);
        favoriteSwitch.setChecked(session.isFavorite());

        hourPicker = findViewById(R.id.hourPicker);
        hourPicker.setMinValue(0);
        hourPicker.setMaxValue(23);
        hourPicker.setValue(session.getHourSession());

        minPicker = findViewById(R.id.minPicker);
        minPicker.setMinValue(0);
        minPicker.setMaxValue(59);
        minPicker.setValue(session.getMinSession());

        descriptionEdit = findViewById(R.id.descriptionEdit);
        descriptionEdit.setText(session.getDescription());

        windSpeedPicker = findViewById(R.id.windSpeedPicker);
        windSpeedPicker.setMinValue(0);
        windSpeedPicker.setMaxValue(200);
        windSpeedPicker.setValue(session.getWindSpeed());

        wavePeriodPicker = findViewById(R.id.wavePeriodPicker);
        wavePeriodPicker.setMinValue(0);
        wavePeriodPicker.setMaxValue(50);
        wavePeriodPicker.setValue(session.getWavePeriod());

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
                windOrientation = session.getWindOrientation();
            }
        });

        for (int i = 0; i < windOrientationSpinner.getCount(); i++) {
            if (windOrientationSpinner.getItemAtPosition(i).equals(session.getWindOrientation()))
                windOrientationSpinner.setSelection(i);
        }

        waveSizeSpinner = findViewById(R.id.waveSizeSpinner);
        ArrayAdapter<CharSequence> adapterWaveSize = ArrayAdapter.createFromResource(this, R.array.waveSize, android.R.layout.simple_spinner_item);
        adapterWaveSize.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        waveSizeSpinner.setAdapter(adapterWaveSize);
        waveSizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                waveSize = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                waveSize = session.getWaveSize();
            }
        });

        for (int i = 0; i < waveSizeSpinner.getCount(); i++) {
            if (waveSizeSpinner.getItemAtPosition(i).equals(session.getWaveSize()))
                waveSizeSpinner.setSelection(i);
        }

        updateButton.setOnClickListener(view -> {
            session.setTitle(titleEdit.getText().toString());
            session.setDescription(descriptionEdit.getText().toString());
            session.setFavorite(favoriteSwitch.isChecked());
            session.setWindSpeed(windSpeedPicker.getValue());
            session.setWavePeriod(wavePeriodPicker.getValue());
            session.setHourSession(hourPicker.getValue());
            session.setMinSession(minPicker.getValue());
            session.setWindOrientation(windOrientation);
            session.setWaveSize(waveSize);
            if (uri != null)
                session.setUri(uri.toString());

            if (session.getTitle() != null) {
                updateSessionViewModel.saveCurrentSession(session);
                Log.d("test", "session saved from update activity " + session.toString());
                updateSessionViewModel.updateSession(session);
                finish();
            }
        });

        cancelButton.setOnClickListener(view -> finish());

        detailImage.setOnClickListener(v -> {
            Intent gallery = new Intent();
            gallery.setType("image/*");
            gallery.setAction(Intent.ACTION_OPEN_DOCUMENT);

            startActivityForResult(Intent.createChooser(gallery, "Select Picture"), 1);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == -1) {
            uri = data != null ? data.getData() : null;
            if (uri != null)
                getContentResolver().takePersistableUriPermission(uri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                detailImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
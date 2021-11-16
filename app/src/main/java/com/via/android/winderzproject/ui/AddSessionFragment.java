package com.via.android.winderzproject.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.via.android.winderzproject.R;
import com.via.android.winderzproject.data.Session;

public class AddSessionFragment extends Fragment {
    AddSessionViewModel addSessionViewModel;
    NavController navController;

    Spinner windOrientationSpinner;
    EditText titleEdit;
    EditText descriptionEdit;
    EditText windSpeedEdit;
    EditText waveSizeEdit;
    EditText waveFrequencyEdit;
    Switch favoriteSwitch;
    Button cancelButton;
    Button addButton;

    String title;
    String description;
    String windSpeed;
    String windOrientation;
    String waveSize;
    String waveFrequency;
    Boolean favorite;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addSessionViewModel = new ViewModelProvider(this).get(AddSessionViewModel.class);
        addSessionViewModel.init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_session, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.navController = Navigation.findNavController(view);
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        cancelButton = view.findViewById(R.id.cancelButton);
        addButton = view.findViewById(R.id.addButton);
        titleEdit = view.findViewById(R.id.titleEdit);
        descriptionEdit = view.findViewById(R.id.descriptionEdit);
        windSpeedEdit = view.findViewById(R.id.WindSpeedEdit);
        waveSizeEdit = view.findViewById(R.id.waveSizeEdit);
        waveFrequencyEdit = view.findViewById(R.id.WaveFrequencyEdit);
        favoriteSwitch = view.findViewById(R.id.Favorite_switch);

        //add part
        addButton.setOnClickListener(v -> {
            title = titleEdit.getText().toString();
            description = descriptionEdit.getText().toString();
            windSpeed = windSpeedEdit.getText().toString();
            waveSize = waveSizeEdit.getText().toString();
            waveFrequency = waveFrequencyEdit.getText().toString();
            if(title != null){
                addSessionViewModel.addSession(new Session(title, description, windSpeed, windOrientation, waveSize, waveFrequency, false));
            }
            navController.navigate(R.id.homeFragment);
        });

        //delete part
        cancelButton.setOnClickListener(v -> navController.navigate(R.id.homeFragment));

        //Spinner part
        windOrientationSpinner = getView().findViewById(R.id.WindOrientationSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getView().getContext(), R.array.windOrientation, android.R.layout.simple_spinner_item);
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
    }
}

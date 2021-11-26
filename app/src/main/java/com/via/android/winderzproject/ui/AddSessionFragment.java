package com.via.android.winderzproject.ui;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.via.android.winderzproject.R;
import com.via.android.winderzproject.data.Session;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

public class AddSessionFragment extends Fragment{

    AddSessionViewModel addSessionViewModel;
    NavController navController;

    Spinner windOrientationSpinner;
    Spinner waveSizeSpinner;
    EditText titleEdit;
    EditText descriptionEdit;
    Switch favoriteSwitch;
    Button cancelButton;
    Button addButton;
    NumberPicker hourPicker;
    NumberPicker minPicker;
    NumberPicker windSpeedPicker;
    NumberPicker wavePeriodPicker;

    String title;
    String description;
    int windSpeed;
    String windOrientation;
    String waveSize;
    int wavePeriod;
    Boolean favorite = false;
    String date;
    String hour;
    int hourSession;
    int minSession;


    //image part 1
    private ImageView ProfileImage;
    private static final int PICK_IMAGE = 1;
    Uri imageUri;
    Button photoButton;



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
        photoButton = view.findViewById(R.id.takePhotoButton);
        descriptionEdit = view.findViewById(R.id.descriptionEdit);

        descriptionEdit.setOnTouchListener((v, motionEvent) -> {
            if (descriptionEdit.hasFocus()) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                switch (motionEvent.getAction() & MotionEvent.ACTION_MASK){
                    case MotionEvent.ACTION_SCROLL:
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        return true;
                }
            }
            return false;
        });


        favoriteSwitch = view.findViewById(R.id.Favorite_switch);

        hourPicker = getView().findViewById(R.id.hourPicker);
        hourPicker.setMinValue(0);
        hourPicker.setMaxValue(23);

        minPicker = getView().findViewById(R.id.minPicker);
        minPicker.setMinValue(0);
        minPicker.setMaxValue(23);

        windSpeedPicker = getView().findViewById(R.id.windSpeedPicker);
        windSpeedPicker.setMinValue(0);
        windSpeedPicker.setMaxValue(200);

        wavePeriodPicker = getView().findViewById(R.id.wavePeriodPicker);
        wavePeriodPicker.setMinValue(0);
        wavePeriodPicker.setMaxValue(50);

        //add part
        addButton.setOnClickListener(v -> {
            title = titleEdit.getText().toString();
            description = descriptionEdit.getText().toString();
            windSpeed = windSpeedPicker.getValue();
            wavePeriod = wavePeriodPicker.getValue();
            hourSession = hourPicker.getValue();
            minSession = minPicker.getValue();
            favorite = favoriteSwitch.isChecked();

            //Date part
            Date now = new Date();
            DateFormat dateformatter = DateFormat.getDateInstance(DateFormat.SHORT);
            date = dateformatter.format(now);
            DateFormat timeformatter = DateFormat.getTimeInstance(DateFormat.SHORT);
            hour = timeformatter.format(now);

            String uri = null;
            if(imageUri != null)
                uri = imageUri.toString();

            if(title != null){
                addSessionViewModel.addSession(new Session(title, description, windSpeed, windOrientation, waveSize, wavePeriod, favorite,date,hour,hourSession,minSession, uri));
            }
            navController.navigate(R.id.homeFragment);
        });

        //delete part
        cancelButton.setOnClickListener(v -> navController.navigate(R.id.homeFragment));

        //Spinner part
        windOrientationSpinner = getView().findViewById(R.id.windOrientationSpinner);
        ArrayAdapter<CharSequence> adapterWindOrientation = ArrayAdapter.createFromResource(getView().getContext(), R.array.windOrientation, android.R.layout.simple_spinner_item);
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

        waveSizeSpinner = getView().findViewById(R.id.waveSizeSpinner);
        ArrayAdapter<CharSequence> adapterWaveSize = ArrayAdapter.createFromResource(getView().getContext(), R.array.waveSize, android.R.layout.simple_spinner_item);
        adapterWaveSize.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        waveSizeSpinner.setAdapter(adapterWaveSize);
        waveSizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                waveSize = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                waveSize = "flat";
            }
        });



        //image Part



        ProfileImage= view.findViewById(R.id.activityImage);
        ProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_OPEN_DOCUMENT);

                startActivityForResult(Intent.createChooser(gallery, "Select Picture"), PICK_IMAGE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == -1){
            imageUri = data.getData();
            if(imageUri != null)
                getActivity().getContentResolver().takePersistableUriPermission(imageUri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),imageUri);
                ProfileImage.setImageBitmap(bitmap);
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

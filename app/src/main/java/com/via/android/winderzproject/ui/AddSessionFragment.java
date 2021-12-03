package com.via.android.winderzproject.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.provider.Settings;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.via.android.winderzproject.R;
import com.via.android.winderzproject.data.Session;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

public class AddSessionFragment extends Fragment {
    AddSessionViewModel addSessionViewModel;
    NavController navController;
    static final int PICK_IMAGE = 1;

    //allows to get the last known position of the device
    FusedLocationProviderClient fusedLocationClient;


    Spinner windOrientationSpinner;
    Spinner waveSizeSpinner;

    EditText titleEdit;
    EditText descriptionEdit;

    Switch favoriteSwitch;
    ImageView addImage;

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
    Boolean favorite = false; //Initially defines to false
    String date;
    String hour;
    int hourSession;
    int minSession;
    Uri imageUri;
    double lattitude;
    double longitude;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addSessionViewModel = new ViewModelProvider(this).get(AddSessionViewModel.class);
        addSessionViewModel.init();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());

        // method to get the location
        getLastLocation();

    }

    @SuppressLint("MissingPermission")
    private void getLastLocation() {

        // check if location is enabled
        if (isLocationEnabled()) {

            fusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    if (location == null) {
                        requestNewLocationData();
                    } else {
                        longitude = location.getLongitude();
                        lattitude = location.getLatitude();
                        //Toast.makeText(getContext(), lattitude + " "+ longitude,Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            //Toast.makeText(getContext(), "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }
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


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        cancelButton = view.findViewById(R.id.cancelButton);
        addButton = view.findViewById(R.id.addButton);
        titleEdit = view.findViewById(R.id.titleEdit);
        descriptionEdit = view.findViewById(R.id.descriptionEdit);

        //Enable scroll of the description TextView inside a ScrollView
        descriptionEdit.setOnTouchListener((v, motionEvent) -> {
            if (descriptionEdit.hasFocus()) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                if ((motionEvent.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_SCROLL) {
                    v.getParent().requestDisallowInterceptTouchEvent(false);
                    return true;
                }
            }
            return false;
        });

        favoriteSwitch = view.findViewById(R.id.Favorite_switch);

        hourPicker = view.findViewById(R.id.hourPicker);
        hourPicker.setMinValue(0);
        hourPicker.setMaxValue(23);

        minPicker = view.findViewById(R.id.minPicker);
        minPicker.setMinValue(0);
        minPicker.setMaxValue(59);

        windSpeedPicker = view.findViewById(R.id.windSpeedPicker);
        windSpeedPicker.setMinValue(5);
        windSpeedPicker.setMaxValue(60);

        wavePeriodPicker = view.findViewById(R.id.wavePeriodPicker);
        wavePeriodPicker.setMinValue(0);
        wavePeriodPicker.setMaxValue(30);


        addButton.setOnClickListener(v -> {
            title = titleEdit.getText().toString();
            description = descriptionEdit.getText().toString();
            windSpeed = windSpeedPicker.getValue();
            wavePeriod = wavePeriodPicker.getValue();
            hourSession = hourPicker.getValue();
            minSession = minPicker.getValue();
            favorite = favoriteSwitch.isChecked();


            //definition date of the session's creation
            Date now = new Date();
            DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.DEFAULT);
            date = dateFormatter.format(now);
            DateFormat timeFormatter = DateFormat.getTimeInstance(DateFormat.SHORT);
            hour = timeFormatter.format(now);

            //if no picture are uploaded for the session create a session with a null uri value
            String uri = null;
            if (imageUri != null)
                uri = imageUri.toString();

            if (!title.equals("")) {
                addSessionViewModel.addSession(new Session(title, description, windSpeed, windOrientation, waveSize, wavePeriod, favorite, date, hour, hourSession, minSession, uri, lattitude, longitude));
                navController.navigate(R.id.homeFragment);
            } else {
                //Toast.makeText(getContext(), "Please enter a title", Toast.LENGTH_LONG).show();
            }

            //adding lng & lat when add button clicked


        });

        cancelButton.setOnClickListener(v -> navController.navigate(R.id.homeFragment));

        windOrientationSpinner = view.findViewById(R.id.windOrientationSpinner);
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

        waveSizeSpinner = view.findViewById(R.id.waveSizeSpinner);
        ArrayAdapter<CharSequence> adapterWaveSize = ArrayAdapter.createFromResource(view.getContext(), R.array.waveSize, android.R.layout.simple_spinner_item);
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

        addImage = view.findViewById(R.id.activityImage);
        if (imageUri == null)
            addImage.setImageResource(R.drawable.nophoto);
        addImage.setOnClickListener(v -> {
            Intent gallery = new Intent();
            gallery.setType("image/*");
            gallery.setAction(Intent.ACTION_OPEN_DOCUMENT);
            startActivityForResult(Intent.createChooser(gallery, "Select Picture"), PICK_IMAGE);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Activity activity = getActivity();
        if (requestCode == PICK_IMAGE && resultCode == -1 && activity != null) {
            imageUri = data != null ? data.getData() : null;
            if (imageUri != null)
                activity.getContentResolver().takePersistableUriPermission(imageUri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), imageUri);
                addImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        // setting LocationRequest
        // on FusedLocationClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
        fusedLocationClient.requestLocationUpdates(mLocationRequest, locationCallback, Looper.myLooper());
    }

    private LocationCallback locationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location lastLocation = locationResult.getLastLocation();
            longitude = lastLocation.getLongitude();
            lattitude = lastLocation.getLatitude();
            //Toast.makeText(getContext(), lattitude + ""+ longitude,Toast.LENGTH_SHORT).show();

        }
    };

}

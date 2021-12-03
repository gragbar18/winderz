package com.via.android.winderzproject.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.via.android.winderzproject.R;
import com.via.android.winderzproject.data.Session;

public class MapFragment extends Fragment implements GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback,GoogleMap.OnInfoWindowLongClickListener{

    MapViewModel mapViewModel;
    private GoogleMap map;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapViewModel = new ViewModelProvider(this).get(MapViewModel.class);
        mapViewModel.init();
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setOnMyLocationButtonClickListener(this);
        map.setOnMyLocationClickListener(this);
        map.setMyLocationEnabled(true);
        map.setOnInfoWindowLongClickListener(this);
        map.getUiSettings().setMyLocationButtonEnabled(true);
        mapViewModel.getSessions().observe(this, sessions -> {
            for(Session session: sessions){
                if(session.getLng() != null && session.getLat() != null){
                    map.addMarker(new MarkerOptions()
                        .position(new LatLng(session.getLat(), session.getLng()))
                        .title(session.getTitle()))
                    .setTag(session);
                }
            }
        });
    }


    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(getContext(), "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(getContext(), "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    /*@Override
    public void onInfoWindowClick(@NonNull Marker marker) {
        LatLng position = marker.getPosition();
    Toast.makeText(getContext(), "marker location:\n" + position.latitude + position.longitude, Toast.LENGTH_LONG).show();
    }*/

    @Override
    public void onInfoWindowLongClick(@NonNull Marker marker) {
        LatLng position = marker.getPosition();
        Session session = (Session) marker.getTag();
        mapViewModel.saveCurrentSession(session);
        Intent intent = new Intent(this.getContext(), DetailsActivity.class);
        this.getContext().startActivity(intent);
    }
}
package com.example.lostandfoundapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final float SEARCH_RADIUS_KM = 1000;
    private GoogleMap googleMap;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Lost & Found Map");
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        googleMap = map;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Location permission not granted", Toast.LENGTH_SHORT).show();
            return;
        }

        googleMap.setMyLocationEnabled(true);

        fusedLocationClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                null
        ).addOnSuccessListener(currentLocation -> {

            if (currentLocation == null) {
                Toast.makeText(this, "Current location unavailable", Toast.LENGTH_SHORT).show();
                return;
            }

            LatLng userLatLng = new LatLng(
                    currentLocation.getLatitude(),
                    currentLocation.getLongitude()
            );

            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, 8));

            loadMarkersWithinRadius(currentLocation);

        });
    }

    private void loadMarkersWithinRadius(Location currentLocation) {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        ArrayList<Item> itemList = databaseHelper.getAllItems();

        for (Item item : itemList) {
            float[] results = new float[1];

            Location.distanceBetween(
                    currentLocation.getLatitude(),
                    currentLocation.getLongitude(),
                    item.getLatitude(),
                    item.getLongitude(),
                    results
            );

            float distanceInKm = results[0] / 1000f;

            if (distanceInKm <= SEARCH_RADIUS_KM) {
                LatLng itemLocation = new LatLng(
                        item.getLatitude(),
                        item.getLongitude()
                );

                googleMap.addMarker(new MarkerOptions()
                        .position(itemLocation)
                        .title(item.postType + ": " + item.name)
                        .snippet(String.format("%.2f km away", distanceInKm)));
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
package com.ember.ember.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;

import com.ember.ember.R;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

import java.io.IOException;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, MapboxMap.OnMapClickListener {

    private MapView mapView;
    private Marker marker;
    private MapboxMap mapboxMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Mapbox.getInstance(this, getString(R.string.access_token));
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
    }

    public void submit(View v) {
        LatLng position = marker.getPosition();
        String latlon = position.getLatitude() + "," +  position.getLongitude();
        String addressStr = "Unable to detect address.";
        try {
            List<Address> addresses = new Geocoder(this).getFromLocation(position.getLatitude(), position.getLongitude(), 1);
            if (!addresses.isEmpty()) {
                Address address = addresses.get(0);
                addressStr = extractAddress(address);
            }
            Intent data = new Intent();
            data.putExtra("latlon", latlon);
            data.putExtra("address", addressStr);
            setResult(RESULT_OK, data);
            finish();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String extractAddress(Address address) {
        StringBuilder addressLines = new StringBuilder();
        for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
            addressLines.append(address.getAddressLine(i));
        }
        return  addressLines.toString();
    }

    @Override
    public void onMapClick(@NonNull LatLng point) {
        if (marker != null) {
            mapboxMap.removeMarker(marker);
        }
        marker = mapboxMap.addMarker(new MarkerOptions()
                .position(point)
        );
    }

    @Override
    public void onMapReady(MapboxMap map) {
        mapboxMap = map;
        marker = mapboxMap.addMarker(new MarkerOptions()
                .position(new LatLng(1.3521, 103.8198))
                .title("Your Location")
        );
        mapboxMap.addOnMapClickListener(this);
    }



    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}

package com.ember.ember.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.ember.ember.R;
import com.google.android.material.textfield.TextInputEditText;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

import java.io.IOException;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class AddressFragment extends DialogFragment implements OnMapReadyCallback, MapboxMap.OnMapClickListener {

    private Activity activity;
    private int resource;
    private MapView mapView;
    private Marker marker;
    private MapboxMap mapboxMap;
    private LatLng selectedPoint;

    public AddressFragment() {

    }

    @SuppressLint("ValidFragment")
    public AddressFragment(Activity activity, int resource) {
        this.activity = activity;
        this.resource = resource;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        Mapbox.getInstance(getContext(), getString(R.string.access_token));
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View map = inflater.inflate(R.layout.dialog_map, null);
        mapView = map.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        builder.setView(map)
                .setPositiveButton("Confirm", (DialogInterface dialog, int id) -> {
                    TextInputEditText text = activity.findViewById(resource);
                    LatLng position = marker.getPosition();
                    text.setTag(position.getLatitude() + "," +  position.getLongitude());
                    try {
                        List<Address> addresses = new Geocoder(getContext()).getFromLocation(position.getLatitude(), position.getLongitude(), 1);
                        if (addresses.isEmpty()) {
                            text.setText("Unable to detect address.");
                            return;
                        }
                        Address address = addresses.get(0);
                        text.setText(extractAddress(address));
                    } catch (IOException e) {
                        text.setText("");
                        e.printStackTrace();
                    }
                })
                .setNegativeButton("Cancel", (DialogInterface dialog, int id) -> {
                });
        return builder.create();
    }

    private String extractAddress(Address address) {
        StringBuilder addressLines = new StringBuilder();
        for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
            addressLines.append(address.getAddressLine(i));
        }
        return  addressLines.toString();
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
    public void onDestroyView() {
        super.onDestroyView();
//        mapView.onDestroy();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onMapClick(@NonNull LatLng point) {
        if (marker != null) {
            mapboxMap.removeMarker(marker);
        }
        selectedPoint = point;
        marker = mapboxMap.addMarker(new MarkerOptions()
                .position(selectedPoint)
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
}
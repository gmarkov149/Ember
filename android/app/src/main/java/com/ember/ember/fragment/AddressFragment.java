package com.ember.ember.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.ember.ember.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public class AddressFragment extends DialogFragment implements OnMapReadyCallback {

    Activity activity;
    int resource;
    private GoogleMap googleMap;

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
        // Get the layout inflater
//        LayoutInflater inflater = getActivity().getLayoutInflater();
//        View map = inflater.inflate(R.layout.dialog_map, null);

//        SupportMapFragment mapFragment = getActivity().getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(R.layout.dialog_map)
                // Add action buttons
                .setPositiveButton("Confirm", (DialogInterface dialog, int id) -> {
                    // sign in the user ...
                })
                .setNegativeButton("Cancel", (DialogInterface dialog, int id) -> {
                    AddressFragment.this.getDialog().cancel();
                });
        return builder.create();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.
        LatLng sydney = new LatLng(-33.852, 151.211);
        googleMap.addMarker(new MarkerOptions().position(sydney)
                .title("Marker in Sydney"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
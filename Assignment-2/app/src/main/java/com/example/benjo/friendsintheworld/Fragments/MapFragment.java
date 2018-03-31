package com.example.benjo.friendsintheworld.Fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.benjo.friendsintheworld.SettersGetters.MarkerData;
import com.example.benjo.friendsintheworld.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


public class MapFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap googleMap;
    private View rootView;
    private boolean mapReady;


    public MapFragment() { /* Default constructor */ }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_map, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initGoogleMaps();
    }

    private void initGoogleMaps() {
        MapView mapView = (MapView) rootView.findViewById(R.id.map);
        mapView.onCreate(null);
        mapView.onResume();
        mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        mapReady = true;
    }

    private void addMarker(LatLng latLng, String name) {
        if (mapReady) {
            MarkerOptions mo = new MarkerOptions().position(latLng).title(name);
            googleMap.addMarker(mo);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 3));
        }
    }

    public void addMarkers(ArrayList<MarkerData> markerData) {
        if (mapReady && markerData != null) {
            googleMap.clear();
            for (MarkerData marker : markerData) {
                addMarker(marker.getLatLng(), marker.getName());
            }
        } else {
            Snackbar.make(getActivity().findViewById(R.id.main_activity_parent), "Cant find locations for the current group", Snackbar.LENGTH_LONG).show();
        }
    }

}


package com.example.benjo.friendsintheworld.listeners;


import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.example.benjo.friendsintheworld.Controller.Controller;
import com.google.android.gms.maps.model.LatLng;

public class LocList implements LocationListener {
    private Controller controller;

    public LocList(Controller controller) {
        this.controller = controller;
    }

    // Called when the location has changed.
    public void onLocationChanged(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        controller.setLocation(new LatLng(latitude, longitude));
    }

    // Called when the provider status changes.
    public void onStatusChanged(String provider, int status, Bundle extras) { /* Do nothing */ }

    // Called when the provider is enabled by the user.
    public void onProviderEnabled(String provider) {
        controller.onProviderEnabled(provider);
    }

    // Called when the provider is disabled by the user.
    public void onProviderDisabled(String provider) {
        controller.onProviderDisabled(provider);
    }

}

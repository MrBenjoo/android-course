package com.example.benjo.friendsintheworld.SettersGetters;


import com.google.android.gms.maps.model.LatLng;

public class MarkerData {
    private String name;
    private LatLng latLng;

    public MarkerData(String name, LatLng latLng) {
        this.name = name;
        this.latLng = latLng;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String getName() {
        return name;
    }

    public LatLng getLatLng() {
        return latLng;
    }


}

package com.example.benjo.friendsintheworld.Fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.benjo.friendsintheworld.SettersGetters.MarkerData;
import com.example.benjo.friendsintheworld.SettersGetters.Member;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class DataFragment extends Fragment {

    /* Containers */
    private Map<String, ArrayList<MarkerData>> locations = new HashMap<>();
    private Map<String, Member> memberMap = new HashMap<>();
    private ArrayList<String> myGroups = new ArrayList<>();
    private ArrayList<String> currentGroups = new ArrayList<>();

    /* Booleans */
    private boolean bound = false;
    private boolean serviceRunning = false;
    private boolean executorRunning = false;

    /* Location */
    private LatLng location;
    private boolean connected = false;


    public DataFragment() { /* Default constructor */ }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void setBound(boolean status) {
        this.bound = status;
    }

    public void setServiceRunning(boolean status) {
        this.serviceRunning = status;
    }

    public void setExecutorRunning(boolean status) {
        this.executorRunning = status;
    }

    public void setLocation(LatLng newLocation) {
        this.location = newLocation;
    }

    public boolean isBound() {
        return this.bound;
    }

    public boolean isServiceRunning() {
        return this.serviceRunning;
    }

    public boolean isExecutorRunning() {
        return this.executorRunning;
    }

    public LatLng getLocation() {
        return this.location;
    }

    public void addMember(String group, Member member) {
        this.memberMap.put(group, member);
    }

    public void addToMyGroupList(String group) {
        this.myGroups.add(group);
    }

    public Map<String, Member> getMembers() {
        return this.memberMap;
    }

    public ArrayList<String> getMyGroups() {
        return this.myGroups;
    }

    public Map<String, ArrayList<MarkerData>> getLocations() {
        return this.locations;
    }

    public ArrayList<String> getCurrentGroups() {
        return this.currentGroups;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("datafragment", "ondestroy");
    }

    public boolean isConnected() {
        return this.connected;
    }

    public void setConnected(boolean status) {
        this.connected = status;
    }
}

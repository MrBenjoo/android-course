package com.example.benjo.friendsintheworld.Controller;


import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.benjo.friendsintheworld.Fragments.DataFragment;
import com.example.benjo.friendsintheworld.Fragments.ListFragment;
import com.example.benjo.friendsintheworld.Fragments.MapFragment;
import com.example.benjo.friendsintheworld.Activities.MainActivity;
import com.example.benjo.friendsintheworld.R;
import com.example.benjo.friendsintheworld.Service.ServerCommands;
import com.example.benjo.friendsintheworld.Service.TCPService;
import com.example.benjo.friendsintheworld.SettersGetters.MarkerData;
import com.example.benjo.friendsintheworld.SettersGetters.Member;
import com.example.benjo.friendsintheworld.listeners.LocList;
import com.example.benjo.friendsintheworld.listeners.ReceiveListener;
import com.example.benjo.friendsintheworld.timertask.UpdateDeviceLocation;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Controller {

    /* Service Related */
    private TCPService service;
    private ServiceConn serviceConn;
    private Intent serviceIntent;
    private ReceiveListener receiveListener;
    private ServerCommands command;

    /* Map Related */
    private LocationManager locationManager;
    private LocationListener locListener;
    public static final int REQUEST_ACCESS_FINE_LOCATION = 1;

    /* Fragments */
    private ListFragment listFragment;
    private MapFragment mapFragment;
    private DataFragment dataFragment;

    /* ********** */
    private MainActivity mainActivity;
    private static Timer timer;


    public Controller(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        initFragments();
        initDataFragment();
        startService();
        bindToService();
        initLocationManager(new LocList(this));
    }

    private void initFragments() {
        listFragment = new ListFragment();
        listFragment.setController(this);
        mapFragment = new MapFragment();
        mainActivity.setFragment(listFragment, false);
    }

    private void initDataFragment() {
        FragmentManager fm = mainActivity.getFragmentManager();
        dataFragment = (DataFragment) fm.findFragmentByTag("dataFragment");
        if (dataFragment == null) {
            dataFragment = new DataFragment();
            fm.beginTransaction().add(dataFragment, "dataFragment").commit();
        }
    }

    private void startService() {
        serviceIntent = new Intent(mainActivity, TCPService.class);
        command = new ServerCommands();
        if (!dataFragment.isServiceRunning()) {
            mainActivity.startService(serviceIntent);
            dataFragment.setServiceRunning(true);
        }
    }

    private void bindToService() {
        if (!dataFragment.isBound()) {
            serviceConn = new ServiceConn();
            mainActivity.bindService(serviceIntent, serviceConn, Context.BIND_AUTO_CREATE);
        }
    }

    private void initLocationManager(LocList locList) {
        locationManager = (LocationManager) mainActivity.getSystemService(Context.LOCATION_SERVICE);
        locListener = locList;
        if (ContextCompat.checkSelfPermission(mainActivity.getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(mainActivity, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_ACCESS_FINE_LOCATION);
        } else {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locListener);
        }
    }

    public void register(String name, String group) {
        service.send(command.registration(name, group));
    }

    public void unsubscribe(String group) {
        Member member = dataFragment.getMembers().get(group);
        if (member != null) {
            String id = member.getId();
            service.send(command.deregistration(id));
        } else {
            mainActivity.showText("You are not part of this group");
        }
    }

    public void showMap() {
        mainActivity.setFragment(mapFragment, true);
    }

    public void onRefreshListener() {
        if (dataFragment.isBound()) {
            mainActivity.showText("List updated");
            service.send(command.currentGroups());
        }
    }

    public ArrayList<String> getMyGroups() {
        return dataFragment.getMyGroups();
    }

    public void addMarkers(String group) {
        mapFragment.addMarkers(dataFragment.getLocations().get(group));
    }

    public void showDialogJoin(String group) {
        mainActivity.showDialogJoin(group);
    }

    public void setLocation(LatLng location) {
        dataFragment.setLocation(location);
        if (dataFragment.isBound() && !dataFragment.getMembers().isEmpty() && !dataFragment.isExecutorRunning()) {
            startExecutorService();
        }
    }

    public void onProviderEnabled(String provider) {
        if (provider.equals(LocationManager.GPS_PROVIDER)) {
            mainActivity.showText("GPS provider aktiv");
        }
        if (provider.equals(LocationManager.NETWORK_PROVIDER)) {
            mainActivity.showText("Network provider aktiv");
        }
    }

    public void onProviderDisabled(String provider) {
        if (provider.equals(LocationManager.GPS_PROVIDER)) {
            mainActivity.showText("GPS provider inaktiv");
        }
        if (provider.equals(LocationManager.NETWORK_PROVIDER)) {
            mainActivity.showText("Network provider inaktiv");
        }
    }

    public void receive() throws InterruptedException {
        String response = service.receive();
        processResponse(response);
    }

    public boolean isMembersEmpty() {
        boolean noMembers = dataFragment.getMembers().isEmpty();
        if (noMembers) {
            Log.i("shutdownTimerTask", "isMembersEmpty == true");
            shutdownTimerTask();
            return true;
        } else {
            return false;
        }
    }

    public void shutdownTimerTask() {
        timer.cancel();
        dataFragment.setExecutorRunning(false);
    }

    public void updateDeviceLocation() {
        for (Member member : dataFragment.getMembers().values()) {
            String id = member.getId();
            String latitude = Double.toString(dataFragment.getLocation().latitude);
            String longitude = Double.toString(dataFragment.getLocation().longitude);
            Log.i("TIMER", "SENDING: " + command.setPosition(id, longitude, latitude));
            service.send(command.setPosition(id, longitude, latitude));
        }
    }

    private class ServiceConn implements ServiceConnection {
        public void onServiceConnected(ComponentName arg0, IBinder binder) {
            TCPService.LocalService ls = (TCPService.LocalService) binder;
            service = ls.getService();
            dataFragment.setBound(true);
            receiveListener = new ReceiveListener(Controller.this);
            receiveListener.startThread();
            if (!dataFragment.isConnected()) {
                service.connect();
                dataFragment.setConnected(true);
            }
        }

        public void onServiceDisconnected(ComponentName arg0) {
            Log.i("CONTROLLER", "onServiceDisconnected");
            dataFragment.setBound(false);
        }
    }

    public class ProviderListener extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().matches(LocationManager.PROVIDERS_CHANGED_ACTION)) {
                providers();
            }
        }
    }

    private void providers() {
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            mainActivity.showText("GPS provider aktiv");
        } else {
            mainActivity.showText("GPS provider inaktiv");
        }
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            mainActivity.showText("Network provider aktiv");
        } else {
            mainActivity.showText("Network provider inaktiv");
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_ACCESS_FINE_LOCATION:
                if (ContextCompat.checkSelfPermission(mainActivity, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locListener);
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locListener);
                }
                break;
        }
    }

    private void processResponse(String response) {
        try {
            JSONObject jsonObj = new JSONObject(response);
            String type = (String) jsonObj.get("type");
            switch (type) {
                case "register":
                    Log.i("RECEIVED", "register");
                    registerReceived(jsonObj);
                    break;
                case "unregister":
                    Log.i("RECEIVED", "unregister");
                    unregisterReceived(jsonObj);
                    break;
                case "members":
                    // TODO
                    break;
                case "groups":
                    Log.i("RECEIVED", "groups");
                    groupsReceived(jsonObj);
                    break;
                case "locations":
                    Log.i("RECEIVED", "locations");
                    locationsReceived(jsonObj);
                    break;
                case "exception":
                    Log.i("RECEIVED", response);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.i("processResponse", "EXCEPTION");
        }
    }

    private void registerReceived(JSONObject jsonObj) throws JSONException {
        String group = (String) jsonObj.get("group");
        String id = (String) jsonObj.get("id");
        String name = id.substring((id.indexOf(',') + 1), id.lastIndexOf(',') - 1);
        Log.i("REGISTER", id);

        String latitude;
        String longitude;
        LatLng latLng = dataFragment.getLocation();
        if (latLng == null) {
            latitude = null;
            longitude = null;
            Log.i("REGISTER", "Latitude: null " + "longitude: null");
        } else {
            latitude = Double.toString(latLng.latitude);
            longitude = Double.toString(latLng.longitude);
            Log.i("REGISTER", "Latitude: " + latitude + " longitude: " + longitude);
        }

        dataFragment.addMember(group, new Member(name, group, id, longitude, latitude));
        dataFragment.addToMyGroupList(group);
        mainActivity.showText("Registered.");
    }

    private void startExecutorService() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new UpdateDeviceLocation(this), 0, 30000);
        dataFragment.setExecutorRunning(true);
        Log.i("TIMER", "RUNNING...");
    }

    private void unregisterReceived(JSONObject jsonObj) throws JSONException {
        String id = (String) jsonObj.get("id");

        for (Member member : dataFragment.getMembers().values()) {
            if (member.getId().equals(id)) {
                Log.i("UNREGISTER", "REMOVING GROUP: " + dataFragment.getMembers().get(member.getGroup()).getGroup() + " FROM MEMBERS");
                dataFragment.getMembers().remove(member.getGroup());
                if (dataFragment.getMembers().get(member.getGroup()) == null) {
                    Log.i("UNREGISTER", "REMOVING GROUP FROM MEMBERS = SUCCESSFUL");
                }
                int index = dataFragment.getMyGroups().indexOf(member.getGroup());
                Log.i("UNREGISTER", "REMOVING GROUP:" + dataFragment.getMyGroups().get(index) + " FROM MYGROUPS");
                dataFragment.getMyGroups().remove(member.getGroup());
                if (dataFragment.getMyGroups().indexOf(member.getGroup()) == -1) {
                    Log.i("UNREGISTER", "REMOVING GROUP FROM MYGROUPS = SUCCESSFUL");
                }
                Log.i("UNREGISTER", "REMOVING GROUP: " + dataFragment.getLocations().get(member.getGroup()) + " FROM LOCATIONS");
                dataFragment.getLocations().remove(member.getGroup());
                if (dataFragment.getLocations().get(member.getGroup()) == null) {
                    Log.i("UNREGISTER", "REMOVING GROUP FROM LOCATIONS = SUCCESSFUL");
                }
                break;
            }
        }
    }

    private void groupsReceived(JSONObject jsonObj) throws JSONException {
        dataFragment.getCurrentGroups().clear();
        Log.i("groupsReceived", jsonObj.getString("groups"));
        JSONArray currentGroups = jsonObj.getJSONArray("groups");

        for (int i = 0; i < currentGroups.length(); i++) {
            String group = (String) currentGroups.getJSONObject(i).get("group");
            dataFragment.getCurrentGroups().add(group);
            Log.i("groupsReceived", "adding group: " + group + " to currentGroupsList");
        }

        // Only the original thread that created a view hierarchy can touch its views.
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listFragment.updateRecyclerView(dataFragment.getCurrentGroups());
            }
        });
    }

    private void locationsReceived(JSONObject jsonObj) throws JSONException {
        String group = (String) jsonObj.get("group");
        JSONArray locations = jsonObj.getJSONArray("location");
        ArrayList<MarkerData> groupMarkers = dataFragment.getLocations().get(group);

        if (dataFragment.getLocations().get(group) != null) {
            dataFragment.getLocations().get(group).clear();
        }
        for (int i = 0; i < locations.length(); i++) {
            String name = (String) locations.getJSONObject(i).get("member");
            String longitude = (String) locations.getJSONObject(i).get("longitude");
            String latitude = (String) locations.getJSONObject(i).get("latitude");

            if (longitude.equals("NaN") || latitude.equals("NaN")) {
                Log.i("locationsReceived", "cant update location for " + name + " = NaN");
            } else {
                MarkerData markerData = new MarkerData(name, new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude)));
                if (groupMarkers == null) {
                    groupMarkers = new ArrayList<>();
                    groupMarkers.add(markerData);
                    dataFragment.getLocations().put(group, groupMarkers);
                } else {
                    dataFragment.getLocations().get(group).add(markerData);
                }
            }
        }
    }

    @SuppressWarnings("deprecation")
    public void changeLang(String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        mainActivity.getApplicationContext().getResources().updateConfiguration(config, null);
        mainActivity.recreate();
    }

    public void onPause() {
        if (mainActivity.isFinishing()) {
            mainActivity.getFragmentManager().beginTransaction().remove(dataFragment).commit();
        }

    }

    public void onDestroy() {
        if (dataFragment.isBound()) {
            mainActivity.unbindService(serviceConn);
            dataFragment.setBound(false);
            receiveListener.stopThread();
            if (dataFragment.isExecutorRunning()) {
                Log.i("shutdownTimerTask", "onDestroy");
                shutdownTimerTask();
            }
            Log.i("ONDESTROY", "UNBINDING SERVICE");
        }

        if (mainActivity.isFinishing()) {
            service.disconnect();
            mainActivity.stopService(serviceIntent);
            Log.i("ONDESTROY", "STOPPING SERVICE");
        }
    }

}

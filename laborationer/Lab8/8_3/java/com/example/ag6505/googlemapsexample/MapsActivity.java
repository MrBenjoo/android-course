package com.example.ag6505.googlemapsexample;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Random;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private boolean addMarker = false;
    private boolean addThumbnail = false;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private Button btnAddMarker;
    private Button btnAddThumbnail;
    private Bitmap thumbnail;
    private boolean mapReady;
    public static final int REQUEST_ACCESS_FINE_LOCATION = 1;
    static final int REQUEST_TAKE_THUMBNAIL = 2;
    private BroadcastReceiver providerListener;
    private Random random = new Random();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationListener = new LocList(this);

        btnAddMarker = (Button) findViewById(R.id.btnAddMarker);

        btnAddMarker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addMarker == false && mapReady == true) {
                    addMarker = true;
                } else {
                    Log.d("setOnClickL", "mapReady=false");
                }
            }
        });

        btnAddThumbnail = (Button) findViewById(R.id.btnAddThumbnail);

        btnAddThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addThumbnail == false && mapReady==true) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(intent, REQUEST_TAKE_THUMBNAIL);
                    }
                } else {
                    Log.d("Thumb_sOCL","mapReady=false");
                }
            }
        });

        providerListener = new ProviderListener();

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_ACCESS_FINE_LOCATION);
        } else {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }

    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_ACCESS_FINE_LOCATION :
                if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.registerReceiver(providerListener,new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION));

        providers();

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_ACCESS_FINE_LOCATION);
        } else {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            locationManager.removeUpdates(locationListener);
        } catch(SecurityException e) {
            Log.d("onPause","removeUpdates");
        }
        this.unregisterReceiver(providerListener);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mapReady = true;
    }

    private void addMarker(LatLng latLng) {
        addMarker = false;
        MarkerOptions mo = new MarkerOptions().position(latLng).title("My position");
        mMap.addMarker(mo);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 3));

    }

    private void addThumbnail(LatLng latLng) {
        addThumbnail = false;
        MarkerOptions mo = new MarkerOptions()
                .position(latLng)
                .title("My thumbnail")
                .icon(BitmapDescriptorFactory.fromBitmap(thumbnail));
        mMap.addMarker(mo);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(8), 2000, null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_TAKE_THUMBNAIL && resultCode== Activity.RESULT_OK) {
            thumbnail = data.getParcelableExtra("data");
            Bitmap bitmap = data.getParcelableExtra("data");
            thumbnail = Bitmap.createScaledBitmap(bitmap,bitmap.getWidth()/2,bitmap.getHeight()/2,false);
            addThumbnail = true;
        }
    }

    private class LocList implements LocationListener {

        Activity act;

        public LocList(Activity a){
            this.act = a;
        }

        @Override
        public void onLocationChanged(Location location) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            if (addMarker) {
                addMarker = false;
                addMarker(new LatLng(latitude, longitude));
            }
            if (addThumbnail) {
                addThumbnail(new LatLng(latitude,longitude));
            }

            Log.d("onLocChanged", "Lng=" + longitude + ",Lat=" + latitude);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {
            if(provider.equals(LocationManager.GPS_PROVIDER)) {
                Toast.makeText(act, "GPS provider aktiv", Toast.LENGTH_SHORT).show();
            }
            if (provider.equals(LocationManager.NETWORK_PROVIDER)){
                Toast.makeText(act, "Network provider aktiv", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onProviderDisabled(String provider) {
            if(provider.equals(LocationManager.GPS_PROVIDER)) {
                Toast.makeText(act, "GPS provider inaktiv", Toast.LENGTH_SHORT).show();
            }
            if(provider.equals(LocationManager.NETWORK_PROVIDER)) {
                Toast.makeText(act, "Network provider inaktiv", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void providers() {
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "GPS provider aktiv", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "GPS provider inaktiv", Toast.LENGTH_SHORT).show();
        }
        if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            Toast.makeText(this, "Network provider aktiv", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Network provider inaktiv", Toast.LENGTH_SHORT).show();
        }
    }

    public class ProviderListener extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().matches(LocationManager.PROVIDERS_CHANGED_ACTION)) {
                providers();
            }
        }
    }

}

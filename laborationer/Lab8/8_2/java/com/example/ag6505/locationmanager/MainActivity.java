package com.example.ag6505.locationmanager;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_ACCESS_FINE_LOCATION = 1;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private ProviderListener providerListener;
    private TextView tvLocation;
    private TextView tvGps;
    private TextView tvNetwork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvLocation = (TextView) findViewById(R.id.tvLocation);
        tvGps = (TextView)findViewById(R.id.tvGps);
        tvNetwork = (TextView)findViewById(R.id.tvNetwork);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double x = location.getLatitude();
                double y = location.getLongitude();
                tvLocation.setText("Lat=" + y + ",    Long=" + x);
                Log.d("Pos: ", "Lat=" + y + ",    Long=" + x);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                if(provider.equals(LocationManager.GPS_PROVIDER)) {
                    if(status == LocationProvider.AVAILABLE) tvGps.setText("GPS provider aktiv");
                    else tvGps.setText("GPS provider inaktiv");
                }
                if(provider.equals(LocationManager.NETWORK_PROVIDER)) {
                    if(status == LocationProvider.AVAILABLE) tvNetwork.setText("Network provider aktiv");
                    else tvNetwork.setText("Network provider inaktiv");
                }
            }

            @Override
            public void onProviderEnabled(String provider) {
                if(provider.equals(LocationManager.GPS_PROVIDER)) {
                    tvGps.setText("GPS provider aktiv");
                }
                if(provider.equals(LocationManager.NETWORK_PROVIDER)) {
                    tvNetwork.setText("Network provider aktiv");
                }
            }

            @Override
            public void onProviderDisabled(String provider) {
                if(provider.equals(LocationManager.GPS_PROVIDER)) {
                    tvGps.setText("GPS provider inaktiv");
                }
                if(provider.equals(LocationManager.NETWORK_PROVIDER)) {
                    tvNetwork.setText("Network provider inaktiv");
                }
            }
        };
        providerListener = new ProviderListener();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_ACCESS_FINE_LOCATION);
        } else {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.registerReceiver(providerListener,new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION));
        this.registerReceiver(providerListener,new IntentFilter(LocationManager.MODE_CHANGED_ACTION));
        providers();
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

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_ACCESS_FINE_LOCATION :
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);
                }
                break;
        }
    }

    private void providers() {
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            tvGps.setText("GPS provider aktiv");
        } else {
            tvGps.setText("GPS provider inaktiv");
        }
        if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            tvNetwork.setText("Network provider aktiv");
        } else {
            tvNetwork.setText("Network provider inaktiv");
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

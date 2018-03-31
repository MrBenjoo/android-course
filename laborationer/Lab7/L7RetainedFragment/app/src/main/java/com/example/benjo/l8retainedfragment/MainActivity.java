package com.example.benjo.l8retainedfragment;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;

public class MainActivity extends Activity {
    private Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fm = getFragmentManager();
        MainFragment mainFragment = (MainFragment) fm.findFragmentById(R.id.fragment);
        TCPFragment connection = (TCPFragment) fm.findFragmentByTag("TCPFragment");
        if (connection == null) {
            connection = new TCPFragment();
            fm.beginTransaction().add(connection, "TCPFragment").commit();
        }
        controller = new Controller(this, mainFragment, connection);
    }

    @Override
    protected void onDestroy() {
        controller.disconnectClicked();
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        controller.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        controller.onRestoreInstanceState(savedInstanceState);
    }
}

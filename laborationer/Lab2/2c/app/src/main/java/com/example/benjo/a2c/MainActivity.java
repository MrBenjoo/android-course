package com.example.benjo.a2c;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/*
initializes the system
 */
public class MainActivity extends AppCompatActivity {
    Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeSystem();
    }

    private void initializeSystem() {
        FragmentManager fm = getFragmentManager();
        ViewerFragment viewerFragment = (ViewerFragment)fm.findFragmentById(R.id.viewer_fragment); // Makes a reference to viewerFragment so that we can perform various action on it
        InputFragment inputFragment = (InputFragment)fm.findFragmentById(R.id.input_fragment);
        controller = new Controller(viewerFragment);
        inputFragment.setController(controller);
    }
}

package com.example.benjo.a2b;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/*
This class initializes the system
 */
public class HowToActivity extends AppCompatActivity {
    private Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to);
        initController();
    }

    private void initController()
    {
        FragmentManager fm = getFragmentManager();
        InputFragment inputFragment = (InputFragment)fm.findFragmentById(R.id.input_fragment); // Makes a reference to inputfragment so that we can perform various action on it
        controller = new Controller(inputFragment);
    }
}

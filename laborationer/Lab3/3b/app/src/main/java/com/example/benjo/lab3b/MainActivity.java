package com.example.benjo.lab3b;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initController();
    }

    private void initController() {
        FragmentManager fm = getFragmentManager();
        WhatToDoFragment whatToDoFragment = (WhatToDoFragment) fm.findFragmentById(R.id.whatToDoFragment);
        InstructionFragment lvFragment = (InstructionFragment) fm.findFragmentById(R.id.instructionFragment);
        new Controller(whatToDoFragment, lvFragment);
    }

}

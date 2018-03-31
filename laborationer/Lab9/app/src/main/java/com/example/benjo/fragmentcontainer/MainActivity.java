package com.example.benjo.fragmentcontainer;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("MainActivity-onCreate","Before");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("MainActivity-onCreate", "After");
        FragmentManager fm = getFragmentManager();
        ContainerFragment contFrag = (ContainerFragment)fm.findFragmentById(R.id.container_fragment);
        NavigationFragment navFrag = (NavigationFragment)fm.findFragmentById(R.id.navigation_fragment);
        new Controller(this,contFrag,navFrag,savedInstanceState);
    }
}

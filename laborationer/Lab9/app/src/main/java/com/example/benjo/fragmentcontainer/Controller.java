package com.example.benjo.fragmentcontainer;

import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by tsroax on 27/09/15.
 */
public class Controller {
    private ContainerFragment containerFragment;

    public Controller(MainActivity activity, ContainerFragment contFrag, NavigationFragment navFrag, Bundle savedInstanceState ) {
        containerFragment = contFrag;
        navFrag.setController(this);
        FragmentManager fm = contFrag.getChildFragmentManager(); // Ver 17
        if(savedInstanceState==null){
            contFrag.add(new FirstFragment(),"first");
            contFrag.add(new SecondFragment(),"second");
            contFrag.add(new ThirdFragment(), "third");
            contFrag.setCurrentTag("first");
        } else {
            contFrag.add(fm.findFragmentByTag("first"), "first");
            contFrag.add(fm.findFragmentByTag("second"),"second");
            contFrag.add(fm.findFragmentByTag("third"),"third");
        }
    }

    public void showFragment(String tag) {
        Log.v("control", containerFragment.show(tag));
    }
}

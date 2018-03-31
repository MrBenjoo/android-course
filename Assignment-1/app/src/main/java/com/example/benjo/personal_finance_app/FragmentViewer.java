package com.example.benjo.personal_finance_app;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.util.Log;


public class FragmentViewer {
    private int container;
    private FragmentManager fm;
    private String currentTag;

    public FragmentViewer(FragmentManager fm, int container) {
        this.fm = fm;
        this.container = container;
    }

    public void setCurrentTag(String currentTag) {
        this.currentTag = currentTag;
    }

    public String getCurrentTag() {
        return currentTag;
    }

    public void add(Fragment fragment, String tag) {
        FragmentTransaction ft = fm.beginTransaction();
        if (!fragment.isAdded()) {
            Log.v("ADDING FRAGMENT", tag);
            ft.add(container, fragment, tag);
        }
        ft.hide(fragment);
        ft.commit();
    }

    public String show() {
        return show(currentTag);
    }

    public String show(String tag) {
        Fragment fragment = fm.findFragmentByTag(tag);
        Fragment currentFragment = fm.findFragmentByTag(currentTag);
        if (fragment != null) {
            FragmentTransaction ft = fm.beginTransaction();
            if (currentFragment != null) {
                ft.hide(currentFragment);
                Log.v("HIDE FRAGMENT", currentTag);
            }
            ft.show(fragment);
            Log.v("SHOW FRAGMENT", tag);
            ft.commit();
            currentTag = tag;
            Log.v("TAG", currentTag + " = " + tag);
        }
        return currentTag;
    }
}

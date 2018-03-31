package com.example.benjo.fragmentcontainer;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

/**
 * Created by tsroax on 25/09/15.
 */
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
        if(!fragment.isAdded())
            ft.add(container, fragment, tag);
        ft.hide(fragment);
        ft.commit();
    }

    public String show() {
        return show(currentTag);
    }

    public String show(String tag) {
        Fragment fragment = fm.findFragmentByTag(tag);
        Fragment currentFragment = fm.findFragmentByTag(currentTag);
        if(fragment!=null) {
            FragmentTransaction ft = fm.beginTransaction();
            if(!tag.equals(currentTag)) {
//                ft.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right);
                ft.setCustomAnimations(R.animator.slide_in_right_rotate90, R.animator.slide_out_left_rotate90);
            }
            if(currentFragment!=null)
                ft.hide(currentFragment);
            ft.show(fragment);
            ft.commit();
            currentTag = tag;
        }
        return currentTag;
    }
}

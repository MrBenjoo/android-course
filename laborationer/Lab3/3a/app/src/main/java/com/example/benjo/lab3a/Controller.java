package com.example.benjo.lab3a;


import android.app.FragmentManager;
import android.graphics.Color;

public class Controller {
    private MainActivity mainActivity;
    private ViewerFragment viewerFragment;
    private InputFragment inputFragment;

    public Controller(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        initFragments();
    }

    private void initFragments() {
        FragmentManager fm = mainActivity.getFragmentManager();
        viewerFragment = (ViewerFragment) fm.findFragmentById(R.id.viewerFragment);
        inputFragment = (InputFragment) fm.findFragmentById(R.id.inputFragment);
        inputFragment.setController(this);
    }

    public void onItemClickListener(String color) {
        inputFragment.setBtnText(color);
    }

    public void changeTvColor(String tvColor) {
        switch (tvColor) {
            case "RÖD":
                viewerFragment.setTvColor(Color.RED);
                break;
            case "BLÅ":
                viewerFragment.setTvColor(Color.BLUE);
                break;
            case "GUL":
                viewerFragment.setTvColor(Color.YELLOW);
                break;
            case "GRÖN":
                viewerFragment.setTvColor(Color.GREEN);
                break;
        }
    }
}

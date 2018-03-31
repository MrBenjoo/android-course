package com.example.benjo.lab6a;


import android.graphics.Color;

public class Controller {
    private MainActivity mainActivity;


    public Controller(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void startTask(int tv) {
        Task asyncTask = new Task();
        asyncTask.setController(this);
        asyncTask.execute(tv);
    }

    public void updateTvLeft(int color) {
        mainActivity.updateTvLeft(color);
    }

    public void updateTvRight(int color) {
        mainActivity.updateTvRight(color);
    }

    public void clearTvLeft() {
        mainActivity.updateTvLeft(Color.TRANSPARENT);
    }

    public void clearTvRight() {
        mainActivity.updateTvRight(Color.TRANSPARENT);
    }
}

package com.example.benjo.lab6b;


public class Controller {
    private MainActivity mainActivity;


    public Controller(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void startTask() {
        Colorize colorizeTask = new Colorize();
        colorizeTask.setController(this);
        colorizeTask.run();
    }

    public void updateTvLeft(int color) {
        mainActivity.updateTvLeft(color);
    }

    public void updateTvRight(int color) {
        mainActivity.updateTvRight(color);
    }

    public void runOnUiThread(Colorize.SetColor setColor) {
        mainActivity.runOnUiThread(setColor);
    }
}

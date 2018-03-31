package com.example.benjo.lab6a;

import android.graphics.Color;
import android.os.AsyncTask;

import java.util.Random;


public class Task extends AsyncTask<Integer, Integer, Integer> {
    private Controller controller;

    @Override
    protected Integer doInBackground(Integer... params) {
        Random rand = new Random();
        int tv = params[0]; // tvLeft or tvRight?
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int r = rand.nextInt(256);
            int g = rand.nextInt(256);
            int b = rand.nextInt(256);
            int color = Color.rgb(r, g, b);
            publishProgress(color, tv);
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        switch (values[1]) {
            case 0:
                controller.updateTvLeft(values[0]);
                break;
            case 1:
                controller.updateTvRight(values[0]);
                break;
        }
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Integer integer) {
        controller.clearTvLeft();
        controller.clearTvRight();
        super.onPostExecute(integer);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
}



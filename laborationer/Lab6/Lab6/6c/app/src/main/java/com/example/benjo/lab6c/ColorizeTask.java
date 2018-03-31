package com.example.benjo.lab6c;

import android.graphics.Color;
import android.os.AsyncTask;

import java.util.Random;


public class ColorizeTask extends AsyncTask<Integer, Integer, Integer> {
    private MyTextView myTextView;

    @Override
    protected Integer doInBackground(Integer... params) {
        Random rand = new Random();
        for (int i = 0; i < 30; i++) {
            try {
                Thread.sleep(50); // 50ms = 0.005 seconds (period = 20x/second)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int r = rand.nextInt(256);
            int g = rand.nextInt(256);
            int b = rand.nextInt(256);
            int color = Color.rgb(r, g, b);
            publishProgress(color);
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        myTextView.setBackgroundColor(values[0]);
        super.onProgressUpdate(values);
    }


    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
    }


    public void setReference(MyTextView myTextView) {
        this.myTextView = myTextView;
    }
}



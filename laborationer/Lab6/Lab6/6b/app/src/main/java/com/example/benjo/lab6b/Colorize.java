package com.example.benjo.lab6b;

import android.graphics.Color;

import java.util.Random;


public class Colorize extends Thread {
    private Controller controller;

    public void run() {
        Random rand = new Random();
        int r = rand.nextInt(256);
        int g = rand.nextInt(256);
        int b = rand.nextInt(256);
        int color = Color.rgb(r, g, b);
        controller.runOnUiThread(new SetColor(color));
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.run();
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public class SetColor implements Runnable {
        private int color;

        public SetColor(int color) {
            this.color = color;
        }

        public void run() {
            if (color != 0) {
                controller.updateTvLeft(color);
                controller.updateTvRight(color);
            }

        }
    }
}

package com.example.benjo.friendsintheworld.listeners;

import android.util.Log;

import com.example.benjo.friendsintheworld.Controller.Controller;


public class ReceiveListener extends Thread {
    private Controller controller;
    private boolean receiver;

    public ReceiveListener(Controller controller) {
        this.controller = controller;
    }

    public void startThread() {
        receiver = true;
        start();
    }

    @Override
    public void run() {
        while (receiver) {
            try {
                controller.receive();
            } catch (Exception e) {
                receiver = false;
                e.printStackTrace();
                Log.i("RECEIVE LISTENER", "EXCEPTION -> SETTING receiveListener to null");
            }
        }
    }

    public void stopThread() {
        receiver = false;
    }
}

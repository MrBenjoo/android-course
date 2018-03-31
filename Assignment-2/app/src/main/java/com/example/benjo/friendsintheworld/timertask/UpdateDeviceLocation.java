package com.example.benjo.friendsintheworld.timertask;



import com.example.benjo.friendsintheworld.Controller.Controller;


public class UpdateDeviceLocation extends java.util.TimerTask {
    private Controller controller;

    public UpdateDeviceLocation(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void run() {
        boolean noMembers = controller.isMembersEmpty();
        if(!noMembers) {
            controller.updateDeviceLocation();
        }
    }
}

package com.example.benjo.l8retainedfragment;

import android.os.Bundle;

import f8.Expression;

/**
 * Created by tsroax on 2014-09-30.
 */
public class Controller {
    private static final String TCP_FRAGMENT_TAG = "TCPFragment";
    private MainActivity activity;
    private MainFragment mainFragment;
    private TCPFragment connection;
    private boolean connected = false;
    private ReceiveListener listener;


    public Controller(MainActivity activity, MainFragment mainFragment, TCPFragment connection) {
        this.activity = activity;
        this.mainFragment = mainFragment;
        this.connection = connection;
        this.listener = new RL();

        mainFragment.setController(this);

        connection.setIp("195.178.227.53");
        connection.setPort(9384);
        connection.setListener(listener);
        connection.startThread();
    }

    public void connectClicked() {
        connection.connect();
    }

    public void disconnectClicked() {
        if (connected) {
            connection.disconnect();
        }
    }

    public void sendClicked() {
        char operation;
        try {
            int number1 = Integer.parseInt(mainFragment.getNbr1());
            int number2 = Integer.parseInt(mainFragment.getNnr2());
            String operStr = mainFragment.getOperation();
            if (operStr.length() > 0)
                operation = operStr.charAt(0);
            else
                operation = '?';
            Expression exp = new Expression(number1, operation, number2);
            connection.send(exp);
        } catch (NumberFormatException e) {
            listener.newMessage("Bad arguments: " + mainFragment.getNbr1() +
                    ", " + mainFragment.getNnr2());
        }
    }

    private class RL implements ReceiveListener {
        public void newMessage(final String answer) {
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    String message = answer;
                    Exception e = connection.getException();
                    if ("CONNECTED".equals(answer)) {
                        mainFragment.setConnectEnabled(false);
                        mainFragment.setDisconnectEnabled(true);
                        mainFragment.setSendEnabled(true);
                        connected = true;
                    } else if ("CLOSED".equals(answer)) {
                        mainFragment.setConnectEnabled(true);
                        mainFragment.setDisconnectEnabled(false);
                        mainFragment.setSendEnabled(false);
                        connected = false;
                    } else if ("EXCEPTION".equals(answer) && e != null) {
                        message = e.toString();
                    }
                    mainFragment.setResult(message);
                }
            });
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("connected", connected);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        boolean wasConnected = savedInstanceState.getBoolean("connected");
        if (wasConnected) {
            connectClicked();
        }
    }

}

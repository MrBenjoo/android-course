package com.example.benjo.friendsintheworld.Service;


import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.benjo.friendsintheworld.RunOnThread.RunOnThread;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class TCPService extends Service {
    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;
    private RunOnThread thread;
    private Receive receive;
    private Buffer<String> receiveBuffer;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        thread = new RunOnThread();
        receiveBuffer = new Buffer<>();
        return Service.START_STICKY; // return Service.START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new LocalService();
    }

    public void connect() {
        thread.start();
        thread.execute(new Connect());
    }

    public void disconnect() {
        thread.execute(new Disconnect());
    }

    public void send(String jsonText) {
        thread.execute(new Send(jsonText));
    }

    public String receive() throws InterruptedException {
        return receiveBuffer.get();
    }

    private class Connect implements Runnable {

        @Override
        public void run() {
            try {
                socket = new Socket("195.178.227.53", 7117);
                input = new DataInputStream(socket.getInputStream());
                output = new DataOutputStream(socket.getOutputStream());
                output.flush();
                receive = new Receive();
                receive.start();
                Log.i("SERVICE", "CONNECTED");
            } catch (Exception e) {
                Log.i("SERVICE", "EXCEPTION");
                Log.e("SERVICE", e.getMessage());
            }
        }
    }

    private class Disconnect implements Runnable {
        @Override
        public void run() {
            try {
                if (input != null) {
                    input.close();
                }
                if (output != null) {
                    output.close();
                }
                if (socket != null) {
                    socket.close();
                }
                receive = null;
                thread.stop();
                Log.i("DISCONNECT", "DISCONNECTED");
            } catch (Exception e) {
                Log.i("DISCONNECT", "EXCEPTION");
            }
        }
    }

    private class Receive extends Thread {
        @Override
        public void run() {
            try {
                while (receive != null) {
                    String response = input.readUTF();
                    receiveBuffer.put(response);
                }
            } catch (Exception exception) {
                exception.printStackTrace();
                Log.i("SERVICE", "EXCEPTION -- SETTING RECEIVE TO NULL");
                receive = null;
            }
        }
    }

    private class Send implements Runnable {
        private String jsonText;

        Send(String jsonText) {
            this.jsonText = jsonText;
        }

        @Override
        public void run() {
            try {
                if (socket != null) {
                    Log.i("SERVICE", "SENDING " + jsonText + " TO SERVER");
                    output.writeUTF(jsonText);
                    output.flush();
                }
            } catch (IOException e) {
                Log.i("SEND", "EXCEPTION");
            }
        }
    }

    public class LocalService extends Binder {
        public TCPService getService() {
            return TCPService.this;
        }
    }

}

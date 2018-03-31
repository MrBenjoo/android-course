package com.example.benjo.l8retainedfragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import f8.Expression;

/**
 * Created by tsroax on 2014-09-30.
 */

public class TCPFragment extends Fragment {
    private RunOnThread thread;
    private Receive receive;
    private ReceiveListener listener;
    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private InetAddress address;
    private int connectionPort;
    private String ip;
    private Exception exception;

    public TCPFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void startThread() {
        thread = new RunOnThread();
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPort(int port) {
        this.connectionPort = port;
    }

    public void setListener(ReceiveListener listener) {
        this.listener = listener;
    }

    public void connect() {
        thread.start();
        thread.execute(new Connect());
    }

    public void disconnect() {
        thread.execute(new Disconnect());
    }

    public void send(Expression expression) {
        thread.execute(new Send(expression));
    }

    private class Receive extends Thread {
        public void run() {
            String result;
            try {
                while (receive != null) {
                    result = (String) input.readObject();
                    listener.newMessage(result);
                }
            } catch (Exception e) { // IOException, ClassNotFoundException
                receive = null;
            }
        }
    }

    public Exception getException() {
        Exception result = exception;
        exception = null;
        return result;
    }

    private class Connect implements Runnable {
        public void run() {
            try {
                Log.d("TCPFragment","Connect-run");
                address = InetAddress.getByName(ip);
                Log.d("TCPFragment-Connect","Skapar socket");
                socket = new Socket(address, connectionPort);
                input = new ObjectInputStream(socket.getInputStream());
                output = new ObjectOutputStream(socket.getOutputStream());
                output.flush();
                Log.d("TCPFragment-Connect","Strömmar klara");
                listener.newMessage("CONNECTED");
                receive = new Receive();
                receive.start();
            } catch (Exception e) { // SocketException, UnknownHostException
                Log.d("TCPFragment-Connect","exepction");
                exception = e;
                listener.newMessage("EXCEPTION");
            }
        }
    }

    public class Disconnect implements Runnable {
        public void run() {
            try {
                if (socket != null)
                    socket.close();
                if (input != null)
                    input.close();
                if (output != null)
                    output.close();
                thread.stop();
                listener.newMessage("CLOSED");
            } catch(IOException e) {
                exception = e;
                Log.d("TCPFragment", " disconnect - exception");
                listener.newMessage("EXCEPTION");
            }
        }
    }

    public class Send implements Runnable {
        private Expression exp;

        public Send(Expression exp) {
            this.exp = exp;
        }

        public void run() {
            try {
                output.writeObject(exp);
                output.flush();
            } catch (IOException e) {
                exception = e;
                Log.d("TCPFragment", " Send - exception");
                listener.newMessage("EXCEPTION");
            }
        }
    }
}

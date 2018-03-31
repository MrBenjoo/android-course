package com.example.benjo.l8network1;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class LoadBitmap extends Thread {
    private BitmapListener listener;
    private String urlString;

    public LoadBitmap(BitmapListener listener, String url) {
        if(listener!=null) {
            this.listener = listener;
            this.urlString = url;
            start();
        }
    }

    @Override
    public void run() {
        Bitmap result = null;
        URL url;
        URLConnection connection;
        InputStream input=null;
        try {
            url = new URL(urlString);
            connection = url.openConnection();
            input = connection.getInputStream();
            result = BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            e.printStackTrace();
        }  finally {
            if(input!=null)
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        onPostExecute(result);

    }

    private void onPostExecute(Bitmap result) {
        listener.bitmapLoaded(result);
    }

    public interface BitmapListener {
        public void bitmapLoaded(Bitmap bitmap);
    }


}

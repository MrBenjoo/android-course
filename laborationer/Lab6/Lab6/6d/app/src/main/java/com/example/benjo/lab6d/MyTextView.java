package com.example.benjo.lab6d;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import java.util.Random;

public class MyTextView extends AppCompatTextView {
    Random rand = new Random();

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        int r = rand.nextInt(256);
        int g = rand.nextInt(256);
        int b = rand.nextInt(256);
        paint.setColor(Color.rgb(r, g, b));
        canvas.drawPaint(paint);
        super.onDraw(canvas);
    }

    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void colorize() {
        MyThread thread = new MyThread();
        thread.run();
    }

    @Override
    public void setBackgroundColor(@ColorInt int color) {
        super.setBackgroundColor(color);
    }

    private void update() {
        post(new Runnable() {
            public void run() {
                invalidate();
            }
        });
    }

    private class MyThread extends Thread {
        @Override
        public void run() {
            update();
            super.run();
        }
    }
}

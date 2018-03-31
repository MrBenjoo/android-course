package com.example.benjo.lab6c;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {
    private MyTextView myTextView;
    private Button btnLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        initListener();
    }

    private void initComponents() {
        myTextView = (MyTextView) findViewById(R.id.myTextView);
        btnLeft = (Button) findViewById(R.id.btnLeft);
    }

    private void initListener() {
        btnLeft.setOnClickListener(new ButtonListener());
    }

    private class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            myTextView.colorize();
        }
    }
}

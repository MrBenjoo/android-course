package com.example.benjo.lab6a;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView tvLeft;
    private TextView tvRight;
    private Button btnLeft;
    private Button btnRight;
    private Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        initListener();
        initController();
    }

    private void initComponents() {
        tvLeft = (TextView) findViewById(R.id.tVLeft);
        tvRight = (TextView) findViewById(R.id.tvRight);
        btnLeft = (Button) findViewById(R.id.btnLeft);
        btnRight = (Button) findViewById(R.id.btnRight);
    }

    private void initListener() {
        btnLeft.setOnClickListener(new ButtonListener());
        btnRight.setOnClickListener(new ButtonListener());
    }

    private void initController() {
        controller = new Controller(this);
    }

    public void updateTvLeft(int color) {
        tvLeft.setBackgroundColor(color);
    }

    public void updateTvRight(int color) {
        tvRight.setBackgroundColor(color);
    }

    private class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.btnLeft:
                    controller.startTask(0); // 0 represents tvLeft and 1 tvRight
                    break;

                case R.id.btnRight:
                    controller.startTask(1);
                    break;
            }

        }
    }

}


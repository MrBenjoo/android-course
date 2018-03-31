package com.example.benjo.a2a;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class HowToActivity extends AppCompatActivity {
    private TextView tV_whatToDo;
    private TextView tV_content;
    private Button btn_prev;
    private Button btn_next;
    private String whatToDo;
    private String content;
    private Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeComponents();
        registerListener();
        controller = new Controller(this);
    }

    /*
    Finds and sets references to each UI component
     */
    private void initializeComponents()
    {
        tV_whatToDo = (TextView)findViewById(R.id.tv_whatToDo);
        tV_content = (TextView)findViewById(R.id.tV_content);
        ImageView iV_androidLauncher = (ImageView)findViewById(R.id.imageView_android);
        btn_prev = (Button)findViewById(R.id.btn_prev);
        btn_next = (Button)findViewById(R.id.btn_next);
    }


    private void registerListener()
    {
        OnClickListener buttonListener = new ButtonListener();
        btn_prev.setOnClickListener(buttonListener);
        btn_next.setOnClickListener(buttonListener);
    }

    /*
    Changes the instruction text
     */
    public void setWhatToDo(String whatToDo) {
        this.whatToDo = whatToDo;
        tV_whatToDo.setText(whatToDo);
    }

    /*
    Changes the content text
     */
    public void setContent(String content) {
        this.content = content;
        tV_content.setText(content);
    }

    private class ButtonListener implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.btn_prev :
                    controller.previousClick();
                    break;
                case R.id.btn_next :
                    controller.nextClick();
                    break;
            }
        }
    }
}

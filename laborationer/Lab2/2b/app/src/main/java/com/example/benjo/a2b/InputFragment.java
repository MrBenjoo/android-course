package com.example.benjo.a2b;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/*
UI class which communicates with the controller
 */
public class InputFragment extends Fragment {
    private TextView tV_whatToDo;
    private TextView tV_content;
    private Button btn_prev;
    private Button btn_next;
    private String whatToDo;
    private String content;
    private Controller controller;

    /*
    Renders the fragment_input.xml file to view objects and initializes them
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view =  inflater.inflate(R.layout.fragment_input, container, false);
        initializeComponents(view);
        return view;
    }

    private void initializeComponents(View view)
    {
        tV_whatToDo = (TextView)view.findViewById(R.id.tv_whatToDo);
        tV_content = (TextView)view.findViewById(R.id.tV_content);
        ImageView iV_androidLauncher = (ImageView)view.findViewById(R.id.imageView_android);
        btn_prev = (Button)view.findViewById(R.id.btn_prev);
        btn_next = (Button)view.findViewById(R.id.btn_next);
        registerListener();
    }

    private void registerListener()
    {
        View.OnClickListener buttonListener = new ButtonListener();
        btn_prev.setOnClickListener(buttonListener);
        btn_next.setOnClickListener(buttonListener);
    }

    /*
    Changes the whatToDo text
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

    /*
    Initializes the controller
     */
    public void setController(Controller controller)
    {
        this.controller = controller;
    }

    private class ButtonListener implements View.OnClickListener
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

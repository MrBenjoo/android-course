package com.example.benjo.a2c;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class InputFragment extends Fragment {
    public static final String TAG = "SecondFragment";
    private Button btn_click;
    private int click_counter = 0;
    Controller controller;

    /*
    Renders the fragment_input.xml by creating view objects and makes references to them.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_input, container, false); // Inflate the layout for this fragment
        initializeComponents(view);
        registerListeners();
        return view;
    }

    private void initializeComponents(View view)
    {
        btn_click = (Button)view.findViewById(R.id.btn_click);
    }

    private void registerListeners() {
        OnClickListener buttonListener = new ButtonListener();
        btn_click.setOnClickListener(buttonListener);
    }

    private class ButtonListener implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            controller.setText(click_counter++);
        }
    }

    public void setController(Controller controller)
    {
        this.controller = controller;
    }

}

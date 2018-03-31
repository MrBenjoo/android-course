package com.example.benjo.a2c;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ViewerFragment extends Fragment {
    public static final String TAG = "FirstFragment";
    TextView tV_click;

    /*
    Renders the fragment_viewer.xml by creating view objects and makes references to them
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_viewer, container, false); //
        initializeComponents(view);
        return view;
    }

    private void initializeComponents(View view) {
        tV_click = (TextView)view.findViewById(R.id.tV_nbrOfClicks);
    }

    /*
    Sets text to number of mouse clicks
     */
    public void changeText(String text) {
        tV_click.setText(text);
    }
}

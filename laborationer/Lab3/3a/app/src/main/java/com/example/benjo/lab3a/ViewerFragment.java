package com.example.benjo.lab3a;


import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ViewerFragment extends Fragment {
    private TextView textView;

    public ViewerFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_viewer, container, false);
        initComponents(rootView);
        return rootView;
    }

    private void initComponents(View rootView) {
        textView = (TextView) rootView.findViewById(R.id.tvColor);
        textView.setBackgroundColor(Color.RED);
    }

    public void setTvColor(int tvColor) {
        textView.setBackgroundColor(tvColor);
    }

}

package com.example.benjo.lab3b;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class WhatToDoFragment extends Fragment {
    private TextView tvWhatToDo;
    private TextView tvContent;


    public WhatToDoFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_instructions, container, false);
        initTextViews(rootView);
        return rootView;
    }

    private void initTextViews(View rootView) {
        tvWhatToDo = (TextView) rootView.findViewById(R.id.tvContentWhatToDo);
        tvContent = (TextView) rootView.findViewById(R.id.tvContent);
    }

    public void setWhatToDo(String whatToDo) {
        tvWhatToDo.setText(whatToDo);
    }

    public void setContent(String content) {
        tvContent.setText(content);
    }

}

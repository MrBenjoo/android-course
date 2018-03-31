package com.example.benjo.lab3c;


import android.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


public class WhatToDoFragment extends Fragment {
    private ArrayList<Instruction> whatToDoList;
    private Controller controller;


    public WhatToDoFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_whattodo, container, false);
        initArrayList();
        initListView(rootView);
        return rootView;
    }

    private void initArrayList() {
        whatToDoList = new ArrayList<>();
        Resources res = getResources();
        whatToDoList.add(new Instruction(res.getString(R.string.what_to_do), res.getString(R.string.content)));
        whatToDoList.add(new Instruction(res.getString(R.string.what_to_do2), res.getString(R.string.content2)));
        whatToDoList.add(new Instruction(res.getString(R.string.what_to_do3), res.getString(R.string.content3)));
    }

    private void initListView(View rootView) {
        ListView mListView = (ListView) rootView.findViewById(R.id.listView);
        mListView.setAdapter(new WhatToDoAdapter(getContext(), whatToDoList));
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                controller.onListItemClick(whatToDoList.get(position).getWhatToDo(), whatToDoList.get(position).getContent());
            }
        });
    }


    public void setController(Controller controller) {
        this.controller = controller;
    }

}

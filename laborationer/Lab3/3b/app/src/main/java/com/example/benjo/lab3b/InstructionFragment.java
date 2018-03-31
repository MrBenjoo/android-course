package com.example.benjo.lab3b;


import android.content.res.Resources;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


public class InstructionFragment extends Fragment {
    ArrayList<Instruction> instructionArrayList;
    private Controller controller;


    public InstructionFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        initArrayList();
        initListView(rootView);
        return rootView;
    }

    private void showFirstInstruction() {
        controller.changeInstruction(instructionArrayList.get(0).getWhatToDo(),instructionArrayList.get(0).getContent());
    }

    private void initArrayList() {
        instructionArrayList = new ArrayList<>();
        Resources res = getResources();
        instructionArrayList.add(new Instruction(res.getString(R.string.what_to_do), res.getString(R.string.content)));
        instructionArrayList.add(new Instruction(res.getString(R.string.what_to_do2), res.getString(R.string.content2)));
        instructionArrayList.add(new Instruction(res.getString(R.string.what_to_do3), res.getString(R.string.content3)));
    }

    private void initListView(View rootView) {
        ListView mListView = (ListView)rootView.findViewById(R.id.listView);
        mListView.setAdapter(new InstructionAdapter(getContext(), instructionArrayList));
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                controller.changeInstruction(instructionArrayList.get(position).getWhatToDo(), instructionArrayList.get(position).getContent());
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) { // called after onCreateView
        super.onActivityCreated(savedInstanceState);
        showFirstInstruction(); // update when the view has been created
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

}

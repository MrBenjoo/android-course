package com.example.benjo.lab3c;


import android.app.Fragment;


public class Controller {
    private InstructionFragment instructionFragment;
    private WhatToDoFragment whatToDoFragment;
    private MainActivity mainActivity;

    public Controller(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        initFragments();
        setFragment(whatToDoFragment, false);
    }

    private void setFragment(Fragment fragment, boolean backStack) {
        mainActivity.setFragment(fragment, backStack);
    }

    private void initFragments() {
        instructionFragment = new InstructionFragment();
        whatToDoFragment = new WhatToDoFragment();
        whatToDoFragment.setController(this);
    }

    public void onListItemClick(String whatToDo, String content) {
        instructionFragment.setInstruction(whatToDo, content);
        mainActivity.setFragment(instructionFragment, true);
    }
}

package com.example.benjo.lab3b;


public class Controller {
    private WhatToDoFragment whatToDoFragment;
    private InstructionFragment instructionFragment;

    public Controller(WhatToDoFragment whatToDoFragment, InstructionFragment instructionFragment) {
        this.whatToDoFragment = whatToDoFragment;
        this.instructionFragment = instructionFragment;
        instructionFragment.setController(this);
    }

    public void changeInstruction(String whatToDo, String content) {
        whatToDoFragment.setWhatToDo(whatToDo);
        whatToDoFragment.setContent(content);
    }
}

package com.example.benjo.a2b;

import android.content.res.Resources;


public class Controller {

    private Instruction[] instructions = new Instruction[3];
    private int index = 0;
    private InputFragment ui;

    /*
    This constructor initializes the InputFragment class by making a reference to it. It also initializes the instructions array
    and the controller in InputFragment
     */
    public Controller(InputFragment ui)
    {
        this.ui = ui;
        initializeResources();
        ui.setController(this);
    }

    private void initializeResources()
    {
        Resources res = ui.getResources();
        String whatToDo = res.getString(R.string.what_to_do);
        String content = res.getString(R.string.content);
        instructions[0] = new Instruction(whatToDo, content);
        instructions[1] = new Instruction(res.getString(R.string.what_to_do2), res.getString(R.string.content2));
        instructions[2] = new Instruction(res.getString(R.string.what_to_do3), res.getString(R.string.content3));
    }

    /*
    Method that handles previous click
     */
    public void previousClick()
    {
        index--;
        if(index < 0)
        {
            index = instructions.length-1;
        }
        ui.setWhatToDo(instructions[index].getWhatToDo());
        ui.setContent(instructions[index].getContent());
    }

    /*
    Method that handles next click
     */
    public void nextClick()
    {
        index++;
        if(index > 2)
        {
            index = 0;
        }
        ui.setWhatToDo(instructions[index].getWhatToDo());
        ui.setContent(instructions[index].getContent());
    }
}

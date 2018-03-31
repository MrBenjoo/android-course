package com.example.benjo.lab3c;

/*
This class is used in the controller class to store the instructions in an array
 */
public class Instruction {
    String whatToDo;
    String content;

    public Instruction(String whatToDo, String content)
    {
        this.whatToDo = whatToDo;
        this.content = content;
    }

    public String getWhatToDo()
    {
        return this.whatToDo;
    }

    public String getContent()
    {
        return this.content;
    }


}

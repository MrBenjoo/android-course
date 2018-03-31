package com.example.benjo.lab3b;

public class Instruction {
    private String whatToDo;
    private String content;

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

package com.example.benjo.a2c;


public class Controller {
    private ViewerFragment viewerFragment;

    /*
    This constructor initializes the ViewerFragment class by making a reference to it.
     */
    public Controller(ViewerFragment viewerFragment)
    {
        this.viewerFragment = viewerFragment;
    }

    /*
    Sets text to number of mouse clicks
     */
    public void setText(int click_counter)
    {
        String text = String.valueOf(click_counter);
        viewerFragment.changeText(text);
    }
}

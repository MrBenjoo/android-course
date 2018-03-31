package com.example.benjo.rspstaticfragment;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {
    private RPSController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initController();
        startProgram();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initController() {
        FragmentManager fm = getFragmentManager();
        RPSPlayer computerPlayer = new RPSPlayer();
        ViewerFragment viewer = (ViewerFragment)fm.findFragmentById(R.id.viewer_fragment);
        InputFragment input = (InputFragment)fm.findFragmentById(R.id.input_fragment);
        controller = new RPSController(computerPlayer, viewer, input);
    }

    private void startProgram() {
        controller.newGame();
    }

}

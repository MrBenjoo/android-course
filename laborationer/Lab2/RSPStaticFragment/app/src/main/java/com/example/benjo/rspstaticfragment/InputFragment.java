package com.example.benjo.rspstaticfragment;



import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

public class InputFragment extends Fragment {
    private RPSController controller;
    private Button btnRock;
    private Button btnPaper;
    private Button btnScissors;
    private Button btnNewGame;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_input, container, false);
        initializeComponents(view);
        registerListeners();
        return view;
    }

    private void initializeComponents(View view) {
        btnRock = (Button)view.findViewById(R.id.btnRock);
        btnPaper = (Button)view.findViewById(R.id.btnPaper);
        btnScissors = (Button)view.findViewById(R.id.btnScissors);
        btnNewGame = (Button)view.findViewById(R.id.btnNewGame);
    }

    private void registerListeners() {
        OnClickListener choiceButtons = new ChoiceButtonListener();
        OnClickListener gameButtons = new GameButtonListener();
        btnRock.setOnClickListener(choiceButtons);
        btnPaper.setOnClickListener(choiceButtons);
        btnScissors.setOnClickListener(choiceButtons);
        btnNewGame.setOnClickListener(gameButtons);
    }

    public void setController(RPSController controller) {
        this.controller = controller;
    }

    public void enableChoiceButtons(boolean enabled) {
        btnRock.setEnabled(enabled);
        btnPaper.setEnabled(enabled);
        btnScissors.setEnabled(enabled);
    }

    private class ChoiceButtonListener implements OnClickListener {
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.btnRock : controller.newChoice(RPSController.ROCK); break;
                case R.id.btnPaper : controller.newChoice(RPSController.PAPER); break;
                case R.id.btnScissors : controller.newChoice(RPSController.SCISSORS); break;
            }
        }
    }

    private class GameButtonListener implements OnClickListener {
        public void onClick(View v) {
            controller.newGame();
        }
    }
}
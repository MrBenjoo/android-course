package com.example.benjo.rspstaticfragment;


import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewerFragment extends Fragment {
    private TextView tvHumanPoints;
    private TextView tvComputerPoints;
    private ImageView ivHumanChoice;
    private ImageView ivComputerChoice;
    private Drawable winner, loser;
    private Drawable[] humanImages = new Drawable[4];
    private Drawable[] computerImages = new Drawable[4];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_viewer, container, false);
        initializeComponents(view);
        initializeResources();
        return view;
    }

    private void initializeComponents(View view) {
        tvHumanPoints = (TextView)view.findViewById(R.id.tvHumanPoints);
        tvComputerPoints = (TextView)view.findViewById(R.id.tvComputerPoints);
        ivHumanChoice = (ImageView)view.findViewById(R.id.ivHumanChoice);
        ivComputerChoice = (ImageView)view.findViewById(R.id.ivComputerChoice);
    }

    private void initializeResources() {
        Activity activity = getActivity();
        Drawable empty = ContextCompat.getDrawable(activity, R.drawable.empty);
        humanImages[RPSController.ROCK] = ContextCompat.getDrawable(activity, R.drawable.rockleft);
        humanImages[RPSController.PAPER] = ContextCompat.getDrawable(activity, R.drawable.paperleft);
        humanImages[RPSController.SCISSORS] = ContextCompat.getDrawable(activity, R.drawable.scissorsleft);
        humanImages[RPSController.EMPTY] = empty;
        computerImages[RPSController.ROCK] = ContextCompat.getDrawable(activity, R.drawable.rockright);
        computerImages[RPSController.PAPER] = ContextCompat.getDrawable(activity, R.drawable.paperright);
        computerImages[RPSController.SCISSORS] = ContextCompat.getDrawable(activity, R.drawable.scissorsright);
        computerImages[RPSController.EMPTY] = empty;
        winner = ContextCompat.getDrawable(activity, R.drawable.winner);
        loser = ContextCompat.getDrawable(activity, R.drawable.loser);
    }

    public void setHumanPoints(int points) {
        tvHumanPoints.setText(String.valueOf(points));
    }

    public void setComputerPoints(int points) {
        tvComputerPoints.setText(String.valueOf(points));
    }

    public void setHumanChoice(int choice) {
        switch(choice) {
            case RPSController.ROCK : ivHumanChoice.setImageResource(R.drawable.rockleft); break;
            case RPSController.PAPER: ivHumanChoice.setImageResource(R.drawable.paperleft); break;
            case RPSController.SCISSORS: ivHumanChoice.setImageResource(R.drawable.scissorsleft); break;
        }
 //       ivHumanChoice.setImageDrawable(humanImages[choice]);
    }

    public void setComputerChoice(int choice) {
        switch(choice) {
            case RPSController.ROCK : ivComputerChoice.setImageResource(R.drawable.rockright); break;
            case RPSController.PAPER: ivComputerChoice.setImageResource(R.drawable.paperright); break;
            case RPSController.SCISSORS: ivComputerChoice.setImageResource(R.drawable.scissorsright); break;
        }
//        ivComputerChoice.setImageDrawable(computerImages[choice]);
    }

    public void humanWinner() {
        LayerDrawable theWinner = new LayerDrawable(new Drawable[]{ivHumanChoice.getDrawable(),winner});
        LayerDrawable theLoser = new LayerDrawable(new Drawable[]{ivComputerChoice.getDrawable(),loser});
        ivHumanChoice.setImageDrawable(theWinner);
        ivComputerChoice.setImageDrawable(theLoser);
    }

    public void computerWinner() {
        LayerDrawable theWinner = new LayerDrawable(new Drawable[]{ivComputerChoice.getDrawable(),winner});
        LayerDrawable theLoser = new LayerDrawable(new Drawable[]{ivHumanChoice.getDrawable(),loser});
        ivComputerChoice.setImageDrawable(theWinner);
        ivHumanChoice.setImageDrawable(theLoser);
    }

}

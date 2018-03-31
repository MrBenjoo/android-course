package com.example.benjo.friendsintheworld.Dialogs;


import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;


import com.example.benjo.friendsintheworld.Activities.MainActivity;
import com.example.benjo.friendsintheworld.R;

import java.util.ArrayList;

import static android.content.DialogInterface.BUTTON_NEGATIVE;
import static android.content.DialogInterface.BUTTON_POSITIVE;


public class DialogGroups extends DialogFragment implements DialogInterface.OnClickListener {
    private MainActivity mainActivity;
    private String[] groups;
    private String group;


    public DialogGroups() { /* Required empty public constructor */ }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mainActivity = (MainActivity) getActivity();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = getResources().getString(R.string.map_popup_title);
        String cancel = getResources().getString(R.string.popup_cancel);
        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
        builder.setTitle(title)
                .setPositiveButton("OK", this)
                .setNegativeButton(cancel, this);
        addGroupsToList();
        builder.setSingleChoiceItems(groups, 0, this);
        return builder.create();
    }

    private void addGroupsToList() {
        ArrayList<String> myGroups = mainActivity.getMyGroups();
        groups = new String[myGroups.size()];
        for (int i = 0; i < myGroups.size(); i++) {
            groups[i] = myGroups.get(i);
        }
    }


    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {

            case BUTTON_POSITIVE:
                if (group != null) {
                    mainActivity.showMarkers(group);
                    mainActivity.showText("Show markers for group: " + group);
                }
                dialog.cancel();
                break;

            case BUTTON_NEGATIVE:
                dialog.cancel();
                break;

            default:
                group = groups[which];
                break;
        }
    }

}

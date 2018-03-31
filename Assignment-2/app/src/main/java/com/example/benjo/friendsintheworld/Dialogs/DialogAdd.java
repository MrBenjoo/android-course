package com.example.benjo.friendsintheworld.Dialogs;


import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.benjo.friendsintheworld.Activities.MainActivity;
import com.example.benjo.friendsintheworld.R;

import static android.content.DialogInterface.BUTTON_NEGATIVE;
import static android.content.DialogInterface.BUTTON_POSITIVE;


public class DialogAdd extends DialogFragment implements DialogInterface.OnClickListener {
    private TextInputEditText groupDialogNick;
    private TextInputEditText groupDialogGroup;
    private MainActivity mainActivity;
    private View dialogView;

    public DialogAdd() { /* Requires empty public constructor */ }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mainActivity = (MainActivity) getActivity();
        initializeComponents();
    }

    private void initializeComponents() {
        dialogView = LayoutInflater.from(mainActivity).inflate(R.layout.dialog_add_group, (ViewGroup) mainActivity.findViewById(android.R.id.content), false);
        groupDialogNick = (TextInputEditText) dialogView.findViewById(R.id.dlg_add_name);
        groupDialogGroup = (TextInputEditText) dialogView.findViewById(R.id.dlg_add_group);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = mainActivity.getResources().getString(R.string.drawer_popup_title);
        String cancel = mainActivity.getResources().getString(R.string.popup_cancel);
        String create = mainActivity.getResources().getString(R.string.drawer_popup_add);

        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
        builder.setView(dialogView)
                .setTitle(title)
                .setCancelable(false)
                .setPositiveButton(create, this)
                .setNegativeButton(cancel, this);
        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {

            case BUTTON_POSITIVE:
                positiveClick();
                dialog.cancel();
                break;

            case BUTTON_NEGATIVE:
                dialog.cancel();
                break;
        }
    }

    private void positiveClick() {
        String name = groupDialogNick.getText().toString();
        String group = groupDialogGroup.getText().toString();
        if (!name.isEmpty() && !group.isEmpty()) {
            mainActivity.register(name, group);
        }
    }

}

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
import android.widget.Button;

import com.example.benjo.friendsintheworld.Activities.MainActivity;
import com.example.benjo.friendsintheworld.R;

import static android.content.DialogInterface.BUTTON_NEGATIVE;
import static android.content.DialogInterface.BUTTON_POSITIVE;

public class DialogJoin extends DialogFragment implements DialogInterface.OnClickListener, View.OnClickListener {
    private TextInputEditText etGroup;
    private String group;
    private MainActivity mainActivity;
    private View dialogView;

    public DialogJoin() { /* Requires empty public constructor */ }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mainActivity = (MainActivity) getActivity();
        initializeComponents();
    }

    private void initializeComponents() {
        dialogView = LayoutInflater.from(mainActivity).inflate(R.layout.dialog_join_group, (ViewGroup) mainActivity.findViewById(android.R.id.content), false);
        etGroup = (TextInputEditText) dialogView.findViewById(R.id.dlg_join_group);
        Button btnUnsub = (Button) dialogView.findViewById(R.id.dlg_join_btn);
        btnUnsub.setOnClickListener(this);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            group = (String) savedInstanceState.get("group");
        }

        String title = getResources().getString(R.string.list_popup_title);
        String cancel = getResources().getString(R.string.popup_cancel);

        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
        builder.setView(dialogView)
                .setTitle(title)
                .setCancelable(false)
                .setPositiveButton("OK", this)
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
        String user = etGroup.getText().toString();
        if (!user.isEmpty()) {
            mainActivity.register(user, group);
        }
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("group", group);
    }

    @Override
    public void onClick(View v) {
        mainActivity.unsubscribe(group);
    }

}

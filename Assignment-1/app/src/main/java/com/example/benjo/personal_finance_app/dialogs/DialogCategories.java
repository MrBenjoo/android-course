package com.example.benjo.personal_finance_app.dialogs;


import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.ViewGroup;

import com.example.benjo.personal_finance_app.R;
import com.example.benjo.personal_finance_app.fragments.DataFragment;

import static android.content.DialogInterface.BUTTON_NEGATIVE;
import static android.content.DialogInterface.BUTTON_POSITIVE;


public class DialogCategories extends DialogFragment implements DialogInterface.OnClickListener {
    private final static String TAG = "DialogCategories";
    private String[] incomeCategories = new String[]{"Salary", "Other"};
    private String[] expenditureCategories = new String[]{"Food", "Leisure", "Travel", "Accommodation", "Other"};
    private String[] barcodeCategories = new String[]{"Food", "Leisure", "Travel", "Accommodation", "Salary", "Other (Income)", "Other (Expenditure)"};
    private String category = "";
    private int whichDialog;

    public interface DialogCategoriesListener {
        void onFinishEditDialog(String category);
    }

    public static DialogCategories newInstance(int whichDialog) {
        Bundle bundle = new Bundle();
        bundle.putInt("whichDialog", whichDialog);
        DialogCategories dialogCategories = new DialogCategories();
        dialogCategories.setArguments(bundle);
        return dialogCategories;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            category = (String) savedInstanceState.get("category");
        }
        whichDialog = getArguments().getInt("whichDialog");

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
        mBuilder.setView(getActivity().getLayoutInflater().inflate(R.layout.dialog_categories, (ViewGroup) getActivity().findViewById(android.R.id.content), false));
        if (whichDialog == DataFragment.DIALOG_INCOME_CATEGORIES) {
            mBuilder.setSingleChoiceItems(incomeCategories, 0, this);
        } else if (whichDialog == DataFragment.DIALOG_EXPENDITURE_CATEGORIES) {
            mBuilder.setSingleChoiceItems(expenditureCategories, 0, this);
        } else {
            mBuilder.setSingleChoiceItems(barcodeCategories, 0, this);
        }
        mBuilder.setPositiveButton("OK", this);
        mBuilder.setNegativeButton("Cancel", this);
        return mBuilder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int i) {

        switch (i) {
            case BUTTON_POSITIVE:
                if (!category.isEmpty()) {
                    sendBackResult();
                }
                dialog.cancel();
                break;

            case BUTTON_NEGATIVE:
                dialog.cancel();
                break;

            default:
                switch (whichDialog) {
                    case DataFragment.DIALOG_INCOME_CATEGORIES:
                        category = incomeCategories[i];
                        break;

                    case DataFragment.DIALOG_EXPENDITURE_CATEGORIES:
                        category = expenditureCategories[i];
                        break;

                    case DataFragment.DIALOG_BARCODE_CATEGORIES:
                        category = barcodeCategories[i];
                        break;
                }
        }
    }

    public void sendBackResult() {
        DialogCategoriesListener listener = (DialogCategoriesListener) getTargetFragment();
        listener.onFinishEditDialog(category);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("category", category);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            category = (String) savedInstanceState.get("category");
        }
    }


}

package com.example.benjo.personal_finance_app.fragments;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import java.util.Calendar;


public class DatePickerFragment extends DialogFragment {
    private static DatePickerDialog.OnDateSetListener mListener;
    private static int mYear, mMonth, mDay;

    public static DatePickerFragment newInstance(Calendar calendar, DatePickerDialog.OnDateSetListener listener) {
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        mListener = listener;
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        return datePickerFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        setRetainInstance(true);
        return new DatePickerDialog(getActivity(), mListener, mYear, mMonth, mDay);
    }

    public void setListener(DatePickerDialog.OnDateSetListener listener) {
        mListener = listener;
    }

    @Override
    public void onDestroyView() {
        if (getDialog() != null && getRetainInstance()) {
            getDialog().setDismissMessage(null);
        }
        super.onDestroyView();
    }

}

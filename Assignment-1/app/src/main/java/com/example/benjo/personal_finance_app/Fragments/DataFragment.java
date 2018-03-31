package com.example.benjo.personal_finance_app.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class DataFragment extends Fragment {
    public static final String FRAGMENT_LOGIN = "fragmentLogin";
    public static final String FRAGMENT_REGISTER = "fragmentRegister";
    public static final String FRAGMENT_SUMMARY = "fragmentSummary";
    public static final String FRAGMENT_INCOME = "fragmentIncome";
    public static final String FRAGMENT_EXPENDITURE = "fragmentExpenditure";
    public static final String FRAGMENT_ADD_INCOME = "fragmentAddIncome";
    public static final String FRAGMENT_ADD_EXPENDITURE  = "fragmentAddExpenditure";
    public static final String FRAGMENT_BARCODE = "fragmentBarcode";

    public static final int DIALOG_INCOME_CATEGORIES  = 0;
    public static final int DIALOG_EXPENDITURE_CATEGORIES  = 1;
    public static final int DIALOG_BARCODE_CATEGORIES = 2;

    private boolean fragmentsInitialized = false;


    public DataFragment() { /* Required empty public constructor */ }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRetainInstance(true);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void setFragmentsInitialized() {
        this.fragmentsInitialized = true;
    }

    public boolean fragmentsInitialized() {
        return this.fragmentsInitialized;
    }


}

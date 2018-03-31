package com.example.benjo.personal_finance_app.fragments;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.benjo.personal_finance_app.Controller;
import com.example.benjo.personal_finance_app.Transaction;
import com.example.benjo.personal_finance_app.R;
import com.example.benjo.personal_finance_app.dialogs.DialogCategories;

import java.util.Calendar;


public class BarcodeFragment extends Fragment implements DatePickerDialog.OnDateSetListener, DialogCategories.DialogCategoriesListener, View.OnClickListener {
    private TextView tvBarcodeID;
    private Controller controller;
    private EditText etAmount, etTitle;
    private String date, category;
    private int imgRes;
    private Button btnAdd, btnDate, btnCategory;


    public BarcodeFragment() { /* Required empty public constructor */ }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_barcode, container, false);
        initComponents(rootView);
        attachListeners();
        return rootView;
    }

    private void initComponents(View rootView) {
        tvBarcodeID = (TextView) rootView.findViewById(R.id.transaction_barcode);
        etAmount = (EditText) rootView.findViewById(R.id.transaction_et_amount);
        etTitle = (EditText) rootView.findViewById(R.id.transaction_et_title);
        btnAdd = (Button) rootView.findViewById(R.id.transaction_btn_add);
        btnDate = (Button) rootView.findViewById(R.id.transaction_btn_date);
        btnCategory = (Button) rootView.findViewById(R.id.transaction_btn_category);
    }

    private void attachListeners() {
        btnAdd.setOnClickListener(this);
        btnDate.setOnClickListener(this);
        btnCategory.setOnClickListener(this);
    }

    private void addTransaction() {
        boolean transactionValid = checkIfTransactionIsValid();
        if (transactionValid) {
            addTransactionToDB();
            clearFields();
        } else {
            showText("Please fill in all the fields.");
        }
    }

    private void addTransactionToDB() {
        String title = etTitle.getText().toString();
        String amount = etAmount.getText().toString();
        Transaction income = new Transaction(title, amount, category, date, imgRes);
        income.setBarcode(tvBarcodeID.getText().toString());
        controller.barcodeTransaction(income);
    }

    public void setBarcodeData(Transaction barcodeData) {
        etTitle.setText(barcodeData.getTitle());
        etAmount.setText(barcodeData.getAmount());
        date = barcodeData.getDate();
        category = barcodeData.getCategory();
        imgRes = barcodeData.getImgRes();
    }

    public void setBarcodeID(String barcodeID) {
        tvBarcodeID.setText(barcodeID);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.transaction_btn_add:
                addTransaction();
                break;

            case R.id.transaction_btn_date:
                showDatePicker();
                break;

            case R.id.transaction_btn_category:
                showDialog();
                break;
        }
    }

    private void showDatePicker() {
        DatePickerFragment datePickerFragment = (DatePickerFragment) getActivity().getFragmentManager().findFragmentByTag("datePickerFragment");
        if (datePickerFragment == null) {
            datePickerFragment = DatePickerFragment.newInstance(Calendar.getInstance(), this);
        }
        datePickerFragment.show(getActivity().getFragmentManager(), "datePickerFragment");
    }

    private void showDialog() {
        DialogCategories dialogCategories = (DialogCategories) getFragmentManager().findFragmentByTag("categoryDialogIncome");
        if (dialogCategories == null) {
            dialogCategories = DialogCategories.newInstance(DataFragment.DIALOG_BARCODE_CATEGORIES);
        }
        dialogCategories.setTargetFragment(this, 300);
        dialogCategories.show(getFragmentManager(), "categoryDialogIncome");
    }

    private boolean checkIfTransactionIsValid() {
        String title = etTitle.getText().toString();
        String amount = etAmount.getText().toString();
        return (!title.isEmpty() && !amount.isEmpty() && category != null && date != null);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        date = controller.getFormattedDate(year, month, dayOfMonth);
        showText(date);
    }

    public void setDateFragmentListener(DatePickerFragment datePickerFragment) {
        datePickerFragment.setListener(this);
    }

    @Override
    public void onFinishEditDialog(String category) {
        this.category = category;

        switch (category) {

            case "Salary":
                imgRes = R.drawable.ic_category_salary_48dp;
                break;

            case "Food":
                imgRes = R.drawable.ic_category_food_48dp;
                break;

            case "Leisure":
                imgRes = R.drawable.ic_category_leisure_48dp;
                break;

            case "Travel":
                imgRes = R.drawable.ic_category_travel_48dp;
                break;

            case "Accommodation":
                imgRes = R.drawable.ic_category_accommodation_48dp;
                break;

            default:
                imgRes = R.drawable.ic_category_other_48dp;
                break;
        }
    }

    private void clearFields() {
        etTitle.setText("");
        etAmount.setText("");
        category = null;
        date = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (date != null) {
            outState.putString("date", date);
        }
        if (category != null) {
            outState.putString("category", category);
            outState.putInt("imgRes", imgRes);
        }
        outState.putString("barcodeId", tvBarcodeID.getText().toString());
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) throws NullPointerException {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            date = savedInstanceState.getString("date");
            category = savedInstanceState.getString("category");
            imgRes = savedInstanceState.getInt("imgRes");
            tvBarcodeID.setText(savedInstanceState.getString("barcodeId"));
        }
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void showText(String text) {
        Snackbar.make(getActivity().findViewById(R.id.main_content), text, Snackbar.LENGTH_LONG).show();
    }



}

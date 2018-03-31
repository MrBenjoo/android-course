package com.example.benjo.personal_finance_app;


import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.DatePicker;

import com.example.benjo.personal_finance_app.activities.LoginActivity;
import com.example.benjo.personal_finance_app.activities.UserActivity;
import com.example.benjo.personal_finance_app.databases.ExpenditureDB;
import com.example.benjo.personal_finance_app.databases.IncomeDB;
import com.example.benjo.personal_finance_app.fragments.AddExpenditureFragment;
import com.example.benjo.personal_finance_app.fragments.AddIncomeFragment;
import com.example.benjo.personal_finance_app.fragments.BarcodeFragment;
import com.example.benjo.personal_finance_app.fragments.ContainerFragment;
import com.example.benjo.personal_finance_app.fragments.DataFragment;
import com.example.benjo.personal_finance_app.fragments.DatePickerFragment;
import com.example.benjo.personal_finance_app.fragments.ExpenditureFragment;
import com.example.benjo.personal_finance_app.fragments.IncomeFragment;
import com.example.benjo.personal_finance_app.fragments.LoginFragment;
import com.example.benjo.personal_finance_app.fragments.RegisterFragment;
import com.example.benjo.personal_finance_app.fragments.SummaryFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Controller {
    private LoginActivity loginActivity;
    private UserActivity userActivity;

    private SummaryFragment summaryFragment;
    private IncomeFragment incomeFragment;
    private AddIncomeFragment addIncomeFragment;
    private ExpenditureFragment expenditureFragment;
    private AddExpenditureFragment addExpenditureFragment;
    private BarcodeFragment barcodeFragment;
    private RegisterFragment registerFragment;
    private LoginFragment loginFragment;
    private DataFragment dataFragment;
    private ContainerFragment contFrag;

    private IncomeDB incomeDB;
    private ExpenditureDB expenditureDB;


    public Controller(LoginActivity loginActivity, ContainerFragment contFrag, boolean savedInstance) {
        this.loginActivity = loginActivity;
        this.contFrag = contFrag;
        if (savedInstance) {
            restoreLoginFragments();
        } else {
            initLoginFragments();
        }

    }

    public Controller(UserActivity userActivity, ContainerFragment contFrag) {
        this.userActivity = userActivity;
        this.contFrag = contFrag;
        initDataFragment();
        initMainFragments();
        setUserControllers();
        addToContFrag();
        initDatabases();
        initDatePickerFragment();
    }

    private void restoreLoginFragments() {
        FragmentManager fm = contFrag.getChildFragmentManager();
        registerFragment = (RegisterFragment) fm.findFragmentByTag(DataFragment.FRAGMENT_REGISTER);
        loginFragment = (LoginFragment) fm.findFragmentByTag(DataFragment.FRAGMENT_LOGIN);
        registerFragment.setController(this);
        loginFragment.setController(this);
        contFrag.add(registerFragment, DataFragment.FRAGMENT_REGISTER);
        contFrag.add(loginFragment, DataFragment.FRAGMENT_LOGIN);
    }

    private void initLoginFragments() {
        registerFragment = new RegisterFragment();
        loginFragment = new LoginFragment();
        registerFragment.setController(this);
        loginFragment.setController(this);
        contFrag.add(registerFragment, DataFragment.FRAGMENT_REGISTER);
        contFrag.add(loginFragment, DataFragment.FRAGMENT_LOGIN);
        contFrag.setCurrentTag(DataFragment.FRAGMENT_LOGIN);

    }

    private void initDataFragment() {
        FragmentManager fm = userActivity.getFragmentManager();
        dataFragment = (DataFragment) fm.findFragmentByTag("dataFragment");
        if (dataFragment == null) {
            dataFragment = new DataFragment();
            fm.beginTransaction().add(R.id.fragment_container, dataFragment, "dataFragment").hide(dataFragment).commit();
        }
    }

    private void initMainFragments() {
        if (dataFragment.fragmentsInitialized()) {
            FragmentManager fm = contFrag.getChildFragmentManager();
            summaryFragment = (SummaryFragment) fm.findFragmentByTag(DataFragment.FRAGMENT_SUMMARY);
            incomeFragment = (IncomeFragment) fm.findFragmentByTag(DataFragment.FRAGMENT_INCOME);
            expenditureFragment = (ExpenditureFragment) fm.findFragmentByTag(DataFragment.FRAGMENT_EXPENDITURE);
            addIncomeFragment = (AddIncomeFragment) fm.findFragmentByTag(DataFragment.FRAGMENT_ADD_INCOME);
            addExpenditureFragment = (AddExpenditureFragment) fm.findFragmentByTag(DataFragment.FRAGMENT_ADD_EXPENDITURE);
            barcodeFragment = (BarcodeFragment) fm.findFragmentByTag(DataFragment.FRAGMENT_BARCODE);
        } else {
            summaryFragment = new SummaryFragment();
            incomeFragment = new IncomeFragment();
            expenditureFragment = new ExpenditureFragment();
            addIncomeFragment = new AddIncomeFragment();
            addExpenditureFragment = new AddExpenditureFragment();
            barcodeFragment = new BarcodeFragment();
        }
    }

    private void setUserControllers() {
        summaryFragment.setController(this);
        incomeFragment.setController(this);
        expenditureFragment.setController(this);
        addIncomeFragment.setController(this);
        addExpenditureFragment.setController(this);
        barcodeFragment.setController(this);
    }

    private void addToContFrag() {
        contFrag.add(summaryFragment, DataFragment.FRAGMENT_SUMMARY);
        contFrag.add(incomeFragment, DataFragment.FRAGMENT_INCOME);
        contFrag.add(expenditureFragment, DataFragment.FRAGMENT_EXPENDITURE);
        contFrag.add(addIncomeFragment, DataFragment.FRAGMENT_ADD_INCOME);
        contFrag.add(addExpenditureFragment, DataFragment.FRAGMENT_ADD_EXPENDITURE);
        contFrag.add(barcodeFragment, DataFragment.FRAGMENT_BARCODE);
        if (!dataFragment.fragmentsInitialized()) {
            dataFragment.setFragmentsInitialized();
            contFrag.setCurrentTag(DataFragment.FRAGMENT_SUMMARY);
        }
    }

    private void initDatabases() {
        incomeDB = new IncomeDB(userActivity);
        expenditureDB = new ExpenditureDB(userActivity);
    }

    private void initDatePickerFragment() {
        DatePickerFragment datePickerFragment = (DatePickerFragment) userActivity.getFragmentManager().findFragmentByTag("datePickerFragment");
        if (datePickerFragment != null) {
            setDateFragmentListener(datePickerFragment);
        }
    }

    public void showFragment(String fragment) {
        contFrag.show(fragment);
    }

    public void addIncomeToDB(Transaction income) {
        boolean added = incomeDB.addTransaction(income);
        if (added) {
            incomeFragment.updateIncomeList(getIncomeFromDB());
            summaryFragment.updatePieChart(getTotalRevenue(), getTotalExpenditure());
        }
    }

    public ArrayList<Transaction> getIncomeFromDB() {
        return incomeDB.getTransactions();
    }

    public void addExpenditureToDB(Transaction expenditure) {
        boolean added = expenditureDB.addTransaction(expenditure);
        if (added) {
            expenditureFragment.updateExpenditureList(getExpendituresFromDB());
            summaryFragment.updatePieChart(getTotalRevenue(), getTotalExpenditure());
        }
    }

    public ArrayList<Transaction> getExpendituresFromDB() {
        return expenditureDB.getTransactions();
    }

    public float getTotalRevenue() {
        return incomeDB.getRevenue();
    }

    public float getTotalExpenditure() {
        return expenditureDB.getTotalExpenditure();
    }

    public void clearIncomeList() {
        incomeDB.deleteAll();
        incomeFragment.updateIncomeList(getIncomeFromDB());
        summaryFragment.updatePieChart(0, getTotalExpenditure());
    }

    public void clearExpenditureList() {
        expenditureDB.deleteAll();
        expenditureFragment.updateExpenditureList(getExpendituresFromDB());
        summaryFragment.updatePieChart(getTotalRevenue(), 0);
    }

    private void setIncomeDateSpan(String dateFrom, String dateTo) {
        ArrayList<Transaction> listIncomeSpan = incomeDB.getTransactionsFromTo(dateFrom, dateTo);
        incomeFragment.updateIncomeList(listIncomeSpan);
    }

    private void setExpenditureDateSpan(String dateFrom, String dateTo) {
        ArrayList<Transaction> listExpenditureSpan = expenditureDB.getTransactionsFromTo(dateFrom, dateTo);
        expenditureFragment.updateExpenditureList(listExpenditureSpan);
    }

    public void getFormattedDate(int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
        DateFormat dF = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        Calendar firstCalendar = Calendar.getInstance();
        Calendar secondCalendar = Calendar.getInstance();

        firstCalendar.set(year, monthOfYear, dayOfMonth);
        secondCalendar.set(yearEnd, monthOfYearEnd, dayOfMonthEnd);

        String firstDate = dF.format(firstCalendar.getTime());
        String secondDate = dF.format(secondCalendar.getTime());

        userActivity.showText(firstDate + " - " + secondDate);
        setIncomeDateSpan(firstDate, secondDate);
        setExpenditureDateSpan(firstDate, secondDate);
    }

    private void setDateFragmentListener(DatePickerFragment datePickerFragment) {
        switch (contFrag.getCurrentTag()) {
            case DataFragment.FRAGMENT_ADD_INCOME:
                addIncomeFragment.setDateFragmentListener(datePickerFragment);
                break;
            case DataFragment.FRAGMENT_ADD_EXPENDITURE:
                addExpenditureFragment.setDateFragmentListener(datePickerFragment);
                break;
            case DataFragment.FRAGMENT_BARCODE:
                barcodeFragment.setDateFragmentListener(datePickerFragment);
                break;
        }
    }

    public boolean checkCredentials(final String username, final String password) {
        if(username.isEmpty() || password.isEmpty()) {
            return false;
        }
        SharedPreferences sharedPref = loginActivity.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String user = sharedPref.getString(username, "");
        String pass = sharedPref.getString(password, "");
        return (username.equals(user) && password.equals(pass));
    }

    public boolean registerUser(String name, String password) {
        if (!name.isEmpty() && !password.isEmpty()) {
            SharedPreferences sharedPref = loginActivity.getSharedPreferences("user_info", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(name, name);
            editor.putString(password, password);
            editor.apply();
            return true;
        } else {
            return false;
        }
    }

    public String getUsername() {
        return userActivity.getIntent().getStringExtra("user");
    }

    public void onBarcodeScanned(String barcode) {
        boolean barcodeIncome = incomeDB.checkIfBarcodeExist(barcode);
        boolean barcodeExpenditure = expenditureDB.checkIfBarcodeExist(barcode);
        if (barcodeIncome) {
            Transaction barcodeData = incomeDB.getBarcodeTransaction(barcode);
            barcodeFragment.setBarcodeData(barcodeData);
        } else if (barcodeExpenditure) {
            Transaction barcodeData = expenditureDB.getBarcodeTransaction(barcode);
            barcodeFragment.setBarcodeData(barcodeData);
        }
        barcodeFragment.setBarcodeID(barcode);
        showFragment(DataFragment.FRAGMENT_BARCODE);
    }

    public void barcodeTransaction(Transaction transaction) {
        String category = transaction.getCategory();
        boolean incomeCategory = category.equals("Other (Income)") || category.equals("Salary");

        if (incomeCategory) {
            if (category.equals("Other (Income)")) {
                transaction.setCategory("Other");
            }
            boolean barcodeExist = incomeDB.checkIfBarcodeExist(transaction.getBarcode());
            if (!barcodeExist) {
                addIncomeToDB(transaction);
            } else {
                incomeDB.updateBarcodeTransaction(transaction);
                incomeFragment.updateIncomeList(getIncomeFromDB());
                summaryFragment.updatePieChart(getTotalRevenue(), getTotalExpenditure());
            }
            showFragment(DataFragment.FRAGMENT_INCOME);
        } else {
            if (category.equals("Other (Expenditure)")) {
                transaction.setCategory("Other");
            }
            boolean barcodeExist = expenditureDB.checkIfBarcodeExist(transaction.getBarcode());
            if (!barcodeExist) {
                addExpenditureToDB(transaction);
            } else {
                expenditureDB.updateBarcodeTransaction(transaction);
                expenditureFragment.updateExpenditureList(getExpendituresFromDB());
                summaryFragment.updatePieChart(getTotalRevenue(), getTotalExpenditure());
            }
            showFragment(DataFragment.FRAGMENT_EXPENDITURE);
        }
    }


    public void startUserActivity(String username) {
        loginActivity.startUserActivity(username);
    }

    public String getFormattedDate(int year, int month, int dayOfMonth) {
        DateFormat dF = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        return dF.format(calendar.getTime());
    }

    public boolean checkIfTransactionIsValid(Transaction transaction) {
        String title = transaction.getTitle();
        String amount = transaction.getAmount();
        String category = transaction.getCategory();
        String date = transaction.getDate();
        return (!title.isEmpty() && !amount.isEmpty() && category != null && date != null);
    }
}

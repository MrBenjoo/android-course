package com.example.benjo.personal_finance_app.activities;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;

import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.example.benjo.personal_finance_app.fragments.ContainerFragment;
import com.example.benjo.personal_finance_app.Controller;
import com.example.benjo.personal_finance_app.fragments.DataFragment;
import com.example.benjo.personal_finance_app.R;

import java.util.Calendar;


public class UserActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, DatePickerDialog.OnDateSetListener {
    private Controller controller;
    private ContainerFragment contFrag;
    private TextView navName;
    private String barcode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        initToolbar();
        initNavigationView();
        initController();
        setNavName();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void initNavigationView() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        navName = (TextView) headerView.findViewById(R.id.nav_header_tv_name);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initController() {
        contFrag = (ContainerFragment) getFragmentManager().findFragmentById(R.id.container_fragment_user);
        controller = new Controller(this, contFrag);
    }

    public void setNavName() {
        navName.setText(getIntent().getStringExtra("user"));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            switchFragment(contFrag.getCurrentTag());
        }
    }

    private void switchFragment(String currentTag) {
        switch (currentTag) {
            case DataFragment.FRAGMENT_ADD_INCOME:
                controller.showFragment(DataFragment.FRAGMENT_INCOME);
                break;
            case DataFragment.FRAGMENT_ADD_EXPENDITURE:
                controller.showFragment(DataFragment.FRAGMENT_EXPENDITURE);
                break;
            case DataFragment.FRAGMENT_BARCODE:
                controller.showFragment(DataFragment.FRAGMENT_SUMMARY);
                break;
            default:
                super.onBackPressed();
                break;
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.nav_summary:
                controller.showFragment(DataFragment.FRAGMENT_SUMMARY);
                break;

            case R.id.nav_income:
                controller.showFragment(DataFragment.FRAGMENT_INCOME);
                break;

            case R.id.nav_expenditure:
                controller.showFragment(DataFragment.FRAGMENT_EXPENDITURE);
                break;

            case R.id.nav_scan:
                Intent scannerIntent = new Intent(this, BarcodeActivity.class);
                startActivityForResult(scannerIntent, 0);
                break;

            case R.id.nav_time_span:
                showDateDialog();
                break;

            case R.id.nav_clear_income:
                controller.clearIncomeList();
                break;

            case R.id.nav_clear_expenditure:
                controller.clearExpenditureList();
                break;

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showDateDialog() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = com.borax12.materialdaterangepicker.date.DatePickerDialog.newInstance(this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    @Override
    protected void onResume() {
        super.onResume();
        DatePickerDialog datepickerdialog = (DatePickerDialog) getFragmentManager().findFragmentByTag("Datepickerdialog");
        if (datepickerdialog != null) {
            datepickerdialog.setOnDateSetListener(this);
        }

        if (barcode != null) {
            controller.onBarcodeScanned(barcode);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (data != null) {
                barcode = data.getStringExtra(BarcodeActivity.RESULT_STRING);
            }
        }
    }

    public void showText(String text) {
        Snackbar.make(findViewById(R.id.main_content), text, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
        controller.getFormattedDate(year, monthOfYear, dayOfMonth, yearEnd, monthOfYearEnd, dayOfMonthEnd);
    }

}

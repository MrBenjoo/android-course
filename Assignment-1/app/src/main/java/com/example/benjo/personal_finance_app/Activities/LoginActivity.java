package com.example.benjo.personal_finance_app.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.benjo.personal_finance_app.fragments.ContainerFragment;
import com.example.benjo.personal_finance_app.Controller;
import com.example.benjo.personal_finance_app.R;
import com.example.benjo.personal_finance_app.fragments.DataFragment;

public class LoginActivity extends AppCompatActivity {
    private ContainerFragment contFrag;
    private Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        boolean savedInstance = false;
        if (savedInstanceState != null) {
            savedInstance = true;
        }
        initController(savedInstance);
    }

    private void initController(boolean savedInstanceState) {
        contFrag = (ContainerFragment) getFragmentManager().findFragmentById(R.id.container_fragment_login);
        controller = new Controller(this, contFrag, savedInstanceState);

    }

    public void startUserActivity(String username) {
        Intent userIntent = new Intent(this, UserActivity.class);
        userIntent.putExtra("user", username);
        startActivity(userIntent);
    }

    @Override
    public void onBackPressed() {
        if(contFrag.getCurrentTag().equals(DataFragment.FRAGMENT_REGISTER)) {
            controller.showFragment(DataFragment.FRAGMENT_LOGIN);
        } else {
            super.onBackPressed();
        }
    }

}

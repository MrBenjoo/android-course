package com.example.benjo.lab1_loginscreen;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ConstraintLayout constraintLayout;
    EditText editTxt_user;
    EditText editTxt_pass;
    String username;
    String password;
    Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        init();
    }

    public void onLoginClick(View v) {
        username = editTxt_user.getText().toString();
        password = editTxt_pass.getText().toString();
        if (username.equals("benjamin") && password.equals("sejdic")) {
            Intent login_intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(login_intent);
            editTxt_user.setText("");
            editTxt_pass.setText("");
        } else {
            snackbar = Snackbar.make(constraintLayout, "Cant login", Snackbar.LENGTH_SHORT);
            View mView = snackbar.getView();
            TextView mTextView = (TextView) mView.findViewById(android.support.design.R.id.snackbar_text);
            mTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            snackbar.show();
        }
    }

    private void init() {
        constraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayout);
        editTxt_user = (EditText) findViewById(R.id.eT_name);
        editTxt_pass = (EditText) findViewById(R.id.eT_password);

    }
}

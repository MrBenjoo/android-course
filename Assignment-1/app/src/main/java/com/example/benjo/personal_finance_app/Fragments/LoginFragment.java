package com.example.benjo.personal_finance_app.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.benjo.personal_finance_app.Controller;
import com.example.benjo.personal_finance_app.R;


public class LoginFragment extends Fragment implements View.OnClickListener {
    private EditText etUser, etPass;
    private TextView tvRegister;
    private Button btnLogin;
    private Controller controller;


    public LoginFragment() { /* Required empty public constructor */ }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        initComponents(rootView);
        attachListeners();
        return rootView;
    }

    private void initComponents(View rootView) {
        tvRegister = (TextView) rootView.findViewById(R.id.frag_login_et_register);
        etUser = (EditText) rootView.findViewById(R.id.frag_login_et_name);
        etPass = (EditText) rootView.findViewById(R.id.frag_login_et_password);
        btnLogin = (Button) rootView.findViewById(R.id.frag_login_btn);
    }

    private void attachListeners() {
        btnLogin.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
    }

    private void login() {
        boolean registered = checkCredentials();
        if (registered) {
            String username = etUser.getText().toString();
            controller.startUserActivity(username);
            clearFields();
        } else {
            loginUnsuccessful();
        }
    }

    public boolean checkCredentials() {
        String username = etUser.getText().toString();
        String password = etPass.getText().toString();
        return controller.checkCredentials(username, password);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.frag_login_btn:
                login();
                break;

            case R.id.frag_login_et_register:
                controller.showFragment(DataFragment.FRAGMENT_REGISTER);
                break;
        }
    }

    public void loginUnsuccessful() {
        Snackbar.make(getActivity().findViewById(R.id.container_fragment_login), "Login unsuccessful", Snackbar.LENGTH_LONG).show();
    }

    public void clearFields() {
        etUser.setText("");
        etPass.setText("");
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

}

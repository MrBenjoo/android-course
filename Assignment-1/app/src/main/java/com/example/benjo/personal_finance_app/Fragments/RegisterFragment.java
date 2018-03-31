package com.example.benjo.personal_finance_app.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.benjo.personal_finance_app.Controller;
import com.example.benjo.personal_finance_app.R;


public class RegisterFragment extends Fragment implements View.OnClickListener {
    private Button btnRegister, btnBack;
    private EditText etName, etPassword;
    private Controller controller;


    public RegisterFragment() { /* Required empty public constructor */ }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_register, container, false);
        initComponents(rootView);
        attachListeners();
        return rootView;
    }

    private void initComponents(final View rootView) {
        btnBack = (Button) rootView.findViewById(R.id.frag_reg_btn_back);
        btnRegister = (Button) rootView.findViewById(R.id.frag_reg_btn_reg);
        etName = (EditText) rootView.findViewById(R.id.frag_reg_et_name);
        etPassword = (EditText) rootView.findViewById(R.id.frag_reg_et_pass);
    }

    private void attachListeners() {
        btnBack.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    private boolean registerUser() {
        String name = etName.getText().toString();
        String password = etPassword.getText().toString();
        return controller.registerUser(name, password);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.frag_reg_btn_reg:
                onRegistrationClicked();
                break;

            case R.id.frag_reg_btn_back:
                controller.showFragment(DataFragment.FRAGMENT_LOGIN);
                break;

        }
    }

    private void onRegistrationClicked() {
        boolean registerSuccessful = registerUser();
        if (registerSuccessful) {
            showText("Registered successfully.");
            controller.showFragment(DataFragment.FRAGMENT_LOGIN);
        } else {
            showText("Failed to register.");
        }
    }

    public void showText(String text) {
        Snackbar.make(getActivity().findViewById(R.id.container_fragment_login), text, Snackbar.LENGTH_LONG).show();
    }


    public void setController(Controller controller) {
        this.controller = controller;
    }
}

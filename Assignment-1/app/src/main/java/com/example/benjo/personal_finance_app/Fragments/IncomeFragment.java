package com.example.benjo.personal_finance_app.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.benjo.personal_finance_app.Controller;
import com.example.benjo.personal_finance_app.Transaction;
import com.example.benjo.personal_finance_app.R;
import com.example.benjo.personal_finance_app.adapters.ListAdapter;

import java.util.ArrayList;


public class IncomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private Controller controller;
    private ListAdapter lvAdapterIncome;
    private SwipeRefreshLayout swipeRefreshLayout;

    public IncomeFragment() { /*  Required empty public constructor */ }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.transaction_list, container, false);
        initFab(rootView);
        initRecyclerView(rootView);
        initSwipeRefresh(rootView);
        return rootView;
    }


    private void initFab(View rootView) {
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.transaction_list_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.showFragment(DataFragment.FRAGMENT_ADD_INCOME);
            }
        });
    }

    private void initRecyclerView(View rootView) {
        RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.transaction_list_rc);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        lvAdapterIncome = new ListAdapter(new ArrayList<Transaction>());
        mRecyclerView.setAdapter(lvAdapterIncome);
    }

    private void initSwipeRefresh(View rootView) {
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.transaction_list_refresh);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getListFromDatabase();
    }

    private void getListFromDatabase() {
        ArrayList<Transaction> incomeList = controller.getIncomeFromDB();
        lvAdapterIncome.updateList(incomeList);
    }


    public void updateIncomeList(ArrayList<Transaction> incomeList) {
        lvAdapterIncome.updateList(incomeList);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void onRefresh() {
        ArrayList<Transaction> incomeList = controller.getIncomeFromDB();
        lvAdapterIncome.updateList(incomeList);
        swipeRefreshLayout.setRefreshing(false);
    }
}

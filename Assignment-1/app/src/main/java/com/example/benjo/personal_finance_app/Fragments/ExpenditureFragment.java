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
import com.example.benjo.personal_finance_app.adapters.ListAdapter;
import com.example.benjo.personal_finance_app.R;

import java.util.ArrayList;


public class ExpenditureFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private Controller controller;
    private ListAdapter lvAdapterExpenditure;
    private SwipeRefreshLayout swipeRefreshLayout;


    public ExpenditureFragment() { /* Required empty public constructor */ }


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
                controller.showFragment(DataFragment.FRAGMENT_ADD_EXPENDITURE);
            }
        });
    }

    private void initRecyclerView(View rootView) {
        RecyclerView rcv = (RecyclerView) rootView.findViewById(R.id.transaction_list_rc);
        rcv.setHasFixedSize(true);
        rcv.setLayoutManager(new LinearLayoutManager(getActivity()));
        lvAdapterExpenditure = new ListAdapter(new ArrayList<Transaction>());
        rcv.setAdapter(lvAdapterExpenditure);
    }

    private void initSwipeRefresh(View rootView) {
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.transaction_list_refresh);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getListFromDatabase();
    }

    private void getListFromDatabase() {
        lvAdapterExpenditure.updateList(controller.getExpendituresFromDB());
    }

    public void updateExpenditureList(ArrayList<Transaction> expenditureList) {
        lvAdapterExpenditure.updateList(expenditureList);
    }

    @Override
    public void onRefresh() {
        ArrayList<Transaction> expenditureList = controller.getExpendituresFromDB();
        lvAdapterExpenditure.updateList(expenditureList);
        swipeRefreshLayout.setRefreshing(false);
    }
}

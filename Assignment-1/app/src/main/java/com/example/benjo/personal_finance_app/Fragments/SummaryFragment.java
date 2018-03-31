package com.example.benjo.personal_finance_app.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.benjo.personal_finance_app.Controller;
import com.example.benjo.personal_finance_app.CustomTextView;
import com.example.benjo.personal_finance_app.piechart.PieChartData;
import com.example.benjo.personal_finance_app.piechart.PieChartHandler;
import com.example.benjo.personal_finance_app.R;
import com.github.mikephil.charting.charts.PieChart;


public class SummaryFragment extends Fragment {
    private CustomTextView welcomeMsg;
    private PieChart pieChart;
    private Controller controller;
    private PieChartHandler pieChartHandler;
    private CustomTextView customTextView;


    public SummaryFragment() { /* Required empty public constructor */ }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_summary, container, false);
        initializeComponents(rootView);
        return rootView;
    }

    private void initializeComponents(View rootView) {
        welcomeMsg = (CustomTextView) rootView.findViewById(R.id.frag_sum_tv_welcome);
        pieChart = (PieChart) rootView.findViewById(R.id.frag_sum_pie_chart);
        customTextView = new CustomTextView(getContext());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setWelcomeMsg(controller.getUsername());
        initializePieChart();
    }

    public void setWelcomeMsg(final String username) {
        welcomeMsg.setWelcomeMessage(username);
    }

    private void initializePieChart() {
        pieChartHandler = new PieChartHandler(pieChart, new PieChartData(controller.getTotalRevenue(), controller.getTotalExpenditure(), customTextView));
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void updatePieChart(float revenue, float expenditure) {
        pieChartHandler.updatePieChart(new PieChartData(revenue, expenditure, customTextView));
    }

}

package com.example.benjo.personal_finance_app.piechart;


import android.graphics.Color;

import com.example.benjo.personal_finance_app.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class PieChartHandler {

    private PieChart pieChart;
    private PieDataSet pieDataSet;
    private PieData pieData;
    private ArrayList<PieEntry> yValues;
    private PieChartData pieChartData;


    public PieChartHandler(PieChart pieChart, PieChartData pieChartData) {
        this.pieChart = pieChart;
        this.pieChartData = pieChartData;
        initializeDataSet();
        initializePieData();
        initializePieChart();
    }

    private void initializeDataSet() {
        yValues = new ArrayList<>();
        yValues.add(new PieEntry(pieChartData.getRevenue(), "Revenue"));
        yValues.add(new PieEntry(pieChartData.getExpenditure(), "Expenditure"));
        pieDataSet = new PieDataSet(yValues, "");
        pieDataSet.setSliceSpace(3f);
        pieDataSet.setSelectionShift(5f);
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
    }

    private void initializePieData() {
        pieData = new PieData(pieDataSet);
        pieData.setValueTextSize(10f);
    }

    private void initializePieChart() {
        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterTextColor(Color.WHITE);
        pieChart.setCenterTextSize(18f);
        pieChart.setCenterText(pieChartData.getBalance().getText().toString());
        pieChart.getLegend().setEnabled(false);
        pieChart.setHoleColor(Color.parseColor("#212121"));
    }

    public void updatePieChart(PieChartData pieChartData) {
        yValues.clear();
        pieDataSet.clear();
        pieChart.clearValues();
        pieDataSet.addEntry(new PieEntry(pieChartData.getRevenue(), "Revenue"));
        pieDataSet.addEntry(new PieEntry(pieChartData.getExpenditure(), "Expenditure"));
        pieDataSet.notifyDataSetChanged();
        pieData.setDataSet(pieDataSet);
        pieChart.setData(pieData);
        pieChart.setCenterText(pieChartData.getBalance().getText().toString());
        pieChart.invalidate();
    }

}

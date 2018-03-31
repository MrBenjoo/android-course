package com.example.benjo.personal_finance_app.piechart;


import com.example.benjo.personal_finance_app.CustomTextView;

public class PieChartData {

    private float revenue;
    private float expenditure;
    //private String balance;
    private CustomTextView balance;

    public PieChartData(float revenue, float expenditure, CustomTextView balance) {
        this.revenue = revenue;
        this.expenditure = expenditure;
        this.balance = balance;
        this.balance.setFloatAsText(revenue - expenditure);
        //this.balance = Float.toString(revenue - expenditure);
    }

    public float getRevenue() {
        return revenue;
    }

    public void setRevenue(float revenue) {
        this.revenue = revenue;
    }

    public float getExpenditure() {
        return expenditure;
    }

    public void setExpenditure(float expenditure) {
        this.expenditure = expenditure;
    }

    public CustomTextView getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        //this.balance = Float.toString(balance);
        this.balance.setFloatAsText(balance);
    }
}

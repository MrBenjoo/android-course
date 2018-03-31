package com.example.benjo.personal_finance_app;

public class Transaction {
    private String title, amount, category, date, barcode;
    private int imgRes;

    public Transaction(String title, String amount, String category, String date, int imgRes) {
        this.title = title;
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.imgRes = imgRes;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }

    public String getTitle() {
        return this.title;
    }

    public String getAmount() {
        return this.amount;
    }

    public String getCategory() {
        return this.category;
    }

    public String getDate() {
        return this.date;
    }

    public int getImgRes() {
        return this.imgRes;
    }

    public String getBarcode() {
        if(barcode == null) {
            return "null";
        } else {
            return this.barcode;
        }
    }

}

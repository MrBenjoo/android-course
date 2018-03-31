package com.example.benjo.personal_finance_app.databases;


public class Constants {
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_AMOUNT = "amount";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_IMG = "image";
    public static final String COLUMN_BARCODE = "barcode";


    public static String DATABASE_CREATE(final String TABLE_NAME) {
        String DATABASE_CREATE =
                "create table " + TABLE_NAME + "(" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_TITLE + " text, " +
                        COLUMN_CATEGORY + " text, " +
                        COLUMN_AMOUNT + " text, " +
                        COLUMN_DATE + " text, " +
                        COLUMN_IMG + " INTEGER, " +
                        COLUMN_BARCODE + " text);";
        return DATABASE_CREATE;
    }

}

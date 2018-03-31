package com.example.benjo.personal_finance_app.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.benjo.personal_finance_app.Transaction;

import java.util.ArrayList;

import static com.example.benjo.personal_finance_app.databases.Constants.COLUMN_ID;
import static com.example.benjo.personal_finance_app.databases.Constants.COLUMN_TITLE;
import static com.example.benjo.personal_finance_app.databases.Constants.COLUMN_AMOUNT;
import static com.example.benjo.personal_finance_app.databases.Constants.COLUMN_BARCODE;
import static com.example.benjo.personal_finance_app.databases.Constants.COLUMN_CATEGORY;
import static com.example.benjo.personal_finance_app.databases.Constants.COLUMN_DATE;
import static com.example.benjo.personal_finance_app.databases.Constants.COLUMN_IMG;


public class ExpenditureDB extends SQLiteOpenHelper {
    private final String TAG = "ExpenditureDB";
    private static final String TABLE_NAME = "expenditure_table";
    private static final String DATABASE_NAME = "expenditure.db";
    private static final int DATABASE_VERSION = 6;


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constants.DATABASE_CREATE(TABLE_NAME));
    }

    public boolean addTransaction(Transaction expenditure) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = getContentValues(expenditure);
        long result = db.insert(TABLE_NAME, "", values);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public ArrayList<Transaction> getTransactions() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        ArrayList<Transaction> transactions = getAddedTransactions(cursor);
        cursor.close();
        return transactions;
    }

    private ContentValues getContentValues(Transaction expenditure) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, expenditure.getTitle());
        values.put(COLUMN_CATEGORY, expenditure.getCategory());
        values.put(COLUMN_AMOUNT, expenditure.getAmount());
        values.put(COLUMN_DATE, expenditure.getDate());
        values.put(COLUMN_IMG, expenditure.getImgRes());
        values.put(COLUMN_BARCODE, expenditure.getBarcode());
        return values;
    }

    private ArrayList<Transaction> getAddedTransactions(Cursor cursor) {
        ArrayList<Transaction> transactions = new ArrayList<>();
        int titleIndex = cursor.getColumnIndex(COLUMN_TITLE);
        int categoryIndex = cursor.getColumnIndex(COLUMN_CATEGORY);
        int amountIndex = cursor.getColumnIndex(COLUMN_AMOUNT);
        int dateIndex = cursor.getColumnIndex(COLUMN_DATE);
        int imgIndex = cursor.getColumnIndex(COLUMN_IMG);

        while (cursor.moveToNext()) {
            String title = cursor.getString(titleIndex);
            String category = cursor.getString(categoryIndex);
            String amount = cursor.getString(amountIndex);
            String date = cursor.getString(dateIndex);
            int imgRes = cursor.getInt(imgIndex);
            Transaction transaction = new Transaction(title, amount, category, date, imgRes);
            transactions.add(transaction);
        }

        return transactions;
    }

    public ArrayList<Transaction> getTransactionsFromTo(String dateFrom, String dateTo) {
        SQLiteDatabase db = this.getReadableDatabase();
        final String columns[] = {COLUMN_ID, COLUMN_TITLE, COLUMN_CATEGORY, COLUMN_AMOUNT, COLUMN_DATE, COLUMN_IMG};
        Cursor cursor = db.query(TABLE_NAME, columns, COLUMN_DATE + " between ? and ?", new String[]{dateFrom, dateTo}, null, null, COLUMN_DATE + " ASC");
        ArrayList<Transaction> transactions = getAddedTransactions(cursor);
        cursor.close();
        return transactions;
    }

    public float getTotalExpenditure() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_AMOUNT + " FROM " + TABLE_NAME, new String[]{});
        int amountIndex = cursor.getColumnIndex(COLUMN_AMOUNT);
        float totalExpenditure = 0;

        while (cursor.moveToNext()) {
            if (cursor.getString(amountIndex) != null && cursor.getString(amountIndex).length() > 0) {
                float foo = Float.parseFloat(cursor.getString(amountIndex));
                totalExpenditure += foo;
            }
        }
        cursor.close();
        return totalExpenditure;
    }

    public boolean checkIfBarcodeExist(String barcode) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectString = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_BARCODE + " = ?";
        Cursor cursor = db.rawQuery(selectString, new String[]{barcode});
        boolean exist = (cursor.getCount() > 0);
        cursor.close();
        return exist;
    }

    public Transaction getBarcodeTransaction(String barcode) {
        SQLiteDatabase db = this.getReadableDatabase();
        Transaction transaction = null;
        Cursor cursor;
        boolean exist = checkIfBarcodeExist(barcode);
        if (exist) {
            String selectString = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_BARCODE + " = ?";
            cursor = db.rawQuery(selectString, new String[]{barcode});
            cursor.moveToFirst();
            transaction = getBarcodeData(cursor);
            cursor.close();
        }
        return transaction;
    }

    private Transaction getBarcodeData(Cursor cursor) {
        int titleIndex = cursor.getColumnIndex(COLUMN_TITLE);
        int categoryIndex = cursor.getColumnIndex(COLUMN_CATEGORY);
        int amountIndex = cursor.getColumnIndex(COLUMN_AMOUNT);
        int dateIndex = cursor.getColumnIndex(COLUMN_DATE);
        int imgIndex = cursor.getColumnIndex(COLUMN_IMG);

        String title = cursor.getString(titleIndex);
        String category = cursor.getString(categoryIndex);
        String amount = cursor.getString(amountIndex);
        String date = cursor.getString(dateIndex);
        int imgRes = cursor.getInt(imgIndex);

        return new Transaction(title, amount, category, date, imgRes);
    }

    public void updateBarcodeTransaction(Transaction expenditure) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = getContentValues(expenditure);
        db.update(TABLE_NAME, values, "barcode = ?", new String[]{expenditure.getBarcode()});
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
    }

    public ExpenditureDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}

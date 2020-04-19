package com.example.a5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteData extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "A5Manager.db";
    public static final String TABLE_NAME = "A5Manager";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "ITEM";
    public static final String COL_3 = "DATE";
    public static final String COL_4 = "PRICE";
    public SQLiteData(Context context){
        super(context, DATABASE_NAME, null, 5);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, ITEM varchar(250), DATE varchar(250), PRICE DOUBLE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int NewVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean createTransaction(Model model){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, model.mItem);
        contentValues.put(COL_3, model.mDate);
        contentValues.put(COL_4, model.mPrice);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1) { return false; }
        return true;
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return result;
    }

    public Cursor getFilteredData(String priceFromString, String priceToString, String dateFrom, String dateTo) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = null;
        Double priceFrom = null;
        Double priceTo = null;
        if (!priceFromString.isEmpty()) {
            priceFrom = Double.parseDouble(priceFromString);
        }
        if (!priceToString.isEmpty()) {
            priceTo = Double.parseDouble(priceToString);
        }

        // if price from
        if (priceFrom != null && priceTo == null && dateFrom.isEmpty() && dateTo.isEmpty()) {
            result = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE Price >= " + priceFrom, null);
        } else if (priceFrom == null && priceTo != null && dateFrom.isEmpty() && dateTo.isEmpty()) {
            result = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE Price <= " + priceTo, null);
        } else if (priceFrom == null && priceTo == null && !dateFrom.isEmpty() && dateTo.isEmpty()) {
            result = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE Date >= '" + dateFrom + "'", null);
        } else if (priceFrom == null && priceTo == null && dateFrom.isEmpty() && !dateTo.isEmpty()) {
            result = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE Date <= '" + dateTo + "'", null);
        } else if (priceFrom != null && priceTo != null && dateFrom.isEmpty() && dateTo.isEmpty()) {
            result = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE Price >= " + priceFrom + " AND Price <= " + priceTo, null);
        } else if (priceFrom == null && priceTo == null && !dateFrom.isEmpty() && !dateTo.isEmpty()) {
            result = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE Date >= '" + dateFrom + "' AND Date <= '" + dateTo + "'", null);
        } else if (priceFrom != null && priceTo == null && !dateFrom.isEmpty() && dateTo.isEmpty()){
            result = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE Price >= " + priceFrom + " AND Date >= '" + dateFrom + "'", null);
        } else if (priceFrom != null && priceTo == null && dateFrom.isEmpty() && !dateTo.isEmpty()) {
            result = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE Price >= " + priceFrom + " AND Date <= '" + dateTo + "'", null);
        } else if (priceFrom == null && priceTo != null && !dateFrom.isEmpty() && dateTo.isEmpty()) {
            result = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE Price <= " + priceTo + " AND Date >= '" + dateFrom + "'", null);
        } else if (priceFrom == null && priceTo != null && dateFrom.isEmpty() && !dateTo.isEmpty()) {
            result = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE Price <= " + priceTo + " AND Date <= '" + dateTo + "'", null);
        } else if (priceFrom != null && priceTo != null && !dateFrom.isEmpty() && dateTo.isEmpty()) {
            result = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE Price >= " + priceFrom + " AND Price <= " +
                    priceTo + " AND Date >= '" + dateFrom + "'", null);
        } else if (priceFrom != null && priceTo != null && dateFrom.isEmpty() && !dateTo.isEmpty()) {
            result = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE Price >= " + priceFrom + " AND Price <= " +
                    priceTo + " AND Date <= '" + dateTo + "'", null);
        } else if (priceFrom == null && priceTo != null && !dateFrom.isEmpty() && !dateTo.isEmpty()) {
            result = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE Price <= " + priceTo + " AND Date >= '" +
                    dateFrom + "' AND Date <= '" + dateTo + "'", null);
        } else if (priceFrom != null && priceTo == null && !dateFrom.isEmpty() && !dateTo.isEmpty()) {
            result = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE Price >= " + priceFrom + " AND Date >= '" +
                    dateFrom + "' AND Date <= '" + dateTo +"'", null);
        } else if (priceFrom != null && priceTo != null && !dateFrom.isEmpty() && !dateTo.isEmpty()) {
            result = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE Price >= " + priceFrom + " AND Price <= " + priceTo + " AND Date >= '" +
                    dateFrom + "' AND Date <= '" + dateTo + "'", null);
        } else {
            result = getAllData();
        }
        return result;
    }
}
package com.example.warehouse2.db.DbManager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.warehouse2.adapter.ListItemR;
import com.example.warehouse2.db.DbContract.ContractDocEN;
import com.example.warehouse2.db.DbContract.ContractDocON;
import com.example.warehouse2.db.DbContract.ContractNomenclature;
import com.example.warehouse2.db.DbHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ManagerEN {
    private Context context;
    private DbHelper dbHelper;
    private SQLiteDatabase db;

    public ManagerEN(Context context) {
        this.context = context;
        dbHelper = new DbHelper(context);
    }

    public void openDb() {
        db = dbHelper.getWritableDatabase();
    }

    public void insertToDb(int num_name, int count, int number){
        ContentValues cv = new ContentValues();
        cv.put(ContractDocEN.NUM_NAME, num_name);
        cv.put(ContractDocEN.COUNT,count);
        cv.put(ContractDocEN.NUMBER_D1, number);
        db.insert(ContractDocEN.TABLE_NAME_DOC_EN, null, cv);
    }

    public Collection<? extends ListItemR> getForListItems() {
        List<ListItemR> tempListR = new ArrayList<>();
        db = dbHelper.getWritableDatabase();

        String query =
                "SELECT " + ContractNomenclature.TITLE_P + ", " + ContractNomenclature.UNIT_MEASUREMENT +
                ",  COALESCE(SUM(sum_count2), 0) AS sum_count2, sum_count1, " + ContractDocEN._ID_D_EN +
                        " FROM (" +
                        " SELECT  COALESCE(SUM(" + ContractDocEN.COUNT + "), 0) AS sum_count1, " + ContractDocEN.NUM_NAME +
                        ", " + ContractDocEN._ID_D_EN +
                            " FROM " + ContractDocEN.TABLE_NAME_DOC_EN + " GROUP BY " + ContractDocEN.NUM_NAME + ") " +
                        "AS  t2" +
                " JOIN " + ContractNomenclature.TABLE_NAME_N + " ON " + ContractNomenclature._ID + " = " + ContractDocEN.NUM_NAME +
                " LEFT JOIN (" +
                    " SELECT DISTINCT " + ContractDocON.NUM_NAME2 + ", COALESCE(SUM(" + ContractDocON.COUNT2 + "), 0) AS  sum_count2" +
                        " FROM " + ContractDocON.TABLE_NAME_DOC_ON +
                    " GROUP BY " + ContractDocON.NUM_NAME2 + ") " +
                    ContractDocON.TABLE_NAME_DOC_ON + " ON " + ContractNomenclature._ID + " = " + ContractDocON.NUM_NAME2 +
                " GROUP BY " + ContractNomenclature.TITLE_P + ";";

        Cursor cursor = db.rawQuery(query, null, null);

        while (cursor.moveToNext()) {
            ListItemR listItemR = new ListItemR();
            int id =  cursor.getInt(cursor.getColumnIndexOrThrow(ContractDocEN._ID_D_EN));
            int count1 = cursor.getInt(cursor.getColumnIndexOrThrow("sum_count1"));
            int count2 = cursor.getInt(cursor.getColumnIndexOrThrow("sum_count2"));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(ContractNomenclature.TITLE_P));
            String unit_measurement = cursor.getString(cursor.getColumnIndexOrThrow(ContractNomenclature.UNIT_MEASUREMENT));
            int count = count1 - count2;
            listItemR.setCount1R(count1);
            listItemR.setCount2R(count2);
            listItemR.setCountR(count);
            listItemR.setIdR(id);
            listItemR.setTitleR(title);
            listItemR.setUnitR(unit_measurement);
            tempListR.add(listItemR);
        }
        cursor.close();
        return tempListR;
    }

    public Collection<? extends ListItemR> getForItem(int number) {
        List<ListItemR> tempListR = new ArrayList<>();
        db = dbHelper.getWritableDatabase();

        String query =
                "SELECT " + ContractNomenclature.TITLE_P + ", " + ContractNomenclature.UNIT_MEASUREMENT +
                        ",  COALESCE(SUM(sum_count2), 0) AS sum_count2, sum_count1, " + ContractDocEN._ID_D_EN +
                        " FROM (" +
                        " SELECT  COALESCE(SUM(" + ContractDocEN.COUNT + "), 0) AS sum_count1, " + ContractDocEN.NUM_NAME +
                        ", " + ContractDocEN._ID_D_EN +
                        " FROM " + ContractDocEN.TABLE_NAME_DOC_EN + " GROUP BY " + ContractDocEN.NUM_NAME + ") " +
                        "AS  t2" +
                        " JOIN " + ContractNomenclature.TABLE_NAME_N + " ON " + ContractNomenclature._ID + " = " + ContractDocEN.NUM_NAME +
                        " LEFT JOIN (" +
                        " SELECT DISTINCT " + ContractDocON.NUM_NAME2 + ", COALESCE(SUM(" + ContractDocON.COUNT2 + "), 0) AS  sum_count2" +
                        " FROM " + ContractDocON.TABLE_NAME_DOC_ON +
                        " GROUP BY " + ContractDocON.NUM_NAME2 + ") " +
                        ContractDocON.TABLE_NAME_DOC_ON + " ON " + ContractNomenclature._ID + " = " + ContractDocON.NUM_NAME2 +
                        " WHERE " + ContractDocEN.NUM_NAME + " = " + number +
                        " GROUP BY " + ContractNomenclature.TITLE_P + ";";

        Cursor cursor = db.rawQuery(query, null, null);

        while (cursor.moveToNext()) {
            ListItemR listItemR = new ListItemR();
            int id =  cursor.getInt(cursor.getColumnIndexOrThrow(ContractDocEN._ID_D_EN));
            int count1 = cursor.getInt(cursor.getColumnIndexOrThrow("sum_count1"));
            int count2 = cursor.getInt(cursor.getColumnIndexOrThrow("sum_count2"));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(ContractNomenclature.TITLE_P));
            String unit_measurement = cursor.getString(cursor.getColumnIndexOrThrow(ContractNomenclature.UNIT_MEASUREMENT));
            int count = count1 - count2;
            listItemR.setCount1R(count1);
            listItemR.setCount2R(count2);
            listItemR.setCountR(count);
            listItemR.setIdR(id);
            listItemR.setTitleR(title);
            listItemR.setUnitR(unit_measurement);
            tempListR.add(listItemR);
        }
        cursor.close();
        return tempListR;
    }

    public String getNumber(){
        int number = 0;
        db = dbHelper.getWritableDatabase();

        String query = "SELECT MAX(" + ContractDocEN.NUMBER_D1 + ") AS max  FROM " + ContractDocEN.TABLE_NAME_DOC_EN + ";";
        Cursor cursor = db.rawQuery(query, null, null);

        while (cursor.moveToNext()) {
            number = cursor.getInt(cursor.getColumnIndexOrThrow("max"));
        }

        number += 1;
        return String.valueOf(number);
    }

    public int checkingNegativeNumber(int number){
        int resultSum = 0;
        db = dbHelper.getWritableDatabase();

        String query = "SELECT SUM(" + ContractDocEN.COUNT + ") AS sum," + ContractDocEN.NUM_NAME +
                " FROM " + ContractDocEN.TABLE_NAME_DOC_EN +
                " WHERE " +  ContractDocEN.NUM_NAME + " = " + number + ";";
        Cursor cursor = db.rawQuery(query, null, null);

        while (cursor.moveToNext()) {
            resultSum = cursor.getInt(cursor.getColumnIndexOrThrow("sum"));
        }

        return resultSum;
    }

    public void closeDb() {
        dbHelper.close();
    }
}

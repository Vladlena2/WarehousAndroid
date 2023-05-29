package com.example.warehouse2.db.DbManager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.warehouse2.db.DbContract.ContractDocON;
import com.example.warehouse2.db.DbHelper;

public class ManagerON {
    private Context context;
    private DbHelper dbHelper;
    private SQLiteDatabase db;

    public ManagerON(Context context) {
        this.context = context;
        dbHelper = new DbHelper(context);
    }

    public void openDb() {
        db = dbHelper.getWritableDatabase();
    }

    public void insertToDb(int num_name, int count, int number){
        ContentValues cv = new ContentValues();
        cv.put(ContractDocON.NUM_NAME2, num_name);
        cv.put(ContractDocON.COUNT2,count);
        cv.put(ContractDocON.NUMBER_D2, number);
        db.insert(ContractDocON.TABLE_NAME_DOC_ON, null, cv);
    }

    public String getNumber(){
        int number = 0;
        db = dbHelper.getWritableDatabase();

        String query = "SELECT MAX(" + ContractDocON.NUMBER_D2 + ") AS max  FROM " + ContractDocON.TABLE_NAME_DOC_ON + ";";
        Cursor cursor = db.rawQuery(query, null, null);

        while (cursor.moveToNext()) {
            number = cursor.getInt(cursor.getColumnIndexOrThrow("max"));
        }

        number += 1;
        return String.valueOf(number);
    }


    public void closeDb() {
        dbHelper.close();
    }
}

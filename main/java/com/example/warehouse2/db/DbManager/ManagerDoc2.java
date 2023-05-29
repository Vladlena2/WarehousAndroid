package com.example.warehouse2.db.DbManager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.warehouse2.db.DbContract.ContractDocOffs;
import com.example.warehouse2.db.DbHelper;

public class ManagerDoc2 {
    private Context context;
    private DbHelper dbHelper;
    private SQLiteDatabase db;

    public ManagerDoc2(Context context) {
        this.context = context;
        dbHelper = new DbHelper(context);
    }

    public void openDb() {
        db = dbHelper.getWritableDatabase();
    }

    public void insertToDb(int number, long date){
        ContentValues cv = new ContentValues();
        cv.put(ContractDocOffs.NUMBER2, number);
        cv.put(ContractDocOffs.DATE2, date);
        db.insert(ContractDocOffs.TABLE_NAME_DOC2, null, cv);
    }

    public int compareNumbers(int number2){
        int number = 0;
        db = dbHelper.getWritableDatabase();

        String query = "SELECT COUNT(*) AS result FROM " + ContractDocOffs.TABLE_NAME_DOC2 + " WHERE " + ContractDocOffs.NUMBER2 + " = " + number2 + ";";
        Cursor cursor = db.rawQuery(query, null, null);

        while (cursor.moveToNext()) {
            number = cursor.getInt(cursor.getColumnIndexOrThrow("result"));
        }

        return number;
    }

  public void closeDb() {
        dbHelper.close();
    }
}

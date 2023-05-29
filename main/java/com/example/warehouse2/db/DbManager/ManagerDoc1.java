package com.example.warehouse2.db.DbManager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.warehouse2.db.DbContract.ContactDocEnrollment;
import com.example.warehouse2.db.DbHelper;

public class ManagerDoc1 {
    private Context context;
    private DbHelper dbHelper;
    private SQLiteDatabase db;

    public ManagerDoc1(Context context) {
        this.context = context;
        dbHelper = new DbHelper(context);
    }

    public void openDb() {
        db = dbHelper.getWritableDatabase();
    }

    public void insertToDb(int number, long date){
        ContentValues cv = new ContentValues();
        cv.put(ContactDocEnrollment.NUMBER1, number);
        cv.put(ContactDocEnrollment.DATE1, date);
        db.insert(ContactDocEnrollment.TABLE_NAME_DOC1, null, cv);
    }

    public int compareNumbers(int number2){
        int number = 0;
        db = dbHelper.getWritableDatabase();

        String query = "SELECT COUNT(*) AS result FROM " + ContactDocEnrollment.TABLE_NAME_DOC1 + " WHERE " + ContactDocEnrollment.NUMBER1 + " = " + number2 + ";";
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

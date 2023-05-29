package com.example.warehouse2.db.DbManager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.warehouse2.adapter.ListItemU;
import com.example.warehouse2.db.DbContract.ContractUnit;
import com.example.warehouse2.db.DbHelper;

import java.util.ArrayList;
import java.util.List;

public class ManagerUnit {
    private Context context;
    private DbHelper dbHelper;
    private SQLiteDatabase db;

    public ManagerUnit(Context context) {
        this.context = context;
        dbHelper = new DbHelper(context);
    }

    public void openDb(){
        db = dbHelper.getWritableDatabase();
    }

    public void insertToDb(String unit_measurement){
        ContentValues cv = new ContentValues();
        cv.put(ContractUnit.UNIT_MEASUREMENT, unit_measurement);
        db.insert(ContractUnit.TABLE_NAME_U, null, cv);
    }

    public void deleteItem(int id){
        String selection = ContractUnit._ID + "=" + id;
        db.delete(ContractUnit.TABLE_NAME_U, selection,null);
    }

    public List<ListItemU> getFromDb(String unit) {
        List<ListItemU> tempListU = new ArrayList<>();
        String selection = ContractUnit.UNIT_MEASUREMENT + " like ?";
        Cursor cursor = db.query(ContractUnit.TABLE_NAME_U, null,
                selection, new String[]{"%" + unit + "%"},
                null, null, null);

        while (cursor.moveToNext()) {
            ListItemU item = new ListItemU();
            int _id = cursor.getInt(cursor.getColumnIndexOrThrow(ContractUnit._ID));
            String unit_measurement = cursor.getString(cursor.getColumnIndexOrThrow(ContractUnit.UNIT_MEASUREMENT));
            item.setId(_id);
            item.setUnit_measurement(unit_measurement);
            tempListU.add(item);
        }
        cursor.close();
        return tempListU;
    }


    public List<String> getUnits(){
        List<String> tempList = new ArrayList<>();
        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(" SELECT " + ContractUnit.UNIT_MEASUREMENT +  " FROM " + ContractUnit.TABLE_NAME_U, null);

        while (cursor.moveToNext()) {
            String unit_measurement = cursor.getString(cursor.getColumnIndexOrThrow(ContractUnit.UNIT_MEASUREMENT));
            tempList.add(unit_measurement);
        }
        cursor.close();
        return tempList;
    }


    public void closeDb(){
        dbHelper.close();
    }
}

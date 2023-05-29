package com.example.warehouse2.db.DbManager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.warehouse2.adapter.ListItemN;
import com.example.warehouse2.db.DbContract.ContractNomenclature;
import com.example.warehouse2.db.DbHelper;
import com.example.warehouse2.db.OnDataReceived;

import java.util.ArrayList;
import java.util.List;

public class ManagerNomenclature {
    private Context context;
    private DbHelper dbHelper;
    private SQLiteDatabase db;

    public ManagerNomenclature(Context context) {
        this.context = context;
        dbHelper = new DbHelper(context);
    }

    public void openDb() {
        db = dbHelper.getWritableDatabase();
    }

    public void insertToDb(String title, String unit_measurement, String uri) {
        ContentValues cv = new ContentValues();
        cv.put(ContractNomenclature.TITLE_P, title);
        cv.put(ContractNomenclature.UNIT_MEASUREMENT, unit_measurement);
        cv.put(ContractNomenclature.URI, uri);
        db.insert(ContractNomenclature.TABLE_NAME_N, null, cv);
    }

    public void deleteItem(int id) {
        String selection = ContractNomenclature._ID + "=" + id;
        db.delete(ContractNomenclature.TABLE_NAME_N, selection, null);
    }

    public void updateItem(String title, String unit_measurement, String uri, int id) {
        String selection = ContractNomenclature._ID + "=" + id;
        ContentValues cv = new ContentValues();
        cv.put(ContractNomenclature.TITLE_P, title);
        cv.put(ContractNomenclature.UNIT_MEASUREMENT, unit_measurement);
        cv.put(ContractNomenclature.URI, uri);
        db.update(ContractNomenclature.TABLE_NAME_N, cv, selection, null);
    }

    public void getFromDb(String search_text, OnDataReceived onDataReceived) {
        List<ListItemN> tempList = new ArrayList<>();
        db = dbHelper.getWritableDatabase();
        String selection = ContractNomenclature.TITLE_P + " like ?";
        Cursor cursor = db.query(ContractNomenclature.TABLE_NAME_N, null, selection, new String[]{"%" + search_text + "%"},
                null, null, null);

        while (cursor.moveToNext()) {
            ListItemN listItemN = new ListItemN();
            String title = cursor.getString(cursor.getColumnIndexOrThrow(ContractNomenclature.TITLE_P));
            String unit_measurement = cursor.getString(cursor.getColumnIndexOrThrow(ContractNomenclature.UNIT_MEASUREMENT));
            String uri = cursor.getString(cursor.getColumnIndexOrThrow(ContractNomenclature.URI));
            int _id = cursor.getInt(cursor.getColumnIndexOrThrow(ContractNomenclature._ID));
            listItemN.setTitle(title);
            listItemN.setUri(uri);
            listItemN.setId(_id);
            listItemN.setUnit_measurement(unit_measurement);
            tempList.add(listItemN);
        }
        cursor.close();
        onDataReceived.onReceived(tempList);
    }


    public String getTitle(String title){
        String result = "";
        db = dbHelper.getWritableDatabase();
        String query = " SELECT " + ContractNomenclature.UNIT_MEASUREMENT +  " FROM " + ContractNomenclature.TABLE_NAME_N
                + " WHERE " + ContractNomenclature.TITLE_P + " = ?";
        Cursor cursor = db.rawQuery(query, new String[] { title });
        if (cursor.moveToFirst()) {
            result = cursor.getString(cursor.getColumnIndexOrThrow(ContractNomenclature.UNIT_MEASUREMENT));
        }
        cursor.close();
        return result;
    }

    public List<String> getTitleForSpinner(){
        List<String> tempList = new ArrayList<>();
        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(" SELECT " + ContractNomenclature.TITLE_P +  " FROM " + ContractNomenclature.TABLE_NAME_N, null);

        while (cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndexOrThrow(ContractNomenclature.TITLE_P));
            tempList.add(title);
        }
        cursor.close();
        return tempList;
    }

    public void closeDb() {
        dbHelper.close();
    }
}


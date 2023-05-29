package com.example.warehouse2.db;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.warehouse2.db.DbContract.ContactDocEnrollment;
import com.example.warehouse2.db.DbContract.ContractDocEN;
import com.example.warehouse2.db.DbContract.ContractDocON;
import com.example.warehouse2.db.DbContract.ContractDocOffs;
import com.example.warehouse2.db.DbContract.ContractNomenclature;
import com.example.warehouse2.db.DbContract.ContractUnit;

public class DbHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "my_warehouse.db";
    public static final int DB_VERSION = 16;

    public DbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @SuppressLint("SQLiteString")
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ContractNomenclature.TABLE_STRUCTURE);
        db.execSQL(ContractUnit.TABLE_STRUCTURE);
        db.execSQL(ContactDocEnrollment.TABLE_STRUCTURE);
        db.execSQL(ContractDocOffs.TABLE_STRUCTURE);
        db.execSQL(ContractDocEN.TABLE_STRUCTURE);
        db.execSQL(ContractDocON.TABLE_STRUCTURE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ContractNomenclature.DROP_TABLE);
        db.execSQL(ContractUnit.DROP_TABLE);
        db.execSQL(ContactDocEnrollment.DROP_TABLE);
        db.execSQL(ContractDocOffs.DROP_TABLE);
        db.execSQL(ContractDocEN.DROP_TABLE);
        db.execSQL(ContractDocON.DROP_TABLE);
        onCreate(db);
    }
}

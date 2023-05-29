package com.example.warehouse2.db.DbContract;

import static com.example.warehouse2.db.DbContract.ContractUnit.TABLE_NAME_U;

public class ContractNomenclature {
    public static final String LIST_ITEM_INTENT = "list_item_intent";
    public static final String EDIT_STATE = "edit_state";
    public static final String TABLE_NAME_N = "table_nomenclature";
    public static final String _ID = "_id";
    public static final String TITLE_P = "title_p";
    public static final String UNIT_MEASUREMENT = "unit_measurement";
    public static final String URI = "uri";

    public static final String TABLE_STRUCTURE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_N +
            " (" + _ID + " INTEGER PRIMARY KEY," +
            TITLE_P + " TEXT,"  +
            URI + " TEXT," +
            UNIT_MEASUREMENT + " TEXT," +
            " FOREIGN KEY (" + UNIT_MEASUREMENT + ") REFERENCES " +TABLE_NAME_U + "(" + ContractUnit._ID + "));";


    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME_N;
}

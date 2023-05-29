package com.example.warehouse2.db.DbContract;

public class ContractUnit {
    public static final String TABLE_NAME_U = "table_unit";
    public static final String _ID = "_id";
    public static final String UNIT_MEASUREMENT = "unit_measurement";

    public static final String TABLE_STRUCTURE = "CREATE TABLE IF NOT EXISTS " +
            TABLE_NAME_U + " (" + _ID +
            " INTEGER PRIMARY KEY," + UNIT_MEASUREMENT + " TEXT)";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME_U;

}

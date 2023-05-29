package com.example.warehouse2.db.DbContract;

public class ContractDocON {
    public static final String TABLE_NAME_DOC_ON = "table_doc_on";
    public static final String _ID_D_ON = "_id_D_ON";
    public static final String NUM_NAME2 = "num_name2";
    public static final String COUNT2 = "count2";
    public static final String NUMBER_D2 = "number2";

    public static final String TABLE_STRUCTURE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_DOC_ON +
            " (" + _ID_D_ON + " INTEGER PRIMARY KEY," +
            NUM_NAME2 + " INTEGER REFERENCES " + ContractNomenclature.TABLE_NAME_N
            + "(" + ContractNomenclature._ID + ") NOT NULL, "  +
            COUNT2 + " INTEGER NOT NULL, " +
            NUMBER_D2 + " INTEGER REFERENCES " + ContractDocOffs.TABLE_NAME_DOC2
            + "(" + ContractDocOffs.NUMBER2 + "));";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME_DOC_ON;
}


package com.example.warehouse2.db.DbContract;

public class ContractDocEN {
    public static final String TABLE_NAME_DOC_EN = "table_doc_en";
    public static final String _ID_D_EN = "_id_D_EN";
    public static final String NUM_NAME = "num_name1";
    public static final String COUNT = "count1";
    public static final String NUMBER_D1 = "number1";

    public static final String TABLE_STRUCTURE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_DOC_EN +
            " (" + _ID_D_EN + " INTEGER PRIMARY KEY," +
            NUM_NAME + " INTEGER REFERENCES " + ContractNomenclature.TABLE_NAME_N
            + "(" + ContractNomenclature._ID + ") NOT NULL, "  +
            COUNT + " INTEGER NOT NULL, " +
            NUMBER_D1 + " INTEGER REFERENCES " + ContactDocEnrollment.TABLE_NAME_DOC1
            + "(" + ContactDocEnrollment.NUMBER1 + "));";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME_DOC_EN;
}

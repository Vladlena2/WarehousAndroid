package com.example.warehouse2.db.DbContract;

import static com.example.warehouse2.db.DbContract.ContractNomenclature.TABLE_NAME_N;

public class ContractDocOffs {
    public static final String TABLE_NAME_DOC2 = "table_doc_offs";
    public static final String _ID_D2 = "_id_D2";
    public static final String NUMBER2 = "number2";
    public static final String DATE2 = "date2";


    public static final String TABLE_STRUCTURE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_DOC2 +
            " (" + _ID_D2 + " INTEGER PRIMARY KEY," +
            NUMBER2 + " INTEGER," +
            DATE2 + " INTEGER);";


    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME_DOC2;
}


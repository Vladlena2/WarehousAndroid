package com.example.warehouse2.db.DbContract;

public class ContactDocEnrollment {
    public static final String TABLE_NAME_DOC1 = "table_doc_enrollment";
    public static final String _ID_D1 = "_id_D1";
    public static final String NUMBER1 = "number";
    public static final String DATE1 = "date";

    public static final String TABLE_STRUCTURE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_DOC1 +
            " (" + _ID_D1 + " INTEGER PRIMARY KEY," +
            NUMBER1 + " INTEGER," +
            DATE1 + " INTEGER);";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME_DOC1;
}

package com.example.lab5v1.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "students_faculties.db";
    private static final int SCHEMA = 1;
    static final String TABLE = "Students";

    public static final String COLUMN_ID = "Id";
    public static final String COLUMN_SURNAME = "Surname";
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_BORN_DATE = "BornDate";
    public static final String COLUMN_FACULTIES = "Faculties";
    public static final String COLUMN_GROUP = "[Group]";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS Students (\n" +
                "    Id        INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    Name      TEXT,\n" +
                "    Surname   TEXT,\n" +
                "    BornDate  DATE,\n" +
                "    Faculties TEXT,\n" +
                "    [Group]   TEXT\n" +
                ");\n");


        db.execSQL("INSERT INTO "+ TABLE +" (" + COLUMN_SURNAME
                + ", " + COLUMN_NAME
                + ", " + COLUMN_BORN_DATE
                + ", " + COLUMN_FACULTIES
                + ", " + COLUMN_GROUP
                + ") VALUES ('Burtsev', 'Vitalij', '2002-07-08', 'Fais', 'IP-41')"
                +", ('Prokopenko', 'Vlad', '2001-06-11', 'EF', 'EP-41')"
                +", ('Emelenko', 'Artem', '2000-15-04', 'Fais', 'ITI-41') ;");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }
}

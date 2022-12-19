package com.example.labaratornaya_1.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "trainsAndroid1.db";
    private static final int SCHEMA = 1;
    static final String TABLE = "Trains";

    public static final String COLUMN_ID = "Id";
    public static final String COLUMN_DESTINATIONS = "Destination";
    public static final String COLUMN_NUMBER = "Number";
    public static final String COLUMN_DATEARRIVE = "DateArrive";
    public static final String COLUMN_COUPE = "Coupe";
    public static final String COLUMN_PLATSKART = "Paltskart";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS Trains (\n" +
            "    Id        INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "    Destination      TEXT,\n" +
            "    Number   TEXT,\n" +
            "    DateArrive  TEXT,\n" +
            "    Coupe TEXT,\n" +
            "    Paltskart   TEXT\n" +
            ");\n");


        db.execSQL("INSERT INTO "+ TABLE +" ("
            + COLUMN_DESTINATIONS
            + ", " + COLUMN_NUMBER
            + ", " + COLUMN_DATEARRIVE
            + ", " + COLUMN_COUPE
            + ", " + COLUMN_PLATSKART
            + ") VALUES ('Paris', '100', '08-10-2022 18:00', '10', '23')"
            +", ('Paris', '99', '09-09-2022 18:00', '0', '43')"
            +", ('Berlin', '1', '09-05-2022 19:00', '100', '65') ;");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }
}

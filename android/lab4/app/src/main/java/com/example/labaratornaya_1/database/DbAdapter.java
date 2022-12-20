package com.example.labaratornaya_1.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.labaratornaya_1.entity.Train;

import java.util.ArrayList;

public class DbAdapter {

    private DbHelper dbHelper;
    private SQLiteDatabase database;

    public DbAdapter(Context context){
        dbHelper = new DbHelper(context.getApplicationContext());
    }

    public DbAdapter open(){
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    private Cursor getAllEntries(){
        String[] columns = new String[] {
            DbHelper.COLUMN_ID,
            DbHelper.COLUMN_DESTINATIONS,
            DbHelper.COLUMN_NUMBER,
            DbHelper.COLUMN_DATEARRIVE,
            DbHelper.COLUMN_COUPE,
            DbHelper.COLUMN_PLATSKART
        };
        return database.query(DbHelper.TABLE,
            columns,
            null,
            null,
            null,
            null,
            null);
    }

    public ArrayList<Train> getTrains(){
        ArrayList<Train> trains = new ArrayList<Train>();
        Cursor cursor = getAllEntries();
        while (cursor.moveToNext()){
            Integer Id = cursor.getInt(0);
            String Destination = cursor.getString(1);
            String Number = cursor.getString(2);
            String DateArrive = cursor.getString(3);
            String Coupe = cursor.getString(4);
            String Platskart = cursor.getString(5);
            Train train = new Train(Id,Destination, Number, DateArrive, Coupe, Platskart);
            trains.add(train);

        }
        cursor.close();
        return trains;
    }

    public Train getTrain(long id){
        Train train = null;
        String query = String.format("SELECT * FROM %s WHERE %s=?", DbHelper.TABLE, DbHelper.COLUMN_ID);
        Cursor cursor = database.rawQuery(query, new String[]{ String.valueOf(id)});
        if(cursor.moveToFirst()){
            Integer Id = cursor.getInt(0);
            String Destination = cursor.getString(1);
            String Number = cursor.getString(2);
            String DateArrive = cursor.getString(3);
            String Coupe = cursor.getString(4);
            String Platskart = cursor.getString(5);
            train = new Train(Id, Destination, Number, DateArrive, Coupe, Platskart);

        }
        cursor.close();
        return train;
    }

    public long insert(Train train){
        ContentValues cv = new ContentValues();
        cv.put(DbHelper.COLUMN_DESTINATIONS, train.getDestination());
        cv.put(DbHelper.COLUMN_NUMBER, train.getNumber());
        cv.put(DbHelper.COLUMN_DATEARRIVE, train.getDateArrive());
        cv.put(DbHelper.COLUMN_COUPE, train.getCoupe());
        cv.put(DbHelper.COLUMN_PLATSKART, train.getPlatskart());

        return database.insert(DbHelper.TABLE, null, cv);
    }

    public long delete(long userId){
        String whereClause = "Id = ?";
        String[] whereArgs = new String[]{String.valueOf(userId)};
        return database.delete(DbHelper.TABLE, whereClause, whereArgs);
    }

    public long update(Train train){
        String whereClause = DbHelper.COLUMN_ID + "=" + (train.getId() - 1);
        ContentValues cv = new ContentValues();
        cv.put(DbHelper.COLUMN_DESTINATIONS, train.getDestination());
        cv.put(DbHelper.COLUMN_NUMBER, train.getNumber());
        cv.put(DbHelper.COLUMN_DATEARRIVE, train.getDateArrive());
        cv.put(DbHelper.COLUMN_COUPE, train.getCoupe());
        cv.put(DbHelper.COLUMN_PLATSKART, train.getPlatskart());
        return database.update(DbHelper.TABLE, cv, whereClause, null);
    }
}

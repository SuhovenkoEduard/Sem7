package com.example.lab5v1.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.example.lab5v1.entity.Student;
import com.example.lab5v1.inteface.Repository;

import java.util.ArrayList;
import java.util.Date;

public class DbAdapter implements Repository {

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
                                    DbHelper.COLUMN_SURNAME,
                                    DbHelper.COLUMN_NAME,
                                    DbHelper.COLUMN_BORN_DATE,
                                    DbHelper.COLUMN_FACULTIES,
                                    DbHelper.COLUMN_GROUP
                            };
        return database.query(DbHelper.TABLE,
                columns,
                null,
                null,
                null,
                null,
                null);
    }

    @Override
    public ArrayList<Student> getStudents(){

        ArrayList<Student> students = new ArrayList<Student>();
        Cursor cursor = getAllEntries();
        while (cursor.moveToNext()){
            Integer Id = cursor.getInt(0);
            String Surname = cursor.getString(1);
            String Name = cursor.getString(2);
            String[] BornDate = cursor.getString(3).split("-");
            int year = Integer.parseInt(BornDate[0]);
            int month = Integer.parseInt(BornDate[2]);
            int date = Integer.parseInt(BornDate[1]);
            String Faculties = cursor.getString(4);
            String Group = cursor.getString(5);
            Student student = new Student(Id,Surname, Name, new Date(year, month, date), Faculties, Group);
            students.add(student);

        }
        cursor.close();
        return  students;
    }

    @Override
    public Student getStudent(int id){
        Student student = null;
        String query = String.format("SELECT * FROM %s WHERE %s=?",DbHelper.TABLE, DbHelper.COLUMN_ID);
        Cursor cursor = database.rawQuery(query, new String[]{ String.valueOf(id)});
        if(cursor.moveToFirst()){
            Integer Id = cursor.getInt(0);
            String Surname = cursor.getString(1);
            String Name = cursor.getString(2);
            String[] BornDate = cursor.getString(3).split("-");
            int year = Integer.parseInt(BornDate[0]);
            int month = Integer.parseInt(BornDate[2]);
            int date = Integer.parseInt(BornDate[1]);
            String Faculties = cursor.getString(4);
            String Group = cursor.getString(5);
            student = new Student(Id, Surname, Name, new Date(year, month, date), Faculties, Group);

        }
        cursor.close();
        return  student;
    }

    @Override
    public long insert(@NonNull Student student){
        ContentValues cv = new ContentValues();
        cv.put(DbHelper.COLUMN_SURNAME, student.getSurname());
        cv.put(DbHelper.COLUMN_NAME, student.getName());
        cv.put(DbHelper.COLUMN_BORN_DATE, student.getBornDate());
        cv.put(DbHelper.COLUMN_FACULTIES, student.getFaculties());
        cv.put(DbHelper.COLUMN_GROUP, student.getGroup());

        return  database.insert(DbHelper.TABLE, null, cv);
    }

    @Override
    public long delete(int userId){

        String whereClause = "Id = ?";
        String[] whereArgs = new String[]{String.valueOf(userId)};
        return database.delete(DbHelper.TABLE, whereClause, whereArgs);
    }

    @Override
    public long update(@NonNull Student student){
        String whereClause = DbHelper.COLUMN_ID + "=" + student.getId();
        ContentValues cv = new ContentValues();
        cv.put(DbHelper.COLUMN_SURNAME, student.getSurname());
        cv.put(DbHelper.COLUMN_NAME, student.getName());
        cv.put(DbHelper.COLUMN_BORN_DATE, student.getBornDate());
        cv.put(DbHelper.COLUMN_FACULTIES, student.getFaculties());
        cv.put(DbHelper.COLUMN_GROUP, student.getGroup());
        return database.update(DbHelper.TABLE, cv, whereClause, null);
    }



}

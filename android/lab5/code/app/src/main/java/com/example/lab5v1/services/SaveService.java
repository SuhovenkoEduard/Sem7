package com.example.lab5v1.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.example.lab5v1.businessLogic.data.StudentOperations;
import com.example.lab5v1.database.DbAdapter;
import com.example.lab5v1.model.Model;

public class SaveService extends Service {

    DbAdapter dbAdapter;
    Model model;

    @Override
    public void onCreate() {
        super.onCreate();
        dbAdapter = new DbAdapter(this);
        model = new Model(dbAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getAction().equals("Refactor")) {
            String studentData = intent.getExtras().getString("data");
            refactor(studentData);
        }
        else{
            String studentData = intent.getExtras().getString("data1");
            create(studentData);
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void refactor(String data){
        StudentOperations studentOperations = new StudentOperations();
        model.updateStudent(studentOperations.getDataSplit(data));
    }

    private void create(String data){
        StudentOperations studentOperations = new StudentOperations();
        model.insertStudent(studentOperations.getDataSplit(data));
    }

}
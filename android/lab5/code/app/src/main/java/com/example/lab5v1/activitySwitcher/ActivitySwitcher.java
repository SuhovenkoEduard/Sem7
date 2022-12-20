package com.example.lab5v1.activitySwitcher;

import android.content.Context;
import android.content.Intent;

import com.example.lab5v1.entity.Student;

import java.util.ArrayList;

public class ActivitySwitcher {

    public static void switchActivity(Context mainView, Student student, Class activityClass){
        Intent intent = new Intent(mainView, activityClass);
        intent.putExtra("student", student);
        mainView.startActivity(intent);
    }

    public static void switchActivity(Context mainView, Class activityClass){
        Intent intent = new Intent(mainView, activityClass);
        mainView.startActivity(intent);
    }

    public static void switchActivity(Context mainView, ArrayList<Student> students, Class activityClass){
        Intent intent = new Intent((Context) mainView, activityClass);
        intent.putExtra("students", students);
        ((Context) mainView).startActivity(intent);
    }

}

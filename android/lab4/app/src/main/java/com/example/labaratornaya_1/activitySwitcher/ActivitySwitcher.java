package com.example.labaratornaya_1.activitySwitcher;

import android.content.Context;
import android.content.Intent;

import com.example.labaratornaya_1.entity.Train;

import java.util.ArrayList;

public class ActivitySwitcher {

    public static void switchActivity(Context mainView, Train train, Class activityClass){
        Intent intent = new Intent((Context) mainView, activityClass);
        intent.putExtra("train", train);
        mainView.startActivity(intent);
    }

    public static void switchActivity(Context mainView, Class activityClass){
        Intent intent = new Intent((Context) mainView, activityClass);
        mainView.startActivity(intent);
    }

    public static void switchActivity(Context mainView, ArrayList<Train> trains, Class activityClass){
        Intent intent = new Intent((Context) mainView, activityClass);
        intent.putExtra("trains", trains);
        ((Context) mainView).startActivity(intent);
    }

}

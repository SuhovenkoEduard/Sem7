package com.example.labaratornaya_1.presenter;
import android.content.Context;

import com.example.labaratornaya_1.activity.TrainActivity;
import com.example.labaratornaya_1.activitySwitcher.ActivitySwitcher;
import com.example.labaratornaya_1.entity.Train;
import com.example.labaratornaya_1.inteface.Contract;

import java.util.ArrayList;

public class FindPresenter{

    private Contract.View mainView;
    private Contract.Model model;


    public FindPresenter(Contract.View mainView, Contract.Model model){
        this.mainView = mainView;
        this.model = model;
    }

    public void onFindByFaisSelect(){
        ArrayList<Train> trainsFilterCity = model.getFilterTrain("Faculty");
        ActivitySwitcher.switchActivity((Context) mainView,
                                        trainsFilterCity,
                                        TrainActivity.class);
    }
    public void onFindByBornSelect(){
        ArrayList<Train> studentsFilterDate = model.getFilterTrain("Date");
        ActivitySwitcher.switchActivity((Context) mainView,
                                        studentsFilterDate,
                                        TrainActivity.class);
    }

    public void onFindSelect(String Type, String Filter){
        ArrayList<Train> trainsFilter =  model.getFilterStudentUniversal(Type, Filter);
        ActivitySwitcher.switchActivity((Context) mainView,
                trainsFilter,
                TrainActivity.class);
    }
}

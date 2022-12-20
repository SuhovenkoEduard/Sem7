package com.example.labaratornaya_1.presenter;
import android.content.Context;

import com.example.labaratornaya_1.activity.MainActivity;
import com.example.labaratornaya_1.activity.TrainCreateActivity;
import com.example.labaratornaya_1.activitySwitcher.ActivitySwitcher;
import com.example.labaratornaya_1.businessLogic.data.TrainOperations;
import com.example.labaratornaya_1.inteface.Contract;

public class CreatePresenter {

    private Contract.View mainView;
    private Contract.Model model;


    public CreatePresenter(Contract.View mainView, Contract.Model model){
        this.mainView = mainView;
        this.model = model;
    }

    public void onCreateSelect(){
        ActivitySwitcher.switchActivity((Context) mainView,  TrainCreateActivity.class);
    }

    public void onCreateClick(){
        TrainOperations trainOperations = new TrainOperations();
        String data = ((Contract.View.Options) mainView).getStudentData();
        model.insertTrain(trainOperations.getDataSplit(data));
        mainView.showToast("train was created");
        ActivitySwitcher.switchActivity((Context) mainView, MainActivity.class);
    }
}

package com.example.labaratornaya_1.presenter;
import android.content.Context;

import com.example.labaratornaya_1.activity.MainActivity;
import com.example.labaratornaya_1.activity.RefactorActivity;
import com.example.labaratornaya_1.activitySwitcher.ActivitySwitcher;
import com.example.labaratornaya_1.businessLogic.data.TrainOperations;
import com.example.labaratornaya_1.entity.Train;
import com.example.labaratornaya_1.inteface.Contract;

public class RefactorPresenter{

    private Contract.View mainView;
    private Contract.Model model;

    public RefactorPresenter(Contract.View mainView, Contract.Model model){
        this.mainView = mainView;
        this.model = model;
    }

    public void onRefactorSelect(Train train){
        if(train != null){
            ActivitySwitcher.switchActivity((Context) mainView, train, RefactorActivity.class);
        }else {
            mainView.showToast("Not select train");
        }
    }

    public void onRefactorClick(){
        TrainOperations trainOperations = new TrainOperations();
        String data = ((Contract.View.Options) mainView).getStudentData();
        model.updateTrain(trainOperations.getDataSplit(data));
        mainView.showToast("train was update");
        ActivitySwitcher.switchActivity((Context) mainView, MainActivity.class);

    }
}

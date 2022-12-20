package com.example.labaratornaya_1.presenter;
import com.example.labaratornaya_1.entity.Train;
import com.example.labaratornaya_1.inteface.Contract;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class DeletePresenter{

    private Contract.View mainView;
    private Contract.Model model;


    public DeletePresenter(Contract.View mainView, Contract.Model model){
        this.mainView = mainView;
        this.model = model;
    }

    public void onDeleteSelect(Train train){
        if (train != null){
            model.deleteTrainById(train.getId());
            getStudents();
        }else {
            mainView.showToast("Train wasn't selected");
        }
    }

    public void getStudents(){
        ArrayList<Train> trains = null;
        try {
            trains = model.getTrains();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mainView.showTrain(trains);
    }
}

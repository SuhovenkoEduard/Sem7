package com.example.labaratornaya_1.presenter;

import com.example.labaratornaya_1.entity.Train;
import com.example.labaratornaya_1.inteface.Contract;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Presenter{

    private Contract.View mainView;
    private Contract.Model model;


    public Presenter(Contract.View mainView, Contract.Model model){
        this.mainView = mainView;
        this.model = model;
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

package com.example.labaratornaya_1.inteface;

import com.example.labaratornaya_1.entity.Train;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public interface Contract {

    interface View {
        void showTrain(ArrayList<Train> trains);
        void showToast(String message);

        interface Options{
            String getStudentData();
        }
    }

    interface Model {
        void updateTrain(Train train);
        void insertTrain(Train train);
        void deleteTrainById(int id);

        Train getTrain(int id);
        ArrayList<Train> getTrains() throws FileNotFoundException;
        ArrayList<Train> getFilterTrain(String Filter);
        ArrayList<Train> getFilterStudentUniversal(String Type, String Filter);
    }
}

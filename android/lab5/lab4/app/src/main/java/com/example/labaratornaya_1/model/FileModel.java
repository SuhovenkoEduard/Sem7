package com.example.labaratornaya_1.model;

import com.example.labaratornaya_1.businessLogic.data.TrainFileRepository;
import com.example.labaratornaya_1.entity.Train;
import com.example.labaratornaya_1.inteface.Contract;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class FileModel implements Contract.Model {

    TrainFileRepository context;

    public FileModel(TrainFileRepository context){
        this.context = context;
    }


    @Override
    public void updateTrain(Train train) {
        context.update(train);
    }

    @Override
    public void insertTrain(Train train) {
        context.insert(train);
    }

    @Override
    public void deleteTrainById(int id) {
        context.delete(id);
    }

    @Override
    public Train getTrain(int id) {
        return context.getStudent(id);
    }

    @Override
    public ArrayList<Train> getTrains() throws FileNotFoundException {
        return context.getStudents();
    }

    public void Save(ArrayList<Train> trains){
        context.save(trains);
    }

    @Override
    public ArrayList<Train> getFilterTrain(String Filter) {
        return null;
    }

    @Override
    public ArrayList<Train> getFilterStudentUniversal(String Type, String Filter) {
        return null;
    }
}

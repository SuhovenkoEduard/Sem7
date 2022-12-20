package com.example.labaratornaya_1.businessLogic.data;

import android.content.Context;

import com.example.labaratornaya_1.entity.Train;
import com.example.labaratornaya_1.inteface.Repository;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

public class TrainFileRepository implements Repository {
    Context context;
    TrainFiles trainFiles;

    public TrainFileRepository(TrainFiles trainFiles, Context context){
        this.trainFiles = trainFiles;
        this.context = context;
    }


    @Override
    public ArrayList<Train> getStudents() throws FileNotFoundException {
        String student = trainFiles.readFile(context);
        Gson gson = new Gson();
        Train[] studentsArray = gson.fromJson(student, Train[].class);
        return new ArrayList<Train>(Arrays.asList(studentsArray));
    }

    @Override
    public Train getStudent(int id) {
        String student = trainFiles.readFile(context);
        Gson gson = new Gson();
        Train[] studentsArray = gson.fromJson(student, Train[].class);
        return studentsArray[id];
    }

    public void save(ArrayList<Train> trains){
        trainFiles.writeFile(new Gson().toJson(trains), context);
    }


    @Override
    public long insert(Train train) {
        return 0;
    }

    @Override
    public long delete(int userId) {
        return 0;
    }

    @Override
    public long update(Train train) {
        return 0;
    }
}

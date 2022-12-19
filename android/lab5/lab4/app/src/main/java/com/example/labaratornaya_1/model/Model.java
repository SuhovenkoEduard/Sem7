package com.example.labaratornaya_1.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.labaratornaya_1.database.DbAdapter;
import com.example.labaratornaya_1.entity.Train;
import com.example.labaratornaya_1.inteface.Contract;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Model implements Contract.Model {

    private DbAdapter database;

    private static final String FacultyFilter = "Faculty";
    private static final String DateFilter = "Date";
    private static final String YearFilter = "Year";

    private static final String FacultyNameFilter = "Fais";
    private static final int YearIntFilter = 2000;


    public Model(DbAdapter database){
        this.database = database;
        this.database.open();
    }

    @Override
    public ArrayList<Train> getFilterTrain(String Filter) {
        ArrayList<Train> trains = database.getTrains();
        switch (Filter){
            case FacultyFilter:
                return getFilterTrainByCity(trains);
            case DateFilter:
                return getFilterStudentByDate(trains);
            default:
                return trains;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public ArrayList<Train> getFilterStudentUniversal(String Type, String Filter) {
        switch (Type){
            case "City":
                return getFilterTrainByCity(Filter);
            case "Date":
                return getFilterStudentByDate(Filter);
            default:
                return getTrains();
        }
    }

    public void deleteTrainById(int id){
        database.delete(id);
    }

    public void updateTrain(Train train){
        database.update(train);
    }

    public Train getTrain(int id){
        return database.getTrain(id);
    }

    public void insertTrain(Train train){
        database.insert(train);
    }

    public ArrayList<Train> getTrains(){
        return database.getTrains();
    }

    private ArrayList<Train> getFilterTrainByCity(ArrayList<Train> trains) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private ArrayList<Train> getFilterTrainByCity(String Filter) {
        ArrayList<Train> trains = getTrains();
        return (ArrayList<Train>) trains.stream()
                .filter(train ->
                        train.getDestination()
                                .equals(Filter))
                .collect(Collectors.toList());
    }

    private ArrayList<Train> getFilterStudentByDate(ArrayList<Train> trains) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private ArrayList<Train> getFilterStudentByDate(String Filter) {
        ArrayList<Train> trains = getTrains();
        String city = Filter.split("-")[0];
        int time = Integer.parseInt(Filter.split("-")[1].split(":")[0]);
        return (ArrayList<Train>) trains.stream()
            .filter(train ->
                (train.getDestination()
                    .equals(city) &&
                    Integer.parseInt(train.getDate().split(" ")[1].split(":")[0]) >= time))
            .collect(Collectors.toList());
    }
}

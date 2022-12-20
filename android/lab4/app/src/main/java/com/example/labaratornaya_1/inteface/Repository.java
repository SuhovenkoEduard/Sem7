package com.example.labaratornaya_1.inteface;

import com.example.labaratornaya_1.entity.Train;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public interface Repository {

    ArrayList<Train> getStudents() throws FileNotFoundException;
    Train getStudent(int id);
    long insert(Train train);
    long delete(int userId);
    long update(Train train);

}

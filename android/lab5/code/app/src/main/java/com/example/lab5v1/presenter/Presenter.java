package com.example.lab5v1.presenter;

import com.example.lab5v1.entity.Student;
import com.example.lab5v1.inteface.Contract;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Presenter  {

    private Contract.View mainView;
    private Contract.Model model;


    public Presenter(Contract.View mainView, Contract.Model model){
        this.mainView = mainView;
        this.model = model;
    }

    public void getStudents(){
        ArrayList<Student> students = null;
        try {
            students = model.getStudents();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mainView.showStudent(students);
    }

}

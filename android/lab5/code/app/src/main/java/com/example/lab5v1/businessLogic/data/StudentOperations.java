package com.example.lab5v1.businessLogic.data;

import com.example.lab5v1.entity.Student;

import java.util.Date;

public class StudentOperations {

    public Student getDataSplit(String studentData){
        if(studentData != null){
            String[] studentArgs = studentData.split(";");
            String[] BornDate = studentArgs[3].split("-");
            int year = Integer.parseInt(BornDate[2]);
            int month = Integer.parseInt(BornDate[1]);
            int date = Integer.parseInt(BornDate[0]);

            int id = Integer.parseInt(studentArgs[0]);

            return new Student(id, studentArgs[1], studentArgs[2],
                    new Date(year, month, date), studentArgs[4], studentArgs[5]);
        }
        return null;
    }

}

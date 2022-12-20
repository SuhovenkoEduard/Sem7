package com.example.lab5v1.inteface;

import com.example.lab5v1.entity.Student;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public interface Contract {

    interface View {

        void showStudent(ArrayList<Student> students);
        void showToast(String message);

        interface Options{
            String getStudentData();
        }
    }


    interface Model {

        void updateStudent(Student student);
        void insertStudent(Student student);
        void deleteStudentById(int id);

        Student getStudent(int id);
        ArrayList<Student> getStudents() throws FileNotFoundException;
        ArrayList<Student> getFilterStudent(String Filter);
        ArrayList<Student> getFilterStudentUniversal(String Type, String Filter);

    }

}

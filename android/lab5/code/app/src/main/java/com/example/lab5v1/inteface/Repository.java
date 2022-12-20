package com.example.lab5v1.inteface;

import com.example.lab5v1.entity.Student;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public interface Repository {

    ArrayList<Student> getStudents() throws FileNotFoundException;
    Student getStudent(int id);
    long insert(Student student);
    long delete(int userId);
    long update(Student student);

}

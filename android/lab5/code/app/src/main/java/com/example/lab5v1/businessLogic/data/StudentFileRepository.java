package com.example.lab5v1.businessLogic.data;

import android.content.Context;

import com.example.lab5v1.entity.Student;
import com.example.lab5v1.inteface.Repository;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

public class StudentFileRepository implements Repository {

    Context context;
    StudentFiles studentFiles;


    public StudentFileRepository(StudentFiles studentFiles, Context context){
        this.studentFiles = studentFiles;
        this.context = context;
    }


    @Override
    public ArrayList<Student> getStudents() throws FileNotFoundException {
        String student = studentFiles.readFile(context);
        Gson gson = new Gson();
        Student[] studentsArray = gson.fromJson(student, Student[].class);
        return new ArrayList<Student>(Arrays.asList(studentsArray));
    }

    @Override
    public Student getStudent(int id) {
        String student = studentFiles.readFile(context);
        Gson gson = new Gson();
        Student[] studentsArray = gson.fromJson(student, Student[].class);
        return studentsArray[id];
    }

    public void save(ArrayList<Student> students){
        studentFiles.writeFile(new Gson().toJson(students), context);
    }


    @Override
    public long insert(Student student) {
        return 0;
    }

    @Override
    public long delete(int userId) {
        return 0;
    }

    @Override
    public long update(Student student) {
        return 0;
    }
}

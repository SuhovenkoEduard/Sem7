package com.example.lab5v1.model;

import com.example.lab5v1.businessLogic.data.StudentFileRepository;
import com.example.lab5v1.entity.Student;
import com.example.lab5v1.inteface.Contract;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class FileModel implements Contract.Model {

    StudentFileRepository context;

    public FileModel(StudentFileRepository context){
        this.context = context;
    }


    @Override
    public void updateStudent(Student student) {
        context.update(student);
    }

    @Override
    public void insertStudent(Student student) {
        context.insert(student);
    }

    @Override
    public void deleteStudentById(int id) {
        context.delete(id);
    }

    @Override
    public Student getStudent(int id) {
        return context.getStudent(id);
    }

    @Override
    public ArrayList<Student> getStudents() throws FileNotFoundException {
        return context.getStudents();
    }

    public void Save(ArrayList<Student> students){
        context.save(students);
    }

    @Override
    public ArrayList<Student> getFilterStudent(String Filter) {
        return null;
    }

    @Override
    public ArrayList<Student> getFilterStudentUniversal(String Type, String Filter) {
        return null;
    }
}

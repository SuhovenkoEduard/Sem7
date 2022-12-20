package com.example.lab5v1.model;

import com.example.lab5v1.database.DbAdapter;
import com.example.lab5v1.entity.Student;
import com.example.lab5v1.inteface.Contract;

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
    public ArrayList<Student> getFilterStudent(String Filter) {

        ArrayList<Student> students = database.getStudents();

        switch (Filter){

            case FacultyFilter:
                return getFilterStudentByFaculty(students);

            case DateFilter:
                return getFilterStudentByDate(students);

            default:
                return students;
        }
    }

    public ArrayList<Student> getFilterStudentUniversal(String Type, String Filter) {

        switch (Type){

            case FacultyFilter:
                return getFilterStudentByFaculty(Filter);

            case "Year":
                return getFilterStudentByDate(Filter);

            default:
                return getStudents();
        }
    }

    public void deleteStudentById(int id){
        database.delete(id);
    }

    public void updateStudent(Student student){
        database.update(student);
    }

    public Student getStudent(int id){
        return database.getStudent(id);
    }

    public void insertStudent(Student student){
        database.insert(student);
    }

    public ArrayList<Student> getStudents(){
        return database.getStudents();
    }




    private ArrayList<Student> getFilterStudentByFaculty(ArrayList<Student> students) {
        return (ArrayList<Student>) students.stream()
                .filter(student ->
                        student.getFaculties()
                                .equals(FacultyNameFilter))
                .collect(Collectors.toList());
    }

    private ArrayList<Student> getFilterStudentByFaculty(String Filter) {
        ArrayList<Student> students = getStudents();
        return (ArrayList<Student>) students.stream()
                .filter(student ->
                        student.getFaculties()
                                .equals(Filter))
                .collect(Collectors.toList());
    }

    private ArrayList<Student> getFilterStudentByDate(ArrayList<Student> students) {
        return (ArrayList<Student>) students.stream()
                .filter(student ->
                        student.getDate().getYear() == YearIntFilter)
                .collect(Collectors.toList());
    }

    private ArrayList<Student> getFilterStudentByDate(String Filter) {
        ArrayList<Student> students = getStudents();
        return (ArrayList<Student>) students.stream()
                .filter(student ->
                        student.getDate().getYear() == Integer.parseInt(Filter))
                .collect(Collectors.toList());
    }


}

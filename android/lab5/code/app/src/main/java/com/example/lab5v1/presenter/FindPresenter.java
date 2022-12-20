package com.example.lab5v1.presenter;
import android.content.Context;

import com.example.lab5v1.activity.StudentActivity;
import com.example.lab5v1.activitySwitcher.ActivitySwitcher;
import com.example.lab5v1.entity.Student;
import com.example.lab5v1.inteface.Contract;

import java.util.ArrayList;

public class FindPresenter {

    private Contract.View mainView;
    private Contract.Model model;


    public FindPresenter(Contract.View mainView, Contract.Model model){
        this.mainView = mainView;
        this.model = model;
    }

    public void onFindByFaisSelect(){
        ArrayList<Student> studentsFilterFaculty = model.getFilterStudent("Faculty");
        ActivitySwitcher.switchActivity((Context) mainView,
                                        studentsFilterFaculty,
                                        StudentActivity.class);
    }
    public void onFindByBornSelect(){
        ArrayList<Student> studentsFilterDate = model.getFilterStudent("Date");
        ActivitySwitcher.switchActivity((Context) mainView,
                                        studentsFilterDate,
                                        StudentActivity.class);
    }

    public void onFindSelect(String Type, String Filter){
        ArrayList<Student> studentsFilter =  model.getFilterStudentUniversal(Type, Filter);
        ActivitySwitcher.switchActivity((Context) mainView,
                studentsFilter,
                StudentActivity.class);
    }
}

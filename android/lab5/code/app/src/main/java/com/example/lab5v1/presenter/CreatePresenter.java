package com.example.lab5v1.presenter;
import android.content.Context;

import com.example.lab5v1.activity.MainActivity;
import com.example.lab5v1.activity.StudentCreateActivity;
import com.example.lab5v1.activitySwitcher.ActivitySwitcher;
import com.example.lab5v1.businessLogic.data.StudentOperations;
import com.example.lab5v1.inteface.Contract;

public class CreatePresenter{

    private Contract.View mainView;
    private Contract.Model model;


    public CreatePresenter(Contract.View mainView, Contract.Model model){
        this.mainView = mainView;
        this.model = model;
    }

    public void onCreateSelect(){
        ActivitySwitcher.switchActivity((Context) mainView,  StudentCreateActivity.class);
    }

    public void onCreateClick(){
        StudentOperations studentOperations = new StudentOperations();
        String data = ((Contract.View.Options) mainView).getStudentData();
        model.insertStudent(studentOperations.getDataSplit(data));
        mainView.showToast("student was created");
        ActivitySwitcher.switchActivity((Context) mainView, MainActivity.class);
    }


}

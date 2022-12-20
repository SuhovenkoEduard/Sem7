package com.example.lab5v1.presenter;
import android.content.Context;

import com.example.lab5v1.activity.MainActivity;
import com.example.lab5v1.activity.RefactorActivity;
import com.example.lab5v1.activitySwitcher.ActivitySwitcher;
import com.example.lab5v1.businessLogic.data.StudentOperations;
import com.example.lab5v1.entity.Student;
import com.example.lab5v1.inteface.Contract;

public class RefactorPresenter  {

    private Contract.View mainView;
    private Contract.Model model;

    public RefactorPresenter(Contract.View mainView, Contract.Model model){
        this.mainView = mainView;
        this.model = model;
    }

    public void onRefactorSelect(Student student){
        if(student != null){
            ActivitySwitcher.switchActivity((Context) mainView, student, RefactorActivity.class);
        }else {
            mainView.showToast("Not select student");
        }
    }

    public void onRefactorClick(){
        StudentOperations studentOperations = new StudentOperations();
        String data = ((Contract.View.Options) mainView).getStudentData();
        model.updateStudent(studentOperations.getDataSplit(data));
        mainView.showToast("student was update");
        ActivitySwitcher.switchActivity((Context) mainView, MainActivity.class);

    }


}

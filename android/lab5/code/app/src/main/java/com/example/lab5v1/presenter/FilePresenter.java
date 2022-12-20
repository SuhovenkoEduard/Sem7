package com.example.lab5v1.presenter;

import com.example.lab5v1.inteface.Contract;
import com.example.lab5v1.model.FileModel;

import java.io.FileNotFoundException;

public class FilePresenter {

    private Contract.View mainView;
    private FileModel model;

    public FilePresenter(Contract.View mainView, FileModel model){
        this.mainView = mainView;
        this.model = model;
    }

    public void onSaveFile() throws FileNotFoundException {
        model.Save(model.getStudents());
        mainView.showToast("Data save in files");
    }


}

package com.example.lab5v1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab5v1.R;
import com.example.lab5v1.activitySwitcher.ActivitySwitcher;
import com.example.lab5v1.database.DbAdapter;
import com.example.lab5v1.entity.Student;
import com.example.lab5v1.inteface.Contract;
import com.example.lab5v1.model.Model;
import com.example.lab5v1.presenter.RefactorPresenter;
import com.example.lab5v1.services.SaveService;

import java.util.ArrayList;
import java.util.Objects;

public class RefactorActivity extends AppCompatActivity implements Contract.View, Contract.View.Options {

    private final String[] FacultiesAuto = new String[]{
            "Fais", "EF", "MF"
    };
    private final String[] GroupsAuto = new String[]{
            "Informatica Programming", "ITI", "PE"
    };


    RefactorPresenter presenter;
    String[] studentArgs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refactor);
        init();
    }

    public void init(){
        makeFullScreen();
        Student student = (Student) getIntent().getSerializableExtra("student");

        if(student != null){
            ArrayList<Student> students = new ArrayList<>();
            students.add(student);
            showStudent(students);
        }
        AutoTet();
        DbAdapter dbAdapter = new DbAdapter(this);
        Model model = new Model(dbAdapter);
        presenter = new RefactorPresenter(this, model);
    }

    private void makeFullScreen() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Objects.requireNonNull(getSupportActionBar()).hide();
    }

    @Override
    public void showStudent(ArrayList<Student> students) {
        Student refactorStudent = students.get(0);
        LinearLayout layout = (LinearLayout) findViewById(R.id.refactor_layout);
        ArrayList<EditText> editTextArrayList = new ArrayList<>();
        System.out.println(refactorStudent.getId());
        studentArgs = new String[]{
            String.valueOf(refactorStudent.getId()),
            refactorStudent.getSurname(),
            refactorStudent.getName(),
            refactorStudent.getRuDate(),
            refactorStudent.getFaculties(),
            refactorStudent.getGroup()
        };
        for(int i = 0; i < layout.getChildCount(); i++ )
            if( layout.getChildAt(i) instanceof EditText)
                editTextArrayList.add(((EditText) layout.getChildAt(i)));

        for(int i = 1; i < studentArgs.length; i++){
            editTextArrayList.get(i - 1).setText(studentArgs[i]);
        }


    }

    private void AutoTet(){
        AutoCompleteTextView autoCompleteFacultyTextView =
                (AutoCompleteTextView) findViewById(R.id.refactor_faculty_auto);


        ArrayAdapter<String> facultiesAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, FacultiesAuto
        );

        autoCompleteFacultyTextView.setAdapter(facultiesAdapter);

        AutoCompleteTextView autoCompleteGroupTextView =
                (AutoCompleteTextView) findViewById(R.id.refactor_group_auto);


        ArrayAdapter<String> groupAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, GroupsAuto
        );

        autoCompleteGroupTextView.setAdapter(groupAdapter);
    }

    @Override
    public void showToast(String message) {

    }

    public void onButtonClickRefactor(View view) {
        Intent intent = new Intent(getBaseContext(), SaveService.class);
        intent.putExtra("data", getStudentData());
        intent.setAction("Refactor");
        startService(intent);
        ActivitySwitcher.switchActivity(this, MainActivity.class);

    }

    @Override
    public String getStudentData() {
        LinearLayout layout = (LinearLayout) findViewById(R.id.refactor_layout);
        StringBuilder cont = new StringBuilder();
        cont.append(studentArgs[0]).append(";");
        for( int i = 0; i < layout.getChildCount(); i++ )
            if( layout.getChildAt(i) instanceof EditText)
                cont.append(((EditText) layout.getChildAt(i)).getText().toString()).append(";");


        return cont.toString();
    }


}
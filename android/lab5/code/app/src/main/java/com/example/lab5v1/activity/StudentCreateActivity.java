package com.example.lab5v1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab5v1.R;
import com.example.lab5v1.activitySwitcher.ActivitySwitcher;
import com.example.lab5v1.database.DbAdapter;
import com.example.lab5v1.entity.Student;
import com.example.lab5v1.inteface.Contract;
import com.example.lab5v1.model.Model;
import com.example.lab5v1.presenter.CreatePresenter;
import com.example.lab5v1.services.SaveService;

import java.util.ArrayList;
import java.util.Objects;

public class StudentCreateActivity extends AppCompatActivity implements Contract.View, Contract.View.Options {

    private final String[] FacultiesAuto = new String[]{
            "Fais", "EF", "MF"
    };
    private final String[] GroupsAuto = new String[]{
            "Informatica Programming", "ITI", "PE"
    };


    CreatePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_create);
        init();

    }

    public void init(){

        makeFullScreen();

        DbAdapter dbAdapter = new DbAdapter(this);
        Model model = new Model(dbAdapter);
        presenter = new CreatePresenter(this, model);

        AutoTet();
    }

    private void makeFullScreen() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Objects.requireNonNull(getSupportActionBar()).hide();
    }

    private void AutoTet(){
        AutoCompleteTextView autoCompleteFacultyTextView =
                (AutoCompleteTextView) findViewById(R.id.create_faculty_auto);


        ArrayAdapter<String> facultiesAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, FacultiesAuto
        );

        autoCompleteFacultyTextView.setAdapter(facultiesAdapter);

        AutoCompleteTextView autoCompleteGroupTextView =
                (AutoCompleteTextView) findViewById(R.id.create_group_auto);


        ArrayAdapter<String> groupAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, GroupsAuto
        );

        autoCompleteGroupTextView.setAdapter(groupAdapter);
    }


    public void onButtonClick(View view) {

        Intent intent_c = new Intent(getBaseContext(), SaveService.class);
        intent_c.putExtra("data1", getStudentData());
        intent_c.setAction("Create");
        startService(intent_c);
        ActivitySwitcher.switchActivity(this, MainActivity.class);
    }

    @Override
    public void showStudent(ArrayList<Student> students) {

    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getApplicationContext(), "Student was create",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getStudentData() {

        LinearLayout layout = (LinearLayout) findViewById(R.id.create_layout);
        StringBuilder cont = new StringBuilder();
        cont.append("1").append(";");
        for(int i = 0; i < layout.getChildCount(); i++)
            if(layout.getChildAt(i) instanceof EditText)
                cont.append(((EditText) layout.getChildAt(i)).getText().toString() + ";");


        return cont.toString();
    }
}
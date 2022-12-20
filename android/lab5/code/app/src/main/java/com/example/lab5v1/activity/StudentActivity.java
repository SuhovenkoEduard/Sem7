package com.example.lab5v1.activity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab5v1.R;
import com.example.lab5v1.adaptepters.StudentAdapter;
import com.example.lab5v1.entity.Student;
import com.example.lab5v1.inteface.Contract;

import java.util.ArrayList;
import java.util.Objects;

public class StudentActivity extends AppCompatActivity implements Contract.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        init();
    }

    private void init(){
        makeFullScreen();
        initializeStudent();
    }

    private void initializeStudent() {
        ArrayList<Student> students =
                (ArrayList<Student>) getIntent().getSerializableExtra("students");

        if(students != null){
            showStudent(students);
        }
    }

    private void makeFullScreen() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Objects.requireNonNull(getSupportActionBar()).hide();
    }


    @Override
    public void showStudent(ArrayList<Student> students) {
        LinearLayoutManager llm = new LinearLayoutManager(getBaseContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        RecyclerView listView = (RecyclerView) findViewById(R.id.filter_student);
        listView.setLayoutManager(llm);
        StudentAdapter a = new StudentAdapter((student, pos) -> Toast.makeText(getBaseContext(), "Student was selected",
                Toast.LENGTH_LONG).show(), this.getLayoutInflater(), students);
        listView.setAdapter(a);
    }


    @Override
    public void showToast(String message) {

    }
}
package com.example.lab5v1.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.lab5v1.R;
import com.example.lab5v1.activitySwitcher.ActivitySwitcher;
import com.example.lab5v1.adaptepters.StudentAdapter;
import com.example.lab5v1.businessLogic.data.StudentFileRepository;
import com.example.lab5v1.businessLogic.data.StudentFiles;
import com.example.lab5v1.database.DbAdapter;
import com.example.lab5v1.dialogfragments.DeleteDialogFragment;
import com.example.lab5v1.dialogfragments.InputDialogFragment;
import com.example.lab5v1.dialogfragments.LoadDialogFragment;
import com.example.lab5v1.entity.Student;
import com.example.lab5v1.fragments.FragmentOne;
import com.example.lab5v1.fragments.FragmentTwo;
import com.example.lab5v1.fragments.Utils;
import com.example.lab5v1.fragments.ViewPagerAdapter;
import com.example.lab5v1.inteface.Contract;
import com.example.lab5v1.model.FileModel;
import com.example.lab5v1.model.Model;
import com.example.lab5v1.presenter.CreatePresenter;
import com.example.lab5v1.presenter.DeletePresenter;
import com.example.lab5v1.presenter.FilePresenter;
import com.example.lab5v1.presenter.FindPresenter;
import com.example.lab5v1.presenter.RefactorPresenter;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity implements Contract.View, FragmentOne.SendMessage {
    Student activeStudent;

    FindPresenter findPresenter;
    RefactorPresenter refactorPresenter;
    DeletePresenter deletePresenter;
    CreatePresenter createPresenter;
    FilePresenter filePresenter;

    ViewPager2 viewPager;
    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_main);

        LoadDialogFragment loadDialogFragment = new LoadDialogFragment();
        loadDialogFragment.show(getSupportFragmentManager(), "Fragment");
        try {
            setTimer(loadDialogFragment);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void init(){

        viewPager = (ViewPager2) findViewById(R.id.pager);
        viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);


        DbAdapter dbAdapter = new DbAdapter(this);
        Model model = new Model(dbAdapter);
        findPresenter = new FindPresenter(this, model);
        refactorPresenter = new RefactorPresenter(this, model);
        deletePresenter = new DeletePresenter(this, model);
        createPresenter = new CreatePresenter(this, model);


        filePresenter = new FilePresenter(this, new FileModel(
                new StudentFileRepository(new StudentFiles(), (Context) this)
        ));

    }

    public void test(){
        StudentFiles studentFiles = new StudentFiles();
        System.out.println(studentFiles.readFile( (Context) this));
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.search_student_fais:
                InputDialogFragment inputFacultyFragment = new InputDialogFragment("Faculty");
                inputFacultyFragment.show(getSupportFragmentManager(), "custom");
                break;
            case R.id.search_student_born:
                InputDialogFragment inputYearFragment = new InputDialogFragment("Year");
                inputYearFragment.show(getSupportFragmentManager(), "custom1");
                break;
            case R.id.refactor_student:
                refactorPresenter.onRefactorSelect(activeStudent);
                break;
            case R.id.create_student:
                createPresenter.onCreateSelect();
                break;
            case R.id.delete_student:
                if (activeStudent != null){
                    DeleteDialogFragment deleteDialogFragment = new DeleteDialogFragment(activeStudent,
                            deletePresenter);
                    deleteDialogFragment.show(getSupportFragmentManager(), "delete1");
                }else {
                    showToast("Student wasn't select");
                }
                break;
            case R.id.save_file_student:
                try {
                    filePresenter.onSaveFile();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                test();
                break;

            case R.id.google_menu_map:
                ActivitySwitcher.switchActivity(this, MapsActivity.class);
                break;

        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void showStudent(ArrayList<Student> students) {
        RecyclerView listView = (RecyclerView) findViewById(R.id.students_list);
        StudentAdapter a = new StudentAdapter(new StudentAdapter.OnItemClickListener() {
            @Override
            public void onClick(Student student, int pos) {
                Toast.makeText(getBaseContext(), "Student was selected",
                        Toast.LENGTH_LONG).show();
            }
        }, this.getLayoutInflater(), students);
        listView.setAdapter(a);

    }


    @Override
    public void showToast(String message) {
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public void setTimer(DialogFragment dlg) throws InterruptedException {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                dlg.dismiss();
                runOnUiThread(() -> init());
                timer.cancel();
            }
        }, 1000);
    }

    @Override
    public void setActiveStudent(Student student) {
        activeStudent = student;
    }

    @Override
    public void sendData(String message) {
        FragmentTwo f = (FragmentTwo) getSupportFragmentManager().findFragmentByTag("f1");
        if (f != null){
                f.displayReceivedData(message);

        }
    }



}
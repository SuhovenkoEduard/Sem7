package com.example.labaratornaya_1.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.labaratornaya_1.R;
import com.example.labaratornaya_1.businessLogic.data.TrainFileRepository;
import com.example.labaratornaya_1.businessLogic.data.TrainFiles;
import com.example.labaratornaya_1.database.DbAdapter;
import com.example.labaratornaya_1.dialogfragments.DeleteDialogFragment;
import com.example.labaratornaya_1.dialogfragments.InputDialogFragment;
import com.example.labaratornaya_1.dialogfragments.InputDialogFragment2;
import com.example.labaratornaya_1.dialogfragments.LoadDialogFragment;
import com.example.labaratornaya_1.entity.Train;
import com.example.labaratornaya_1.fragments.FragmentOne;
import com.example.labaratornaya_1.fragments.FragmentTwo;
import com.example.labaratornaya_1.fragments.Utils;
import com.example.labaratornaya_1.fragments.ViewPagerAdapter;
import com.example.labaratornaya_1.inteface.Contract;
import com.example.labaratornaya_1.model.FileModel;
import com.example.labaratornaya_1.model.Model;
import com.example.labaratornaya_1.presenter.CreatePresenter;
import com.example.labaratornaya_1.presenter.DeletePresenter;
import com.example.labaratornaya_1.presenter.FilePresenter;
import com.example.labaratornaya_1.presenter.FindPresenter;
import com.example.labaratornaya_1.presenter.RefactorPresenter;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity implements Contract.View, FragmentOne.SendMessage {
    Train activeTrain;

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
                new TrainFileRepository(new TrainFiles(), (Context) this)
        ));

    }

    public void test(){
        TrainFiles trainFiles = new TrainFiles();
        System.out.println(trainFiles.readFile( (Context) this));
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.search_train:
                InputDialogFragment dialogCity = new InputDialogFragment("City");
                dialogCity.show(getSupportFragmentManager(), "custom");
                break;
            case R.id.search_train_date:
                InputDialogFragment2 dialogCityAndTime = new InputDialogFragment2("Date");
                dialogCityAndTime.show(getSupportFragmentManager(), "custom1");
                break;
            case R.id.refactor_train:
                refactorPresenter.onRefactorSelect(activeTrain);
                break;
            case R.id.create_train:
                createPresenter.onCreateSelect();
                break;
            case R.id.delete_train:
                if (activeTrain != null){
                    DeleteDialogFragment deleteDialogFragment = new DeleteDialogFragment(activeTrain,
                            deletePresenter);
                    deleteDialogFragment.show(getSupportFragmentManager(), "delete1");
                }else {
                    showToast("Train wasn't select");
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

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showTrain(ArrayList<Train> trains) {
        ListView listView = (ListView) findViewById(R.id.students_list);
        ArrayAdapter<Train> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
            trains);
        listView.setAdapter(adapter);
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
    public void setActiveTrain(Train train) {
        activeTrain = train;
    }

    @Override
    public void sendData(String message) {
        FragmentTwo f = (FragmentTwo) getSupportFragmentManager().findFragmentByTag("f1");
        if (f != null){
                f.displayReceivedData(message);
        }
    }
}
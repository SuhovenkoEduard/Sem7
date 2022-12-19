package com.example.labaratornaya_1.activity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.labaratornaya_1.R;
import com.example.labaratornaya_1.entity.Train;
import com.example.labaratornaya_1.inteface.Contract;

import java.util.ArrayList;
import java.util.Objects;

public class TrainActivity extends AppCompatActivity implements Contract.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train);
        init();
    }

    private void init(){
        ArrayList<Train> trains =
                (ArrayList<Train>) getIntent().getSerializableExtra("trains");

        if(trains != null){
            showTrain(trains);
        }
    }

    @Override
    public void showTrain(ArrayList<Train> trains) {
        ListView listView = (ListView) findViewById(R.id.filter_student);
        ArrayAdapter<Train> adapter = new ArrayAdapter<Train>(this,
                android.R.layout.simple_list_item_1,
            trains);
        listView.setAdapter(adapter);
    }

    @Override
    public void showToast(String message) {}
}
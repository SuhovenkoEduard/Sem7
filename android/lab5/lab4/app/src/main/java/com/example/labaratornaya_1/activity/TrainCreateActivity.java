package com.example.labaratornaya_1.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.labaratornaya_1.R;
import com.example.labaratornaya_1.database.DbAdapter;
import com.example.labaratornaya_1.entity.Train;
import com.example.labaratornaya_1.inteface.Contract;
import com.example.labaratornaya_1.model.Model;
import com.example.labaratornaya_1.presenter.CreatePresenter;

import java.util.ArrayList;

public class TrainCreateActivity extends AppCompatActivity implements Contract.View, Contract.View.Options {

    private final String[] CityAuto = new String[]{
            "Paris", "Berlin", "Minsk", "Kiev"
    };
    private final String[] NumberAuto = new String[]{
            "100", "120", "130", "140", "150", "160", "170", "180", "190", "200"
    };

    CreatePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_create);
        init();

    }

    public void init(){
        DbAdapter dbAdapter = new DbAdapter(this);
        Model model = new Model(dbAdapter);
        presenter = new CreatePresenter(this, model);
        AutoTet();
    }

    private void AutoTet(){
        AutoCompleteTextView autoCompleteFacultyTextView =
                (AutoCompleteTextView) findViewById(R.id.create_faculty_auto);

        ArrayAdapter<String> facultiesAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, CityAuto
        );

        autoCompleteFacultyTextView.setAdapter(facultiesAdapter);
        AutoCompleteTextView autoCompleteGroupTextView =
                (AutoCompleteTextView) findViewById(R.id.create_group_auto);

        ArrayAdapter<String> groupAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, NumberAuto
        );

        autoCompleteGroupTextView.setAdapter(groupAdapter);
    }


    public void onButtonClick(View view) {
        presenter.onCreateClick();
    }

    @Override
    public void showTrain(ArrayList<Train> trains) {

    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getApplicationContext(), "Train was create",
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
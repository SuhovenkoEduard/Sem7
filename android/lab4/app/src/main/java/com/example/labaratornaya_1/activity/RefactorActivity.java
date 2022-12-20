package com.example.labaratornaya_1.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.labaratornaya_1.R;
import com.example.labaratornaya_1.database.DbAdapter;
import com.example.labaratornaya_1.entity.Train;
import com.example.labaratornaya_1.inteface.Contract;
import com.example.labaratornaya_1.model.Model;
import com.example.labaratornaya_1.presenter.RefactorPresenter;

import java.util.ArrayList;

public class RefactorActivity extends AppCompatActivity implements Contract.View, Contract.View.Options {

    private final String[] CityAuto = new String[]{
        "Paris", "Berlin", "Minsk", "Kiev"
    };
    private final String[] NumberAuto = new String[]{
        "100", "120", "130", "140", "150", "160", "170", "180", "190", "200"
    };


    RefactorPresenter presenter;
    String[] trainArgs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refactor);
        init();
    }

    public void init(){
        Train train = (Train) getIntent().getSerializableExtra("train");

        if(train != null){
            ArrayList<Train> trains = new ArrayList<>();
            trains.add(train);
            showTrain(trains);
        }
        AutoTet();
        DbAdapter dbAdapter = new DbAdapter(this);
        Model model = new Model(dbAdapter);
        presenter = new RefactorPresenter(this, model);
    }

    @Override
    public void showTrain(ArrayList<Train> trains) {
        Train refactorTrain = trains.get(0);
        LinearLayout layout = (LinearLayout) findViewById(R.id.refactor_layout);
        ArrayList<EditText> editTextArrayList = new ArrayList<>();
        System.out.println(refactorTrain.getId());
        trainArgs = new String[]{
            String.valueOf(refactorTrain.getId()),
            refactorTrain.getDestination(),
            refactorTrain.getNumber(),
            refactorTrain.getDateArrive(),
            refactorTrain.getCoupe(),
            refactorTrain.getPlatskart()
        };
        for(int i = 0; i < layout.getChildCount(); i++ )
            if( layout.getChildAt(i) instanceof EditText)
                editTextArrayList.add(((EditText) layout.getChildAt(i)));

        for(int i = 1; i < trainArgs.length; i++){
            editTextArrayList.get(i - 1).setText(trainArgs[i]);
        }
    }

    private void AutoTet(){
        AutoCompleteTextView autoCompleteFacultyTextView =
                (AutoCompleteTextView) findViewById(R.id.refactor_faculty_auto);

        ArrayAdapter<String> facultiesAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, CityAuto
        );

        autoCompleteFacultyTextView.setAdapter(facultiesAdapter);
        AutoCompleteTextView autoCompleteGroupTextView =
                (AutoCompleteTextView) findViewById(R.id.refactor_group_auto);
        ArrayAdapter<String> groupAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, NumberAuto
        );

        autoCompleteGroupTextView.setAdapter(groupAdapter);
    }

    @Override
    public void showToast(String message) {}

    public void onButtonClickRefactor(View view) {
        presenter.onRefactorClick();
    }

    @Override
    public String getStudentData() {
        LinearLayout layout = (LinearLayout) findViewById(R.id.refactor_layout);
        StringBuilder cont = new StringBuilder();
        cont.append(trainArgs[0]).append(";");
        for( int i = 0; i < layout.getChildCount(); i++ )
            if( layout.getChildAt(i) instanceof EditText)
                cont.append(((EditText) layout.getChildAt(i)).getText().toString()).append(";");
        return cont.toString();
    }
}
package com.example.labaratornaya_1.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.labaratornaya_1.R;
import com.example.labaratornaya_1.activity.MainActivity;
import com.example.labaratornaya_1.database.DbAdapter;
import com.example.labaratornaya_1.entity.Train;
import com.example.labaratornaya_1.model.Model;

public class FragmentOne extends Fragment{

    public static final int IDM_A = 101;
    public static final int IDM_B = 102;
    public static final int IDM_C = 103;

    ListView studentListView;
    SendMessage SM;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.fragment_page, container, false);

        Button button = (Button)rootView.findViewById(R.id.test_a);
        registerForContextMenu(button);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DbAdapter dbAdapter = new DbAdapter(getActivity());
        Model model = new Model(dbAdapter);
        studentListView = (ListView) view.findViewById(R.id.students_list);

        studentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                view.setSelected(true);
                SM.sendData(((Train) adapterView.getItemAtPosition(i)).getFullInfoString());
                SM.setActiveTrain(((Train) adapterView.getItemAtPosition(i)));
                Toast.makeText(getActivity(), "Train was selected",
                        Toast.LENGTH_LONG).show();
            }
        });




        ArrayAdapter<Train> adapter = new ArrayAdapter<Train>(getActivity(),
                android.R.layout.simple_list_item_1,
                model.getTrains());
        studentListView.setAdapter(adapter);

    }

    public interface SendMessage {
        void setActiveTrain(Train train);
        void sendData(String message);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            SM = (SendMessage) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Error in retrieving data. Please try again");
        }
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(Menu.NONE, IDM_A, Menu.NONE, "Тема 1");
        menu.add(Menu.NONE, IDM_B, Menu.NONE, "Тема 2");
        menu.add(Menu.NONE, IDM_C, Menu.NONE, "Тема 3");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case IDM_A:
                Utils.changeToTheme((MainActivity)getActivity(), Utils.THEME_DEFAULT);
                return true;
            case IDM_B:
                Utils.changeToTheme((MainActivity)getActivity(), Utils.THEME_DARK);
                return true;
            case IDM_C:
                Utils.changeToTheme((MainActivity)getActivity(), Utils.THEME_GREEN);
                return true;
        }
        return super.onContextItemSelected(item);
    }
}
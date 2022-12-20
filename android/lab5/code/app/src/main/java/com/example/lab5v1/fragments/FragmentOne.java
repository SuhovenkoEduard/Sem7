package com.example.lab5v1.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab5v1.R;
import com.example.lab5v1.activity.MainActivity;
import com.example.lab5v1.adaptepters.StudentAdapter;
import com.example.lab5v1.database.DbAdapter;
import com.example.lab5v1.entity.Student;
import com.example.lab5v1.model.Model;

public class FragmentOne extends Fragment{

    public static final int IDM_A = 101;
    public static final int IDM_B = 102;
    public static final int IDM_C = 103;

    RecyclerView studentListView;
    SendMessage SM;
    StudentAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.fragment_page, container, false);

        Button button = (Button)rootView.findViewById(R.id.test_a);
        registerForContextMenu(button);
        DbAdapter dbAdapter = new DbAdapter(getActivity());
        Model model = new Model(dbAdapter);
        adapter = new StudentAdapter(new StudentAdapter.OnItemClickListener() {
            @Override
            public void onClick(Student student, int pos) {
                rootView.setSelected(true);
                SM.sendData(student.getFullInfoString());
                SM.setActiveStudent(student);
                Toast.makeText(getActivity(), "Student was selected",
                        Toast.LENGTH_LONG).show();
            }
        }, inflater, model.getStudents());
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        studentListView = (RecyclerView) view.findViewById(R.id.students_list);
        studentListView.setLayoutManager(llm);
        studentListView.setAdapter(adapter);
    }

    public interface SendMessage {
        void setActiveStudent(Student student);
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
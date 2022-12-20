package com.example.labaratornaya_1.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.labaratornaya_1.R;

public class FragmentTwo extends Fragment {

    TextView txtData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.train_info_layout, container, false);
        return rootView;
    }

    public static FragmentTwo newInstance(String mes) {
        FragmentTwo fragment = new FragmentTwo();
        Bundle args=new Bundle();
        args.putString("std", mes);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtData = (TextView)view.findViewById(R.id.qwerty);
        displayReceivedData(getArguments() != null ? getArguments().getString("std") : "No data");
    }

    public void displayReceivedData(String message)
    {
        txtData.setText(message);
    }
}
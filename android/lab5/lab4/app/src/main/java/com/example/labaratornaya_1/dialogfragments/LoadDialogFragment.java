package com.example.labaratornaya_1.dialogfragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;

import com.example.labaratornaya_1.R;

public class LoadDialogFragment extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle){
        super.onCreateView(layoutInflater, viewGroup, bundle);
        return layoutInflater.inflate(R.layout.dialog_fragment, viewGroup, false);
    }
}

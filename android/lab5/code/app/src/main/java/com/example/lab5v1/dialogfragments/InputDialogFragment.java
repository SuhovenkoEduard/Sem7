package com.example.lab5v1.dialogfragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import com.example.lab5v1.R;
import com.example.lab5v1.activity.MainActivity;
import com.example.lab5v1.database.DbAdapter;
import com.example.lab5v1.model.Model;
import com.example.lab5v1.presenter.FindPresenter;

public class InputDialogFragment extends DialogFragment {

    FindPresenter presenter;
    String Type;

    public InputDialogFragment(String Type){
        this.Type = Type;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        DbAdapter dbAdapter = new DbAdapter(getActivity());
        Model model = new Model(dbAdapter);
        presenter = new FindPresenter((MainActivity) getActivity(), model);
        View DialogView = inflater.inflate(R.layout.dialog_data_fragment ,null);
        EditText editText = DialogView.findViewById(R.id.dialog_data_text);

        builder .setView(DialogView)
                .setTitle("Enter the faculty to search for ")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String Filter = editText.getText().toString();
                if(!Filter.equals("")){
                    presenter.onFindSelect(Type, Filter);
                }

            }
        });
        return builder.create();
    }

}

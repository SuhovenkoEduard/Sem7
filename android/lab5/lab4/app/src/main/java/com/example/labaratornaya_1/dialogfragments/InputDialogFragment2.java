package com.example.labaratornaya_1.dialogfragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import com.example.labaratornaya_1.R;
import com.example.labaratornaya_1.activity.MainActivity;
import com.example.labaratornaya_1.database.DbAdapter;
import com.example.labaratornaya_1.model.Model;
import com.example.labaratornaya_1.presenter.FindPresenter;

public class InputDialogFragment2 extends DialogFragment {

  FindPresenter presenter;
  String Type;

  public InputDialogFragment2(String Type){
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
    View DialogView = inflater.inflate(R.layout.dialog_data_fragment2 ,null);
    EditText editTextCity = DialogView.findViewById(R.id.dialog_data_text_city);
    EditText editTextTime = DialogView.findViewById(R.id.dialog_data_text_time);

    builder.setView(DialogView)
        .setTitle("Enter the city and time")
        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.cancel();
          }
        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
            String Filter = editTextCity.getText().toString() + "-" + editTextTime.getText().toString();
            presenter.onFindSelect(Type, Filter);
          }
        });
    return builder.create();
  }

}

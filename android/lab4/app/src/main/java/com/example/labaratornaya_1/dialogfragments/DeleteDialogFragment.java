package com.example.labaratornaya_1.dialogfragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.labaratornaya_1.entity.Train;
import com.example.labaratornaya_1.presenter.DeletePresenter;

public class DeleteDialogFragment extends DialogFragment {

    private DeletePresenter presenter;
    private Train train;
    public DeleteDialogFragment(Train activeTrain, DeletePresenter presenter) {
        this.train = activeTrain;
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        String title = "Delete train with name: "  + train.getDestination()
                                                     + " "
                                                     + train.getNumber();
        String message = "Are you sure?";

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Delete item", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                presenter.onDeleteSelect(train);
                dialog.cancel();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.setCancelable(true);

        return builder.create();
    }
}

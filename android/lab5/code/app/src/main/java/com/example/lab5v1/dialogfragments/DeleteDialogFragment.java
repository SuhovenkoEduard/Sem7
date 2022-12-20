package com.example.lab5v1.dialogfragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.lab5v1.entity.Student;
import com.example.lab5v1.presenter.DeletePresenter;

public class DeleteDialogFragment extends DialogFragment {

    private DeletePresenter presenter;
    private Student student;
    public DeleteDialogFragment(Student activeStudent, DeletePresenter presenter) {
        this.student = activeStudent;
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        String title = "Delete student with name: "  + student.getName()
                                                     + " "
                                                     + student.getSurname();
        String message = "Are u sure?";

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Delete item", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                presenter.onDeleteSelect(student);
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

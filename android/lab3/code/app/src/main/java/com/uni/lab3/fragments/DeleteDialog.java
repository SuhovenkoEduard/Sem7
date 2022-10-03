package com.uni.lab3.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.uni.lab3.R;

import java.util.Arrays;

public class DeleteDialog extends DialogFragment {
    public static String NOT_ALERT_DIALOG = "notAlertDialog";
    public static String FULLSCREEN = "fullScreen";
    public static String ALERT_TITLE = "alertTitle";
    public static String ALERT_MESSAGE = "alertMessage";
    public static String PRODUCT_ID = "productId";
    public static String PRODUCT_IDS = "productIds";
    public static String OK = "Ok";
    public static String CANCEL = "Cancel";

    public static String SELECT_DIALOG = "selectDialog";
    public static String CONFIRMATION_DIALOG = "confirmationDialog";
    DialogListener dialogListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        dialogListener = (DialogListener) getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (getArguments() != null) {
            if (getArguments().getBoolean(NOT_ALERT_DIALOG)) {
                return super.onCreateDialog(savedInstanceState);
            } else {
                builder.setTitle(getArguments().getString(ALERT_TITLE));
                builder.setMessage(getArguments().getString(ALERT_MESSAGE) + " " + getArguments().getInt(PRODUCT_ID));
                builder.setPositiveButton(OK, (dialog, which) -> {
                    dialogListener.onConfirmDeleteProductId(getArguments().getInt(PRODUCT_ID));
                    dialogListener.removeDeleteDialogFragments();
                });
                builder.setNegativeButton(CANCEL, (dialog, which) -> dismiss());
            }
        }
        return builder.create();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Spinner productIdSpinner = view.findViewById(R.id.productIdSpinner);
        if (getArguments() != null && getArguments().getSerializable(PRODUCT_IDS) != null) {
            String[] productIds = Arrays.stream(((int[]) getArguments().getSerializable(PRODUCT_IDS))).mapToObj(Integer::toString).toArray(String[]::new);
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), R.layout.list_item, productIds);
            productIdSpinner.setAdapter(arrayAdapter);

            Button deleteProductButton = view.findViewById(R.id.deleteProductButton);
            deleteProductButton.setOnClickListener(view1 -> dialogListener.onSelectProductIdToDelete(Integer.parseInt(productIdSpinner.getSelectedItem().toString())));
            Button cancelDeletionButton = view.findViewById(R.id.cancelDeletionButton);
            cancelDeletionButton.setOnClickListener(view12 -> dialogListener.removeDeleteDialogFragments());
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean setFullScreen = false;
        if (getArguments() != null) {
            setFullScreen = getArguments().getBoolean(FULLSCREEN);
        }

        if (setFullScreen)
            setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_delete_dialog, container, false);
    }

    public interface DialogListener {
        void onSelectProductIdToDelete(int selectedProductId);
        void onConfirmDeleteProductId(int productId);
        void removeDeleteDialogFragments();
    }
}
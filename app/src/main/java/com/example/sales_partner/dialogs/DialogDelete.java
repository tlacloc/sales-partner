package com.example.sales_partner.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

public class DialogDelete extends DialogFragment {
    public static final String TAG = "EjemploDeDialogo";

    public interface OnDialogListener{
        void OnPositiveButtonClicked();
        void OnNegativeButtonClicked();
    }

    private OnDialogListener onDialogListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        onDialogListener = (OnDialogListener)getActivity();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());

        builder.setTitle("Eliminar")
                .setMessage("¿Desea eliminar definitivamente?")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onDialogListener.OnPositiveButtonClicked();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onDialogListener.OnNegativeButtonClicked();
                    }
                });
        return builder.create();
    }
}

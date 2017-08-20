package com.codepath.simpletodo.ui.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.codepath.simpletodo.ui.activities.MainActivity;

/**
 * Created by yingbwan on 8/13/2017.
 */

public class MyAlertDialogFragment extends DialogFragment {

    public MyAlertDialogFragment() {
        // Empty constructor required for DialogFragment
    }

    public static MyAlertDialogFragment newInstance(String title) {

        MyAlertDialogFragment frag = new MyAlertDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = getArguments().getString("title");

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        alertDialogBuilder.setTitle(title);

        alertDialogBuilder.setMessage("Are you sure?");

        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity act = (MainActivity) getActivity();
                act.onDialogPositiveClick("OK");
            }
        });

        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity act = (MainActivity) getActivity();
                act.onDialogNegativeClick("CANCEL");
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        return alertDialogBuilder.create();
    }

}

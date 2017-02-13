package com.fatzombie.cooklist;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by anton on 1/11/14.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class DefaultUnitDialog extends DialogFragment {

    DefaultUnitDialogInterface listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View layOut =  (View) inflater.inflate(R.layout.default_unit_dialog, null);

        final EditText unit = (EditText) layOut.findViewById(R.id.etDefaultUnitDialog);

        builder.setView(layOut).setTitle(getActivity().getResources().getString(R.string.unit));

        builder.setPositiveButton(getActivity().getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                listener.defaultUnitDialogPositiveClick(unit.getText().toString());
            }
        })
                .setNegativeButton(getActivity().getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        return builder.create();
    }


    public interface DefaultUnitDialogInterface{
        void defaultUnitDialogPositiveClick(String unit);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (DefaultUnitDialogInterface) activity;
        }catch (Exception ex){}
    }

}

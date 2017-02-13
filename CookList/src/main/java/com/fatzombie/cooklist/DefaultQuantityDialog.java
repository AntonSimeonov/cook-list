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
import android.widget.Toast;

/**
 * Created by anton on 1/11/14.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class DefaultQuantityDialog extends DialogFragment {

    DefaultQuantityDialogInterface listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View layOut =  (View) inflater.inflate(R.layout.default_quantity_dialog, null);

        final EditText quantity = (EditText) layOut.findViewById(R.id.etDefaultQuantityDialog);

        builder.setView(layOut).setTitle(getActivity().getResources().getString(R.string.quantity));

        builder.setPositiveButton(getActivity().getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                float quan;
                try{
                   quan =  Float.parseFloat(quantity.getText().toString());
                }catch (Exception ex){
                    quan = 0;
                    Toast.makeText(getActivity(), R.string.toast_incorect_value, Toast.LENGTH_LONG).show();
                }

                listener.defaultQuantityDialogPositiveClick(quan);
            }
        })
                .setNegativeButton(getActivity().getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        return builder.create();
    }


    public interface DefaultQuantityDialogInterface{
        void defaultQuantityDialogPositiveClick(float quantity);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (DefaultQuantityDialogInterface) activity;
        }catch (Exception ex){}
    }
}

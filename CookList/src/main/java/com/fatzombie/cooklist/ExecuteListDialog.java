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
 * Created with IntelliJ IDEA.
 * User: anton
 * Date: 12/24/13
 * Time: 10:04 AM
 * To change this template use File | Settings | File Templates.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ExecuteListDialog extends DialogFragment {

    ExecuteListDialogListener dialogListener;

    public interface ExecuteListDialogListener{
       void onExecuteListDialogPositiveClick(float productPrice);
       void onExecuteListDialogNegativeClick();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View layOut =  (View) inflater.inflate(R.layout.execute_list_dialog, null);

        final EditText price = (EditText) layOut.findViewById(R.id.etExecutePrice);

        builder.setTitle(getActivity().getResources().getString(R.string.price)).setView(layOut).setPositiveButton(getActivity().getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                try {
                    dialogListener.onExecuteListDialogPositiveClick(Float.parseFloat(price.getText().toString()));
                } catch (Exception e) {
                    Toast.makeText(getActivity(), R.string.toast_incorect_value, Toast.LENGTH_LONG).show();
                }

            }
        }).setNegativeButton(getActivity().getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialogListener.onExecuteListDialogNegativeClick();
            }
        });
        return builder.create();

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{

            dialogListener = (ExecuteListDialogListener) activity;

        }catch (Exception ex){

        }

    }
}

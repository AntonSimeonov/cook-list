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
 * Created with IntelliJ IDEA.
 * User: anton
 * Date: 1/1/14
 * Time: 2:45 PM
 * To change this template use File | Settings | File Templates.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class CreateNewProductNameDialog extends DialogFragment {

    CreateNewProductNameListener newProductNameListener;

    public interface CreateNewProductNameListener{
        public void onNewProductNameDialogPositiveClick(DialogFragment dialog, String newProduct);
        public void onNewProductNameDialogNegativeClick(DialogFragment dialog);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View layOut =  (View) inflater.inflate(R.layout.add_new_product_name, null);

        final EditText newProductName = (EditText) layOut.findViewById(R.id.etAddNewProductNameDilaog);
        builder.setView(layOut).setTitle(getActivity().getResources().getString(R.string.add_brand_variety_dialog));

        builder.setPositiveButton(getActivity().getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                newProductNameListener.onNewProductNameDialogPositiveClick(CreateNewProductNameDialog.this,newProductName.getText().toString());
            }
        })
                .setNegativeButton(getActivity().getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    newProductNameListener.onNewProductNameDialogNegativeClick(CreateNewProductNameDialog.this);
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);    //To change body of overridden methods use File | Settings | File Templates.

        try{

            newProductNameListener = (CreateNewProductNameListener) activity;

        }catch (Exception ex){

        }

    }
}


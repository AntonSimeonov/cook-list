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
 * User: NTS Two
 * Date: 11/25/13
 * Time: 10:15 AM
 * To change this template use File | Settings | File Templates.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class CreateNewProductDialog extends DialogFragment {

    CreateNewProductListener newProductListener;

    public interface CreateNewProductListener{
        public void onNewProductDialogPositiveClick(DialogFragment dialog, String newProduct);
        public void onNewProductDialogNegativeClick(DialogFragment dialog);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View layOut =  (View) inflater.inflate(R.layout.add_new_product, null);

        final EditText newProduct = (EditText) layOut.findViewById(R.id.etAddNewProductDilaog);
        builder.setView(layOut).setTitle(getActivity().getResources().getString(R.string.add_product_dialog));

        builder.setPositiveButton(getActivity().getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                newProductListener.onNewProductDialogPositiveClick(CreateNewProductDialog.this, newProduct.getText().toString());
            }
        })
                .setNegativeButton(getActivity().getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        newProductListener.onNewProductDialogNegativeClick(CreateNewProductDialog.this);
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);    //To change body of overridden methods use File | Settings | File Templates.

        try{

            newProductListener = (CreateNewProductListener) activity;

        }catch (Exception ex){

        }

    }
}

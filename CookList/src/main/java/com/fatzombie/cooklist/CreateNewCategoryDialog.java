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
 * Date: 11/24/13
 * Time: 2:55 PM
 * To change this template use File | Settings | File Templates.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class CreateNewCategoryDialog extends DialogFragment {

    CreateNewCategoryListener newCategoryListener;

    public interface CreateNewCategoryListener{

        public void onDialogPositiveClick(DialogFragment dialog, String newCat);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View layOut =  (View) inflater.inflate(R.layout.add_new_category, null);
        final EditText newCat = (EditText) layOut.findViewById(R.id.etAddNewCategoryDialog);
        builder.setView(layOut);

        builder.setTitle(getActivity().getResources().getString(R.string.add_category_dialog));

       final EditText catName = (EditText) getActivity().findViewById(R.id.etAddNewCategoryDialog);
                builder.setPositiveButton(getActivity().getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        newCategoryListener.onDialogPositiveClick(CreateNewCategoryDialog.this, newCat.getText().toString());
                    }
                })
                .setNegativeButton(getActivity().getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        newCategoryListener.onDialogNegativeClick(CreateNewCategoryDialog.this);
                    }
                });

        return builder.create();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);    //To change body of overridden methods use File | Settings | File Templates.

        try{

            newCategoryListener = (CreateNewCategoryListener) activity;

        }catch (Exception ex){

        }

    }
}

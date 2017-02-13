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
import android.widget.TextView;

/**
 * Created with IntelliJ IDEA.
 * User: NTS Two
 * Date: 11/27/13
 * Time: 12:03 PM
 * To change this template use File | Settings | File Templates.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class CreateNewListProductDialog extends DialogFragment implements View.OnClickListener{

    CreateNewListProductInterface newListProductInterface;




    //TESTINF FOR DEFOULT QUANTITY FUNCTIONALITY
    @Override
    public void onClick(View v) {
        //To change body of implemented methods use File | Settings | File Templates.
//        String defaultQuantity = "0";
//
//        switch (v.getId()){
//            case R.id.tvDefaultQuantity1:
//                defaultQuantity = defaultQuantity1.getText().toString();
//                break;
//            case R.id.tvDefaultQuantity2:
//                defaultQuantity = defaultQuantity2.getText().toString();
//                break;
//            case R.id.tvDefaultQuantity3:
//                defaultQuantity = defaultQuantity3.getText().toString();
//                break;
//            case R.id.tvDefaultQuantity4:
//                defaultQuantity = defaultQuantity4.getText().toString();
//                break;
//        }
//
//        newListProductInterface.onNewListProductDialogPositiveClick(CreateNewListProductDialog.this, "",
//                Float.parseFloat(defaultQuantity), "Kg", 0
//                , "");
    }

    public interface CreateNewListProductInterface{
        public void onNewListProductDialogPositiveClick(DialogFragment dialog, String brand, float quantity, String quantityUnits, float price, String currency);
        public void onNewListProductDialogNegativeClick(DialogFragment dialog);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View layOut =  (View) inflater.inflate(R.layout.add_new_list_product, null);


        //final EditText brand = (EditText) layOut.findViewById(R.id.etNewListProductBrandDialog);
        final EditText quantity = (EditText) layOut.findViewById(R.id.etNewListProductQuantityDialog);
        final EditText quantityUnits = (EditText) layOut.findViewById(R.id.etNewListProductQuantityUnitsDialog);
        quantityUnits.setText(R.string.kg);
        //final EditText price = (EditText) layOut.findViewById(R.id.etNewListProductPriceDialog);
        //final EditText currency = (EditText) layOut.findViewById(R.id.etNewListProductPriceCurencyDialog);


        builder.setView(layOut).setTitle("Add new List Product");

        builder.setPositiveButton(getActivity().getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                newListProductInterface.onNewListProductDialogPositiveClick(CreateNewListProductDialog.this, "",
                Float.parseFloat(quantity.getText().toString()), quantityUnits.getText().toString(), 0
                        , "");
            }
        })
                .setNegativeButton(getActivity().getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        newListProductInterface.onNewListProductDialogNegativeClick(CreateNewListProductDialog.this);
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);    //To change body of overridden methods use File | Settings | File Templates.
        try{

            newListProductInterface = (CreateNewListProductInterface) activity;

        }catch (Exception ex){

        }
    }
}

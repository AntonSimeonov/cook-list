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
 * Date: 11/30/13
 * Time: 6:10 PM
 * To change this template use File | Settings | File Templates.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class EditProductDialog extends DialogFragment{

    public static String brandDialogDefalutValue;
    public static float quatityDialogDefaultValue;
    public static String quantityUnitDialogDefaultValue;
    public static float priceDialogDefaultValue;
    public static  String currencyDialogDefaultValue;

    EditNewListProductInterface editListProductInterface;

    public interface EditNewListProductInterface{
        public void onEditListProductDialogPositiveClick(DialogFragment dialog, String brand, float quantity, String quantityUnits, float price, String currency);
        public void onEditListProductDialogNegativeClick(DialogFragment dialog);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View layOut =  (View) inflater.inflate(R.layout.edit_list_product, null);
        final EditText brand = (EditText) layOut.findViewById(R.id.etEditListProductBrandDialog);
        brand.setText(brandDialogDefalutValue);
        final EditText quantity = (EditText) layOut.findViewById(R.id.etEditListProductQuantityDialog);
        quantity.setText("" + quatityDialogDefaultValue);
        final EditText quantityUnits = (EditText) layOut.findViewById(R.id.etEditListProductQuantityUnitsDialog);
       // quantityUnits.setText(R.string.kg);
        quantityUnits.setText(quantityUnitDialogDefaultValue);
        final EditText price = (EditText) layOut.findViewById(R.id.etEditListProductPriceDialog);
        price.setText("" + priceDialogDefaultValue);
        final EditText currency = (EditText) layOut.findViewById(R.id.etEditListProductPriceCurencyDialog);
        currency.setText(R.string.currency);

        builder.setView(layOut).setTitle(getActivity().getResources().getString(R.string.edit_list_product_title));

        builder.setPositiveButton(getActivity().getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                try{
                float quantityParced = Float.parseFloat(quantity.getText().toString());
                float priceParced = Float.parseFloat(price.getText().toString());
                editListProductInterface.onEditListProductDialogPositiveClick(EditProductDialog.this, brand.getText().toString(),
                        quantityParced, quantityUnits.getText().toString(), priceParced
                        , currency.getText().toString());
                }catch (Exception ex){
                    Toast.makeText(getActivity(), R.string.toast_incorect_value, Toast.LENGTH_LONG).show();
                }
            }
        })
                .setNegativeButton(getActivity().getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        editListProductInterface.onEditListProductDialogNegativeClick(EditProductDialog.this);
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);    //To change body of overridden methods use File | Settings | File Templates.
        try{

            editListProductInterface = (EditNewListProductInterface) activity;

        }catch (Exception ex){

        }
    }
}

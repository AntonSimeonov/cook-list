package com.fatzombie.cooklist;

import android.content.Context;
import android.graphics.Typeface;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: anton
 * Date: 12/24/13
 * Time: 5:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class ExecuteListProductsAdapter extends ArrayAdapter<Product>{

    private ArrayList<Product> products;
    private Context context;
    ViewHolderExe holder;
    private View rowView;
    public static final HashMap<Integer,Boolean> checkedProducts = new HashMap<Integer, Boolean>();


    public ExecuteListProductsAdapter(Context c, int id, ArrayList<Product> p ){
        super(c, id, p);
        products = p;
        context = c;
        //checkedProducts = new HashMap<Integer, Boolean>();
    }


    public static class ViewHolderExe{
        public TextView product;
        public TextView productName;
        public TextView quantity;
        public TextView price;
        public TextView unit;
        public TextView currency;
        public CheckBox checkProduct;
      //  public TextView substract;
        //public TextView add;
    }





    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        rowView = convertView;


        //if(rowView == null){

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.execute_product_row_layout, null);
            holder = new ViewHolderExe();
            Typeface font = Typeface.createFromAsset(getContext().getAssets(), "Roboto-Bold.ttf");
            Typeface fontThin = Typeface.createFromAsset(getContext().getAssets(), "Roboto-Thin.ttf");
            holder.product = (TextView) rowView.findViewById(R.id.tvProductForExecution);
            holder.product.setTypeface(font);
            holder.productName = (TextView) rowView.findViewById(R.id.tvProductNameForExecution);
            holder.productName.setTypeface(fontThin);
            holder.price = (TextView) rowView.findViewById(R.id.tvPriceForExecution);
            holder.price.setTypeface(fontThin);
            holder.quantity = (TextView) rowView.findViewById(R.id.tvQuantityForExecution);
            holder.quantity.setTypeface(fontThin);
            holder.unit = (TextView) rowView.findViewById(R.id.tvUnitForExecution);
            holder.unit.setTypeface(fontThin);
            holder.currency = (TextView) rowView.findViewById(R.id.tvCurrencyForExecution);
            holder.currency.setTypeface(fontThin);
            holder.checkProduct = (CheckBox) rowView.findViewById(R.id.cbProductForExecution);
            //holder.substract = (TextView) rowView.findViewById(R.id.bSubstractQuantityForExecution);
            //holder.add = (TextView) rowView.findViewById(R.id.bAddQuantityForExecution);

            //holder.substract.setTag(holder);
            //holder.add.setTag(holder);
            holder.checkProduct.setTag(holder);
            rowView.setTag(holder);

//            rowView.setLongClickable(false);
//            rowView.setClickable( false );
//            rowView.setOnClickListener( null );

//        }else{
//
//            holder = (ViewHolderExe) rowView.getTag();
//
//        }

        final Product product = products.get(position);

        if(product != null){
            holder.product.setText(product.getProduct());
            holder.productName.setText(product.getProductName());
            holder.quantity.setText("" + product.getQuantity());
            holder.unit.setText(product.getUnit());
            holder.price.setText("" + product.getPrice());
            holder.currency.setText(product.getCurrency());
            holder.checkProduct.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    //To change body of implemented methods use File | Settings | File Templates.
                    //here we make changes to the row view when checkbox is checked
                    //ViewHolderExe viewHolder = (ViewHolderExe) compoundButton.getTag();
                    View view = (View) compoundButton.getParent();
                    if(isChecked){
//                        viewHolder.add.setText("");
//                        viewHolder.substract.setText("");
                        view.setBackgroundColor(context.getResources().getColor(R.color.orangeRed));
                        checkedProducts.put(position,true);
                    }else{
                        checkedProducts.put(position,false);
                        if(position % 2 == 0){
                            view.setBackgroundColor(context.getResources().getColor(R.color.skyBlue));
                        }else{
                            view.setBackgroundColor(context.getResources().getColor(R.color.greenish));
                        }
//                        viewHolder.add.setText(" +");
//                        viewHolder.substract.setText("- ");

                    }
                }
            });


//            holder.substract.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    //To change body of implemented methods use File | Settings | File Templates.
//                    //holder.unit.setText("Kilogrami");
//                    float quantity = product.getQuantity() - 1;
//                    ViewHolderExe viewHolder = (ViewHolderExe) view.getTag();
//                    viewHolder.quantity.setText("" + quantity);
//                    product.setQuantity(quantity);
//                }
//            });
//
//            holder.add.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    //To change body of implemented methods use File | Settings | File Templates.
//                    float quantity = product.getQuantity() + 1;
//                    ViewHolderExe viewHolder = (ViewHolderExe) view.getTag();
//                    viewHolder.quantity.setText("" + quantity);
//                    product.setQuantity(quantity);
//                }
//            });
            if (checkedProducts.containsKey(position) == true && checkedProducts.get(position) == true){
                holder.checkProduct.setChecked(true);
                rowView.setBackgroundColor(context.getResources().getColor(R.color.orangeRed));
            }else{

            if(position % 2 == 0){
                rowView.setBackgroundColor(context.getResources().getColor(R.color.skyBlue));
            }else {
                rowView.setBackgroundColor(context.getResources().getColor(R.color.greenish));
            }
            }

//            if(checkedProducts.get(position) == true){
//                holder.checkProduct.setChecked(true);
//            }

        }

        return rowView;
    }
}



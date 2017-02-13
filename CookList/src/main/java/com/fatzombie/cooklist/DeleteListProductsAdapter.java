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
 * User: NTS Two
 * Date: 12/2/13
 * Time: 2:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class DeleteListProductsAdapter extends ArrayAdapter<Product> {

    private ArrayList<Product> products;
    private Context context;
    ViewHolder holder;
    public static SparseBooleanArray listOfproductsForDeletion = new SparseBooleanArray();
    //test arraylist boolean
    public static ArrayList<Boolean> productsForDeletion = new ArrayList<Boolean>();
    public static final HashMap<Integer,Boolean> checkedProducts = new HashMap<Integer, Boolean>();

    private ArrayList<Integer> deletedProducts = new ArrayList<Integer>();
    public DeleteListProductsAdapter(Context c, int id, ArrayList<Product> p ){
        super(c, id, p);
        products = p;
        context = c;

        setDefoultValuesForProductsPositions();
    }

    public static class ViewHolder{
        public TextView product;
        public TextView productName;
        public TextView quantity;
        public TextView price;
        public TextView unit;
        public TextView currency;
        public CheckBox deleteProduct;
        public TextView substract;
        public TextView add;
    }

    private void testDelitionproducts(){
        int size = products.size();
        for(int i = 0; i < size; i++){
            productsForDeletion.add(false);

        }
    }

    private void setDefoultValuesForProductsPositions(){
        int size = products.size();
        for(int i = 0; i < size; i++){
            listOfproductsForDeletion.append(i, false);

        }
    }

    public ArrayList<Integer> getProductsPositionsForDeletion(){

        return deletedProducts;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View rowView = convertView;
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "Roboto-Bold.ttf");
        Typeface fontThin = Typeface.createFromAsset(getContext().getAssets(), "Roboto-Thin.ttf");
        //if(rowView == null){

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.delete_product_row_layout, null);
            holder = new ViewHolder();

            holder.product = (TextView) rowView.findViewById(R.id.tvProductForDeletion);
            holder.product.setTypeface(font);
            holder.productName = (TextView) rowView.findViewById(R.id.tvProductNameForDeletion);
            holder.productName.setTypeface(fontThin);
            holder.price = (TextView) rowView.findViewById(R.id.tvPriceForDeletion);
            holder.price.setTypeface(fontThin);
            holder.quantity = (TextView) rowView.findViewById(R.id.tvQuantityForDeletion);
            holder.quantity.setTypeface(fontThin);
            holder.unit = (TextView) rowView.findViewById(R.id.tvUnitForDeletion);
            holder.unit.setTypeface(fontThin);
            holder.currency = (TextView) rowView.findViewById(R.id.tvCurrencyForDeletion);
            holder.currency.setTypeface(fontThin);
            holder.deleteProduct = (CheckBox) rowView.findViewById(R.id.cbProductForDeletion);
            holder.substract = (TextView) rowView.findViewById(R.id.bSubstractQuantityForDeletion);
            holder.add = (TextView) rowView.findViewById(R.id.bAddQuantityForDeletion);

            holder.substract.setTag(holder);
            holder.add.setTag(holder);
            rowView.setTag(holder);

//        }else{
//
//            holder = (ViewHolder) rowView.getTag();
//
//        }

        final Product product = products.get(position);

        if(product != null){
            holder.product.setText(product.getProduct());
            holder.productName.setText(product.getProductName());
            holder.quantity.setText("" + product.getQuantity());
            holder.unit.setText(product.getUnit());
            holder.currency.setText(product.getCurrency());
            holder.price.setText("" + product.getPrice());
            holder.deleteProduct.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    View view = (View) buttonView.getParent();
                    if(isChecked){
                        deletedProducts.add(getPosition(product));
                        checkedProducts.put(position,true);
                       view.setBackgroundColor(context.getResources().getColor(R.color.orangeRed));
                    }else{
                        checkedProducts.put(position,false);
                        int size = deletedProducts.size();
                        for(int i = 0; i < size; i++){

                            if(deletedProducts.get(i) == getPosition(product)){
                                deletedProducts.remove(i);
                            }

                        }



                        if(position % 2 == 0){
                            view.setBackgroundColor(context.getResources().getColor(R.color.skyBlue));
                        }else{
                            view.setBackgroundColor(context.getResources().getColor(R.color.greenish));
                        }
                    }
                }
            });
            holder.substract.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //To change body of implemented methods use File | Settings | File Templates.

                    float quantity = product.getQuantity() - 1;
                    ViewHolder viewHolder = (ViewHolder) view.getTag();
                    viewHolder.quantity.setText("" + quantity);
                    product.setQuantity(quantity);
                }
            });

            holder.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //To change body of implemented methods use File | Settings | File Templates.
                    float quantity = product.getQuantity() + 1;
                    ViewHolder viewHolder = (ViewHolder) view.getTag();
                    viewHolder.quantity.setText("" + quantity);
                    product.setQuantity(quantity);
                }
            });
        }

        if (checkedProducts.containsKey(position) == true && checkedProducts.get(position) == true){
            holder.deleteProduct.setChecked(true);
            rowView.setBackgroundColor(context.getResources().getColor(R.color.orangeRed));
        }else{

            if(position % 2 == 0){
                rowView.setBackgroundColor(context.getResources().getColor(R.color.skyBlue));
            }else {
                rowView.setBackgroundColor(context.getResources().getColor(R.color.greenish));
            }
        }

        return rowView;
    }
}


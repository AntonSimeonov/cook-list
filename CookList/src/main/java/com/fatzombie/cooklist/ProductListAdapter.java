package com.fatzombie.cooklist;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * Created with IntelliJ IDEA.
 * User: NTS Two
 * Date: 11/19/13
 * Time: 5:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProductListAdapter extends ArrayAdapter<Product> {

    private ArrayList<Product> products;
    private Context context;
    ViewHolder holder;

    public ProductListAdapter(Context c, int id, ArrayList<Product> p ){
        super(c, id, p);
        products = p;
        context = c;

    }

    public static class ViewHolder{
        public TextView product;
        public TextView productName;
        public TextView quantity;
        public TextView price;
        public TextView currency;
        public TextView unit;
        public TextView substract;
        public TextView add;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = convertView;
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "Roboto-Bold.ttf");
        Typeface fontThin = Typeface.createFromAsset(getContext().getAssets(), "Roboto-Thin.ttf");
        if(rowView == null){

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.product_row_layout, null);
            holder = new ViewHolder();
            holder.product = (TextView) rowView.findViewById(R.id.tvProduct);
            holder.product.setTypeface(font);
            holder.productName = (TextView) rowView.findViewById(R.id.tvProductName);
            holder.productName.setTypeface(fontThin);
            holder.price = (TextView) rowView.findViewById(R.id.tvPrice);
            holder.price.setTypeface(fontThin);
            holder.currency = (TextView) rowView.findViewById(R.id.tvCurrency);
            holder.currency.setTypeface(fontThin);
            holder.quantity = (TextView) rowView.findViewById(R.id.tvQuantity);
            holder.quantity.setTypeface(fontThin);
            holder.unit = (TextView) rowView.findViewById(R.id.tvUnit);
            holder.unit.setTypeface(fontThin);
            holder.substract = (TextView) rowView.findViewById(R.id.bSubstractQuantity);
            holder.add = (TextView) rowView.findViewById(R.id.bAddQuantity);

            holder.substract.setTag(holder);
            holder.add.setTag(holder);
            rowView.setTag(holder);

        }else{

            holder = (ViewHolder) rowView.getTag();

        }

       final Product product = products.get(position);

       if(product != null){
            holder.product.setText(product.getProduct());
            holder.productName.setText(product.getProductName());
            holder.quantity.setText("" + product.getQuantity());
            holder.unit.setText(product.getUnit());
            holder.price.setText("" + product.getPrice());
            holder.currency.setText(product.getCurrency());

            holder.substract.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //To change body of implemented methods use File | Settings | File Templates.
                    //holder.unit.setText("Kilogrami");
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

           if(position % 2 == 0){
               rowView.setBackgroundColor(context.getResources().getColor(R.color.skyBlue));
           }else {
               rowView.setBackgroundColor(context.getResources().getColor(R.color.greenish));
           }
       }

       return rowView;
    }
}

package com.fatzombie.cooklist;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by anton on 1/12/14.
 */
public class CustomSimpleGridProductAdapter extends ArrayAdapter<String> {

    private ArrayList<String> items;
    private Context context;
    ViewHolder holder;

    public CustomSimpleGridProductAdapter(Context c, int id, ArrayList<String> it ){
        super(c, id, it);
        items = it;
        context = c;

    }

    public static class ViewHolder{
        public TextView item;
//        public TextView leftBoreder;
//        public TextView rightBoreder;
        public ImageView img;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = convertView;

        if(rowView == null){
            Typeface fontThin = Typeface.createFromAsset(getContext().getAssets(), "Roboto-Black.ttf");
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.custom_simple_grid_product_adapter, null);
            holder = new ViewHolder();
            holder.item = (TextView) rowView.findViewById(R.id.tvCustomSimpleGridProductAdapterName);
            holder.item.setTypeface(fontThin);
            holder.img = (ImageView) rowView.findViewById(R.id.ivCustomSimpleGridProductAdapter);

//            holder.leftBoreder = (TextView) rowView.findViewById(R.id.tvLeftItemBoreder);
//            holder.rightBoreder = (TextView) rowView.findViewById(R.id.tvRightItemBoreder);
           // holder.img.setImageResource(R.drawable.product_arrow_silver);
            rowView.setTag(holder);

        }else{

            holder = (ViewHolder) rowView.getTag();

        }

        String itemName = items.get(position);

        if(itemName != null){
                if(!itemName.equals("")){
                holder.item.setText(itemName);
                holder.img.setImageResource(R.drawable.product_arrow_silver);

                }else{
                   // holder.item.setText(getContext().getResources().getString(R.string.add_brand_for_product));
                    holder.item.setText(context.getResources().getString(R.string.no_brands));
                    holder.img.setImageResource(R.drawable.no_brands);
//                    holder.leftBoreder.setBackgroundColor(context.getResources().getColor(R.color.white));
//                    holder.rightBoreder.setBackgroundColor(context.getResources().getColor(R.color.white));

                }

//            if(position % 2 == 0){
//                holder.leftBoreder.setBackgroundColor(context.getResources().getColor(R.color.skyBlue));
//                holder.rightBoreder.setBackgroundColor(context.getResources().getColor(R.color.skyBlue));
//               // rowView.setBackgroundColor(context.getResources().getColor(R.color.greysh));
//            }else {
//                holder.leftBoreder.setBackgroundColor(context.getResources().getColor(R.color.orangeRed));
//                holder.rightBoreder.setBackgroundColor(context.getResources().getColor(R.color.orangeRed));
//               // rowView.setBackgroundColor(context.getResources().getColor(R.color.sl_greysh));
//            }
        }



        return rowView;
    }
}


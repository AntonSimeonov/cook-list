package com.fatzombie.cooklist;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by anton on 1/12/14.
 */
public class CustomSimpleAdapter extends ArrayAdapter<String> {

    private ArrayList<String> items;
    private Context context;
    ViewHolder holder;

    public CustomSimpleAdapter(Context c, int id, ArrayList<String> it ){
        super(c, id, it);
        items = it;
        context = c;

    }

    public static class ViewHolder{
        public TextView item;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = convertView;

        if(rowView == null){
            Typeface font = Typeface.createFromAsset(getContext().getAssets(), "Roboto-Thin.ttf");
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.custom_simple_adapter, null);
            holder = new ViewHolder();
            holder.item = (TextView) rowView.findViewById(R.id.tvSimpleCustomAdapterItemName);
            holder.item.setTypeface(font);
            rowView.setTag(holder);

        }else{

            holder = (ViewHolder) rowView.getTag();

        }

        String itemName = items.get(position);

        if(itemName != null){
            holder.item.setText(itemName);
//            if(position % 2 == 0){
//                rowView.setBackgroundColor(context.getResources().getColor(R.color.skyBlue));
//            }else {
//                rowView.setBackgroundColor(context.getResources().getColor(R.color.orangeRed));
//            }
        }



        return rowView;
    }
}

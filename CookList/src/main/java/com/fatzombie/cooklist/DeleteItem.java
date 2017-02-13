package com.fatzombie.cooklist;

import android.content.Context;
import android.graphics.Typeface;
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
 * Created by anton on 1/28/14.
 */
public class DeleteItem extends ArrayAdapter<String> {

    private ArrayList<String> items;
    private Context context;
    ViewHolder holder;

    ArrayList<String> deletedItems = new ArrayList<String>();
    public static final HashMap<Integer,Boolean> checkedProductsFordeletion= new HashMap<Integer, Boolean>();
    public DeleteItem(Context c, int id, ArrayList<String> it ){
        super(c, id, it);
        items = it;
        context = c;

    }

    public static class ViewHolder{
        public TextView item;
        public CheckBox deleteItem;

    }

    public ArrayList<String> getDeletedItems(){
        return deletedItems;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View rowView = convertView;

        //if(rowView == null){
            Typeface font = Typeface.createFromAsset(getContext().getAssets(), "Roboto-Thin.ttf");
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.delete_item, null);
            holder = new ViewHolder();
            holder.deleteItem = (CheckBox) rowView.findViewById(R.id.chbDeleteItemState);
            holder.item = (TextView) rowView.findViewById(R.id.tvDeleteItemName);
            holder.item.setTypeface(font);
            rowView.setTag(holder);

//        }else{
//
//            holder = (ViewHolder) rowView.getTag();
//
//        }

        final String itemName = items.get(position);

        if(itemName != null){
            holder.item.setText(itemName);
            holder.deleteItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChrcked) {
                    View view = (View) compoundButton.getParent();
                    if(isChrcked){
                        deletedItems.add(itemName);
                        checkedProductsFordeletion.put(position, true);
                        //view.setBackgroundColor(context.getResources().getColor(R.color.orangeRed));
                    }else{
                        deletedItems.remove(itemName);
                        checkedProductsFordeletion.put(position, false);
                        //view.setBackgroundColor(context.getResources().getColor(R.color.white));
                    }
                }
            });
//            if(position % 2 == 0){
//                rowView.setBackgroundColor(context.getResources().getColor(R.color.dark_violet));
//            }else {
//                rowView.setBackgroundColor(context.getResources().getColor(R.color.light_violet));
//            }
            if(checkedProductsFordeletion.containsKey(position) == true && checkedProductsFordeletion.get(position) == true){
                holder.deleteItem.setChecked(true);
                //rowView.setBackgroundColor(context.getResources().getColor(R.color.orangeRed));
            }
        }



        return rowView;
    }
}


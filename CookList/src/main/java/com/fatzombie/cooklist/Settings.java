package com.fatzombie.cooklist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

/**
 * Created by anton on 1/28/14.
 */
public class Settings extends Activity implements View.OnClickListener{

    private TextView deleteCategory;
    private TextView deleteProduct;
    private TextView deleteProductName;
    private TextView deleteLists;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        init();
    }

    private void init() {
        deleteCategory = (TextView) findViewById(R.id.tvSettingsDeleteCategory);
        deleteCategory.setOnClickListener(this);
        deleteProduct = (TextView) findViewById(R.id.tvSettingsDeleteProduct);
        deleteProduct.setOnClickListener(this);
        deleteProductName = (TextView) findViewById(R.id.tvSettingsDeleteProductName);
        deleteProductName.setOnClickListener(this);
        deleteLists = (TextView) findViewById(R.id.tvSettingsDeleteLists);
        deleteLists.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {

        boolean isCategory = false, isProduct = false, isProduxtName = false;
        Intent intent = null;
        if (view.getId() == R.id.tvSettingsDeleteCategory){
            isCategory = true;
            intent = new Intent(this, AdminItems.class);
            intent.putExtra("IS_CATEGORY", isCategory);
            intent.putExtra("IS_PRODUCT", isProduct);
            intent.putExtra("IS_PRODUCT_NAME", isProduxtName);
        }else if (view.getId() == R.id.tvSettingsDeleteProduct){
            isProduct = true;
            intent = new Intent(this, AdminItems.class);
            intent.putExtra("IS_CATEGORY", isCategory);
            intent.putExtra("IS_PRODUCT", isProduct);
            intent.putExtra("IS_PRODUCT_NAME", isProduxtName);
        }else if(view.getId() == R.id.tvSettingsDeleteProductName){
            isProduxtName = true;
            intent = new Intent(this, AdminItems.class);
            intent.putExtra("IS_CATEGORY", isCategory);
            intent.putExtra("IS_PRODUCT", isProduct);
            intent.putExtra("IS_PRODUCT_NAME", isProduxtName);
        } else if(view.getId() == R.id.tvSettingsDeleteLists){
            intent = new Intent(this, DeleteLists.class);
           // startActivity(intent);
        }

        DeleteItem.checkedProductsFordeletion.clear();
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.miSettingsMenuNewList:
                intent = new Intent(this, NewList.class);
                startActivity(intent);
                return true;
            case R.id.miMenu:
                intent = new Intent(this, MainMenu.class);
                startActivity(intent);
                return true;
            default:
                return true;
        }
    }
}
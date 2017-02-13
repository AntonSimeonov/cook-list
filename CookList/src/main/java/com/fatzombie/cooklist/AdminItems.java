package com.fatzombie.cooklist;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdminItems extends ActionBarActivity implements AdapterView.OnItemClickListener{

    private boolean isCategory;
    private boolean isProduct;
    private boolean isProductName;

    private TextView deletionMode;
    private ListView items;
    private ArrayList<String> alItems = new ArrayList<String>();
    private ArrayAdapter<String> aaItems;



    private DBAdapter dbAdapter;

    private String categoryName;
    private String productName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deletion_mode);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initDB();
        getExtras();
        if (isCategory){
            //Intent
           // deletionMode.setText(getResources().getString(R.string.deletion_mode_category));
            Intent intent = new Intent(this, AdminDeleteItems.class);
            intent.putExtra("IS_CATEGORY", isCategory);
            intent.putExtra("IS_PRODUCT", isProduct);
            intent.putExtra("IS_PRODUCT_NAME", isProductName);
            intent.putExtra("CATEGORY_NAME", categoryName);
            intent.putExtra("PRODUCT_NAME", productName);
            startActivity(intent);
        }else if (isProduct){

            GetAllCategories getAllCategories = new GetAllCategories();
            getAllCategories.execute(null);
        }else if (isProductName){

            GetAllCategories getAllCategories = new GetAllCategories();
            getAllCategories.execute(null);
        }
    }


    private void init(){

        deletionMode = (TextView) findViewById(R.id.tvItemsModeForDeletion);
        items = (ListView) findViewById(R.id.lvItemsModeForDeletion);
        aaItems = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, alItems);
        items.setAdapter(aaItems);
        items.setOnItemClickListener(this);
    }

    private void initMemberVar() {


    }


    private void getExtras(){
        Intent intent = getIntent();
        isCategory = intent.getBooleanExtra("IS_CATEGORY", false);
        isProduct = intent.getBooleanExtra("IS_PRODUCT", false);
        isProductName = intent.getBooleanExtra("IS_PRODUCT_NAME", false);
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
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
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
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


        if (isProduct){
            categoryName = alItems.get(i);
           //Intent
            Intent intent = new Intent(this, AdminDeleteItems.class);
            intent.putExtra("IS_CATEGORY", isCategory);
            intent.putExtra("IS_PRODUCT", isProduct);
            intent.putExtra("IS_PRODUCT_NAME", isProductName);
            intent.putExtra("CATEGORY_NAME", categoryName);
            intent.putExtra("PRODUCT_NAME", productName);
            startActivity(intent);
        }else if (isProductName){

            if (categoryName == null){
            categoryName = alItems.get(i);
            GetAllProducts getAllProducts = new GetAllProducts();
            getAllProducts.execute(null);
            }else{
                productName = alItems.get(i);
                //Intent
                Intent intent = new Intent(this, AdminDeleteItems.class);
                intent.putExtra("IS_CATEGORY", isCategory);
                intent.putExtra("IS_PRODUCT", isProduct);
                intent.putExtra("IS_PRODUCT_NAME", isProductName);
                intent.putExtra("CATEGORY_NAME", categoryName);
                intent.putExtra("PRODUCT_NAME", productName);
                startActivity(intent);
            }

        }
    }

    private void initDB() {
        dbAdapter = new DBAdapter(this);
        dbAdapter.open();
    }



    /**
     * A placeholder fragment containing a simple view.
     */

    private class GetAllCategories extends AsyncTask<Void, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(Void... voids) {

            return dbAdapter.getAllCategoriesInList();
        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            super.onPostExecute(strings);
            alItems.addAll(strings);

           init();
           if(isProduct){
               deletionMode.setText(getResources().getString(R.string.deletion_mode_product));
           }else{
               deletionMode.setText(getResources().getString(R.string.deletion_mode_product_name));
           }
        }
    }

    private class GetAllProducts extends AsyncTask<Void, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(Void... voids) {

            return dbAdapter.getAllproductsForCategoryInList(categoryName);
        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            super.onPostExecute(strings);
            aaItems.clear();
            alItems.addAll(strings);
            aaItems.notifyDataSetChanged();
            //init();
        }
    }

    private class GetAllProductNames extends AsyncTask<Void, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(Void... voids) {

            return dbAdapter.getAlllistNamesInList();
        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            super.onPostExecute(strings);
            alItems.addAll(strings);

           // init();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}

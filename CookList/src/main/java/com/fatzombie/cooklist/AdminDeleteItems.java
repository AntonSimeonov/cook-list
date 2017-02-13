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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by anton on 1/30/14.
 */
public class AdminDeleteItems extends ActionBarActivity implements View.OnClickListener{

    private TextView actualItemsTypeForDeletion;
    private Button cancelButton;
    private Button okButton;
    private ListView itemsForDeletion;
    private ArrayList<String> alItemsForDeletion = new ArrayList<String>();
    private DeleteItem aaItemsForDeletion;

    private ArrayList<String> selectedItemsForDeletion;

    private DBAdapter dbAdapter;
    private boolean isCategory;
    private boolean isProduct;
    private boolean isProductName;

    private String categoryName;
    private String productName;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_lists);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initDB();
        getExtras();

        if (isCategory){

            GetAllCategories getAllCategories = new GetAllCategories();
            getAllCategories.execute(null);

        }else if (isProduct){

            GetAllProductsForCategory getAllProductsForCategory = new GetAllProductsForCategory();
            getAllProductsForCategory.execute(null);

        }else if (isProductName){

            GetAllProductNamesForProduct getAllProductNamesForProduct = new GetAllProductNamesForProduct();
            getAllProductNamesForProduct.execute(null);

        }

    }

    private void init(){
        actualItemsTypeForDeletion = (TextView) findViewById(R.id.tvActualItemsForDeleteon);
        itemsForDeletion = (ListView) findViewById(R.id.lvItemsForDeletion);
        aaItemsForDeletion = new DeleteItem(getApplicationContext(), R.layout.delete_item, alItemsForDeletion);
        itemsForDeletion.setAdapter(aaItemsForDeletion);

        okButton = (Button) findViewById(R.id.bOkDeleteItems);
        okButton.setOnClickListener(this);
        cancelButton = (Button) findViewById(R.id.bCancelDeleteItems);
        cancelButton.setOnClickListener(this);

    }

    private void initDB() {
        dbAdapter = new DBAdapter(this);
        dbAdapter.open();
    }

    private void getExtras(){
        Intent intent = getIntent();
        isCategory = intent.getBooleanExtra("IS_CATEGORY", false);
        isProduct = intent.getBooleanExtra("IS_PRODUCT", false);
        isProductName = intent.getBooleanExtra("IS_PRODUCT_NAME", false);
        categoryName = intent.getStringExtra("CATEGORY_NAME");
        productName = intent.getStringExtra("PRODUCT_NAME");
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
    public void onClick(View view) {

        if(view.getId() == R.id.bOkDeleteItems){

            if (isCategory){
                //Delete category

                DeleteSelectedCatrgories deleteSelectedCatrgories = new DeleteSelectedCatrgories();
                deleteSelectedCatrgories.execute(null);

            }else if (isProduct){
                //Delete product

                DeleteSelectedProducts deleteSelectedProducts = new DeleteSelectedProducts();
                deleteSelectedProducts.execute(null);

            }else if (isProductName){
                //Delete productName

                DeleteSelectedProductNames deleteSelectedProductNames = new DeleteSelectedProductNames();
                deleteSelectedProductNames.execute(null);

            }

        }else {
            Intent intent = new Intent(this, Settings.class);
            startActivity(intent);
        }

    }

    private class GetAllCategories extends AsyncTask<Void, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(Void... voids) {

            return dbAdapter.getAllCategoriesInList();
        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            super.onPostExecute(strings);
            alItemsForDeletion.addAll(strings);

            init();
            actualItemsTypeForDeletion.setText(getResources().getString(R.string.deletion_mode_category));
        }
    }

    private class GetAllProductsForCategory extends AsyncTask<Void, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(Void... voids) {

            return dbAdapter.getAllproductsForCategoryInList(categoryName);
        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            super.onPostExecute(strings);
            alItemsForDeletion.addAll(strings);

            init();
            actualItemsTypeForDeletion.setText(getResources().getString(R.string.deletion_mode_product) + " " + getResources().getString(R.string.from) + " " + categoryName);
        }
    }



    private class GetAllProductNamesForProduct extends AsyncTask<Void, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(Void... voids) {

            return dbAdapter.getAllProductNamesForProductInList(productName);
        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            super.onPostExecute(strings);
            alItemsForDeletion.addAll(strings);

            init();
            actualItemsTypeForDeletion.setText(getResources().getString(R.string.deletion_mode_product_name) + " " + getResources().getString(R.string.from) + " " + productName);
        }
    }



    //Classes for deletion

    private class DeleteSelectedCatrgories extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {

            ArrayList<String> products;
            ArrayList<String> categorieNames = aaItemsForDeletion.getDeletedItems();
            int numberOfListForDeletion = categorieNames.size();
            long categoryId;
            for(int i = 0; i < numberOfListForDeletion; i++){
                categoryId = dbAdapter.getCategoryIdFromCategoryName(categorieNames.get(i));
                products = dbAdapter.getAllproductsForCategoryInList(categorieNames.get(i));
                int numberOfProductsForDeletion = products.size();
                long productId;
                for(int z = 0; z < numberOfProductsForDeletion; z++){
                    productId = dbAdapter.getProductIdFromProduct(products.get(z));
                    ArrayList<String> productNamesForProduct = dbAdapter.getAllProductNamesForProductInList(products.get(z));
                    int size = productNamesForProduct.size();
                    long productNameId;
                    for (int j = 0; j < size; j++){
                        productNameId = dbAdapter.getProductNameIdFormProductName(productNamesForProduct.get(j));
                        dbAdapter.deleteListProducttWithProductNameId("" + productNameId);
                        dbAdapter.deleteProducNametWithName(productNamesForProduct.get(j));
                    }
                    dbAdapter.deleteListProducttWithProductId("" + productId);

                    dbAdapter.deleteProductWithName(products.get(z));
                }


                dbAdapter.deleteCategoryWithName(categorieNames.get(i));

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Intent intent = new Intent(getApplicationContext(), Settings.class);
            startActivity(intent);
        }

}
    private class DeleteSelectedProducts extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {

            ArrayList<String> productNamesForProduct;
            ArrayList<String> products = aaItemsForDeletion.getDeletedItems();
            int numberOfProductsForDeletion = products.size();
            long productId;
            for(int i = 0; i < numberOfProductsForDeletion; i++){
                productId = dbAdapter.getProductIdFromProduct(products.get(i));
                productNamesForProduct = dbAdapter.getAllProductNamesForProductInList(products.get(i));
                int size = productNamesForProduct.size();
                long productNameId;
                for (int j = 0; j < size; j++){
                    productNameId = dbAdapter.getProductNameIdFormProductName(productNamesForProduct.get(j));
                    dbAdapter.deleteListProducttWithProductNameId("" + productNameId);
                    dbAdapter.deleteProducNametWithName(productNamesForProduct.get(j));
                }
                dbAdapter.deleteListProducttWithProductId("" + productId);
                dbAdapter.deleteProductWithName(products.get(i));
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Intent intent = new Intent(getApplicationContext(), Settings.class);
            startActivity(intent);
        }
    }

    private class DeleteSelectedProductNames extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {

            ArrayList<String> listsProductNames = aaItemsForDeletion.getDeletedItems();
            int numberOfProductNamesForDeletion = listsProductNames.size();
            long productNameId;
            for(int i = 0; i < numberOfProductNamesForDeletion; i++){
                productNameId = dbAdapter.getProductNameIdFormProductName(listsProductNames.get(i));
                dbAdapter.deleteListProducttWithProductNameId("" + productNameId);
                dbAdapter.deleteProducNametWithName(listsProductNames.get(i));
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Intent intent = new Intent(getApplicationContext(), Settings.class);
            startActivity(intent);
        }
    }


}


package com.fatzombie.cooklist;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created with IntelliJ IDEA.
 * User: NTS Two
 * Date: 11/20/13
 * Time: 1:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class SavePeoductsList extends ActionBarActivity implements EditProductDialog.EditNewListProductInterface{

    private ListView selectedProducts;
    private ProductListAdapter productsAdapter;
    private int positionOfProductInSavedList;

    private TextView sum;
    private TextView numberOfProducts;
    private TextView listNameTextView;

    private DBAdapter dbAdapter;

    private String listName;
    private long listId;

    public static final String LIST_NAMEC = "listNamec";

    //@TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save_products_list);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();

    }


    private void getListNameExtra(){
        Intent intent = getIntent();
        listName = intent.getStringExtra("LIST_NAME");
    }

    private float calculateProductsSum(){
        int size = DAndDActivity.foodListProducts.size();
        float result = 0;
        for(int i  = 0; i < size; i ++){
            result += DAndDActivity.foodListProducts.get(i).getPrice();
        }

        return result;
    }

    private void init(){
        selectedProducts = (ListView) findViewById(R.id.lwSelectedProducts);
        Collections.sort(DAndDActivity.foodListProducts, new ProductComparator());
        productsAdapter = new ProductListAdapter(this, R.layout.product_row_layout, DAndDActivity.foodListProducts);
        selectedProducts.setAdapter(productsAdapter);

        listNameTextView = (TextView) findViewById(R.id.tvListNameAtSavedProducts);
        Typeface font = Typeface.createFromAsset(getAssets(), "Roboto-Black.ttf");
        Typeface fontThin = Typeface.createFromAsset(getAssets(), "Roboto-Thin.ttf");
        listNameTextView.setTypeface(font);
        getListNameExtra();

        if(listName == null){
            SharedPreferences settings = getSharedPreferences(DAndDActivity.STATE_PREF, 0);
            String lName = settings.getString(DAndDActivity.LIST_NAMEB, listName);
            listNameTextView.setText(lName);
            listName = lName;
        } else{
            listNameTextView.setText(listName);
        }

        numberOfProducts = (TextView) findViewById(R.id.tvNumberOfProducts);
        numberOfProducts.setTypeface(fontThin);
        numberOfProducts.setText(getResources().getString(R.string.tv_save_products_list_number_of_products) + " " +DAndDActivity.foodListProducts.size() + " " + getResources().getString(R.string.products));

        sum = (TextView) findViewById(R.id.tvSumOfSelectedProducts);
        sum.setText("");

        setClickListener();
        initDB();
    }

    private void initDB(){
        dbAdapter = new DBAdapter(this);
        dbAdapter.open();
        //dbAdapter.populateDB();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.deleteListProduct:
                DeleteListProductsAdapter.checkedProducts.clear();
                deleteProductsFromlist();
                return true;
            case R.id.saveListWithProductsInDB:
                saveListProductsInDB();
                return true;
            case R.id.miSettingsSaveList:
                intent = new Intent(this, Settings.class);
                startActivity(intent);
                return true;
            case R.id.miMenu:
                intent = new Intent(this, MainMenu.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    private void setClickListener(){
        selectedProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //To change body of implemented methods use File | Settings | File Templates.

                DialogFragment editProductDialog = new EditProductDialog();
                editProductDialog.show(getFragmentManager(), "EditProduct");
                positionOfProductInSavedList = i;
                setEditProductDefaultValues();
            }
        });
    }

    @Override
    public void onEditListProductDialogPositiveClick(DialogFragment dialog, String brand, float quantity, String quantityUnits, float price, String currency) {
        //To change body of implemented methods use File | Settings | File Templates.
        Product editedproduct = DAndDActivity.foodListProducts.get(positionOfProductInSavedList);
        editedproduct.setProductName(brand);
        editedproduct.setQuantity(quantity);
        editedproduct.setUnit(quantityUnits);
        editedproduct.setPrice(price);
        editedproduct.setCurrency(currency);
        productsAdapter.notifyDataSetChanged();

    }

    @Override
    public void onEditListProductDialogNegativeClick(DialogFragment dialog) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setEditProductDefaultValues(){
        Product editedProduct = DAndDActivity.foodListProducts.get(positionOfProductInSavedList);

        EditProductDialog.brandDialogDefalutValue = editedProduct.getProductName();
        EditProductDialog.quatityDialogDefaultValue = editedProduct.getQuantity();
        EditProductDialog.quantityUnitDialogDefaultValue = editedProduct.getUnit();
        EditProductDialog.priceDialogDefaultValue = editedProduct.getPrice();

    }

    private void deleteProductsFromlist(){
        //selectedProducts.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        Intent intent = new Intent(this, DeleteListProducts.class);
        startActivity(intent);
    }

    //Creates and writes products to
    private void saveListProductsInDB(){
        //DAndDActivity.dbAdapter.open();
        //replase old list with new one, with the same name
        editList();
        listId = dbAdapter.insertNewList(listName);

        SaveListProductsToDB saveProductsInDB = new SaveListProductsToDB();
        saveProductsInDB.execute(null);

    }

    private class SaveListProductsToDB extends AsyncTask<ArrayList<Product>, Void, Boolean>{

        @Override
        protected Boolean doInBackground(ArrayList<Product>... arrayLists) {
            int size = DAndDActivity.foodListProducts.size();
            String productName;
            String product;
            long productnameId;
            long productId;
            for(int i = 0; i < size; i++){
            productName = DAndDActivity.foodListProducts.get(i).getProductName();

            if(productName != null){

                productnameId = dbAdapter.getProductNameIdFormProductName(productName);

                if(productnameId == -2){
                    product = DAndDActivity.foodListProducts.get(i).getProduct();
                    productId = dbAdapter.getProductIdFromProduct(product);
                    productnameId = dbAdapter.insertNewProductName(productName, productId);
                }

                dbAdapter.insertNewListProduct(listId, productnameId, -1, DAndDActivity.foodListProducts.get(i).getPrice(),DAndDActivity.foodListProducts.get(i).getQuantity(), DAndDActivity.foodListProducts.get(i).getUnit(), null, DAndDActivity.foodListProducts.get(i).getCurrency());

            } else{

                product = DAndDActivity.foodListProducts.get(i).getProduct();
                productId = dbAdapter.getProductIdFromProduct(product);
                dbAdapter.insertNewListProduct(listId, -1, productId, DAndDActivity.foodListProducts.get(i).getPrice(),DAndDActivity.foodListProducts.get(i).getQuantity(), DAndDActivity.foodListProducts.get(i).getUnit(), null, DAndDActivity.foodListProducts.get(i).getCurrency());

            }

            }
            //DAndDActivity.dbAdapter.close();
            return true;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);    //To change body of overridden methods use File | Settings | File Templates.


            DAndDActivity.foodListProducts.clear();
           // productsAdapter.clear();
            //productsAdapter.notifyDataSetChanged();

            Intent intent = new Intent(SavePeoductsList.this, RedyListproducts.class);
            intent.putExtra("LIST_NAME",listName);
            startActivity(intent);
        }
    }

    //actuly we dont edit the list, we delete it.
    private void editList(){

        ArrayList<String> listNames = dbAdapter.getAlllistNamesInList();

        if(listNames != null){

        if(listNames.contains(listName)){

            dbAdapter.deleteListWithName(listName);

        }

        }

    }

    @Override
    protected void onStop() {
        super.onStop();    //To change body of overridden methods use File | Settings | File Templates.
        ///dbAdapter.close();


        finish();
    }

    @Override
    protected void onRestart() {
        super.onRestart();    //To change body of overridden methods use File | Settings | File Templates.
        //dbAdapter.open();
    }

    @Override
    protected void onResume() {
        super.onResume();    //To change body of overridden methods use File | Settings | File Templates.
//        if(listName == null){
//            SharedPreferences settings = getSharedPreferences(DAndDActivity.STATE_PREF, 0);
//            String lName = settings.getString(LIST_NAMEC, listName);
//            listNameTextView.setText(lName);
//            listName = lName;
//        }
        productsAdapter.notifyDataSetChanged();
        //dbAdapter.open();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();    //To change body of overridden methods use File | Settings | File Templat
        dbAdapter.close();
    }
}
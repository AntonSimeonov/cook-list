package com.fatzombie.cooklist;

import android.annotation.TargetApi;
import android.app.Activity;
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
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created with IntelliJ IDEA.
 * User: NTS Two
 * Date: 12/9/13
 * Time: 11:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class RedyListproducts extends ActionBarActivity {

    private TextView redyListProductsTextView;
    private ListView redyListProductsListView;

    private ArrayList<Product> alListProducts = new ArrayList<Product>();
    private ProductListAdapter aaListProducts;

    private DBAdapter dbAdapter;

    private String listName;

    private TextView numberOfProducts;

    public static final String REDY_SATE_PREF = "redy_state_pref";
    public static final String LIST_NAMED = "listNameD";

    //@TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.redy_list_products);
       //getActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
    }

    private void init(){

        initDB();
        getListName();
        getListProducts();
        Typeface font = Typeface.createFromAsset(getAssets(), "Roboto-Black.ttf");
        redyListProductsTextView = (TextView) findViewById(R.id.tvRedyListProduct);
        redyListProductsTextView.setTypeface(font);
        redyListProductsTextView.setText(listName);
        redyListProductsListView = (ListView) findViewById(R.id.lvRedyListProduct);
        numberOfProducts = (TextView) findViewById(R.id.tvRedyListProductsNumberOfProducts);
        Typeface fontThin = Typeface.createFromAsset(getAssets(), "Roboto-Thin.ttf");
        numberOfProducts.setTypeface(fontThin);
        Collections.sort(alListProducts, new ProductComparator());
        aaListProducts = new ProductListAdapter(this,  R.layout.product_row_layout, alListProducts);
        redyListProductsListView.setAdapter(aaListProducts);

    }

    private void initDB(){
        dbAdapter = new DBAdapter(this);
        dbAdapter.open();
    }
    // I HAVE TO GET LIST NAME SOME HOW
    private void getListName(){
        Intent intent = getIntent();
        listName = intent.getStringExtra("LIST_NAME");
    }

    private void getListProducts(){
        GetAllListProducts allProducts = new GetAllListProducts();
        allProducts.execute(listName);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.redy_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.miRedyListNewList:
                intent = new Intent(this, NewList.class);
                startActivity(intent);
                return true;
            case R.id.mSendEmailListProduct:
                DAndDActivity.foodListProducts.addAll(alListProducts);
                intent = new Intent(this, SendListAsEmail.class);
                intent.putExtra("LIST_NAME", listName);
                startActivity(intent);
                return true;
            case R.id.mExecListProducts:
                DAndDActivity.foodListProducts.clear();
                DAndDActivity.foodListProducts.addAll(alListProducts);
                ExecuteListProductsAdapter.checkedProducts.clear();
                intent = new Intent(this, ExecuteList.class);
                intent.putExtra("LIST_NAME", listName);
                startActivity(intent);

                return true;
            case R.id.mEditListProducts:
                DAndDActivity.foodListProducts.addAll(alListProducts);
                intent = new Intent(this, DAndDActivity.class);
                intent.putExtra("ENTERT_LIST_NAME", listName);
                startActivity(intent);
                return true;
            case R.id.miMenu:
                intent = new Intent(this, MainMenu.class);
                startActivity(intent);
                return true;
            case R.id.miSettingsRedyList:
                intent = new Intent(this, Settings.class);
                startActivity(intent);
                return true;
            default:
                return true;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();    //To change body of overridden methods use File | Settings | File Templates.
       // dbAdapter.open();
    }

    @Override
    protected void onStop() {
        super.onStop();    //To change body of overridden methods use File | Settings | File Templates.
        dbAdapter.close();
        SharedPreferences settings = getSharedPreferences(DAndDActivity.STATE_PREF, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(LIST_NAMED, redyListProductsTextView.getText().toString());
        editor.commit();

    }

    @Override
    protected void onResume() {
        super.onResume();    //To change body of overridden methods use File | Settings | File Templates.
        //clears any previus products
        DAndDActivity.foodListProducts.clear();
        Intent intent = getIntent();
      //  listName = intent.getStringExtra("LIST_NAME");

        if(intent.getStringExtra("LIST_NAME") == null){
//            SharedPreferences settings = getSharedPreferences(DAndDActivity.STATE_PREF, 0);
//            String lName = settings.getString(LIST_NAMED, listName);
//            redyListProductsTextView.setText(lName);
//            listName = lName;
//            //initDB();
//            dbAdapter.open();
//           // getListName();
//            getListProducts();
//
//            //redyListProductsTextView = (TextView) findViewById(R.id.tvRedyListProduct);
//            redyListProductsTextView.setText(listName);
//            //redyListProductsListView = (ListView) findViewById(R.id.lvRedyListProduct);
//            //Collections.sort(alListProducts, new ProductComparator());
//            //aaListProducts = new ProductListAdapter(this,  R.layout.product_row_layout, alListProducts);
//            redyListProductsListView.setAdapter(aaListProducts);
//            aaListProducts.notifyDataSetChanged();

        }

    }







    private class GetAllListProducts extends AsyncTask<String, Void, ArrayList<Product>>{

        @Override
        protected ArrayList<Product> doInBackground(String... strings) {
            String listName = strings[0];
            ArrayList<Product> listProducts = new ArrayList<Product>();


            listProducts.addAll(dbAdapter.getAllListProductsForListWithProductName(listName));
            listProducts.addAll(dbAdapter.getAllListProductsForListWithProduct(listName));

            return listProducts;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        protected void onPostExecute(ArrayList<Product> products) {
            super.onPostExecute(products);
                //To change body of overridden methods use File | Settings | File Templates.
            if(alListProducts.isEmpty()){

                alListProducts.addAll(products);
                numberOfProducts.setText(getResources().getString(R.string.list_have) + " " + alListProducts.size() + " " + getResources().getString(R.string.products));

            }

            aaListProducts.notifyDataSetChanged();
        }
    }
}
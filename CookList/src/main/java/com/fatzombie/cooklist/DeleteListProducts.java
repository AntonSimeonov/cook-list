package com.fatzombie.cooklist;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: NTS Two
 * Date: 12/2/13
 * Time: 1:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class DeleteListProducts extends ActionBarActivity implements View.OnClickListener{

    private ListView listProductsForDeletion;
    private DeleteListProductsAdapter aaListProductsForDeletion;
    private TextView statement;
    private Button okDeleteProducts;
    private Button cancelDeleteProducts;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_list_products);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
    }

    private void init(){
        listProductsForDeletion = (ListView) findViewById(R.id.lvListProductsForDeletion);
        aaListProductsForDeletion = new DeleteListProductsAdapter(this, R.layout.delete_product_row_layout, DAndDActivity.foodListProducts);
        listProductsForDeletion.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listProductsForDeletion.setAdapter(aaListProductsForDeletion);
        statement = (TextView) findViewById(R.id.tvDeleteListProductsStatement);
        Typeface font = Typeface.createFromAsset(getAssets(), "Roboto-Bold.ttf");
        statement.setTypeface(font);
        okDeleteProducts = (Button) findViewById(R.id.bOkDeleteProducts);
        cancelDeleteProducts = (Button) findViewById(R.id.bCancelDeleteProducts);

        okDeleteProducts.setOnClickListener(this);
        cancelDeleteProducts.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //To change body of implemented methods use File | Settings | File Templates.

        if(v.getId() == R.id.bOkDeleteProducts){
        ArrayList<Integer> productsForDelition = aaListProductsForDeletion.getProductsPositionsForDeletion();
        int product;
       // int size = productsForDelition.size();
//        for(int i = productsForDelition.size() - 1; i >= 0; i--){
//              product = productsForDelition.get(i);
//              DAndDActivity.foodListProducts.remove(product);
//        }
            for(int i = 0; i < productsForDelition.size(); i++){
                product = productsForDelition.get(i);
                DAndDActivity.foodListProducts.remove(product);
            }

        }else if(v.getId() == R.id.bCancelDeleteProducts){

        }
        Intent intent = new Intent(this, SavePeoductsList.class);
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.miMainMenu:
                intent = new Intent(this, Settings.class);
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
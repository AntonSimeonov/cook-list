package com.fatzombie.cooklist;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Typeface;
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

import java.util.Collection;
import java.util.Collections;

/**
 * Created with IntelliJ IDEA.
 * User: NTS Two
 * Date: 12/20/13
 * Time: 9:46 AM
 * To change this template use File | Settings | File Templates.
 */
public class ExecuteList extends ActionBarActivity implements AdapterView.OnItemClickListener, ExecuteListDialog.ExecuteListDialogListener{

    private TextView executeListName;
    private ListView executeListProducts;
    private TextView listSum;
    private TextView numberOfProducts;
    private float allListProductsSum;

    private ExecuteListProductsAdapter aaExecuteListProducts;
    //private ProductListAdapter aaExecuteListProducts;
    private int selectedProductPosition;

    private String listName;

   // @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.execute_list);
       // getActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
    }

    private void init(){
        getListName();
        Typeface font = Typeface.createFromAsset(getAssets(), "Roboto-Black.ttf");
        Typeface fontThin = Typeface.createFromAsset(getAssets(), "Roboto-Thin.ttf");
        executeListName = (TextView) findViewById(R.id.tvExecuteListName);
        executeListName.setTypeface(font);
        executeListName.setText(listName);
        executeListProducts = (ListView) findViewById(R.id.lvExecuteListView);
        Collections.sort(DAndDActivity.foodListProducts, new ProductComparator());
        listSum = (TextView) findViewById(R.id.tvExecuteListSum);
        listSum.setTypeface(fontThin);
        listSum.setText(getResources().getString(R.string.tv_execute_list_sum) + " 0" + " " + getResources().getString(R.string.currency));
        numberOfProducts = (TextView) findViewById(R.id.tvExecuteListNumberOfProducts);
        numberOfProducts.setTypeface(fontThin);
        numberOfProducts.setText(getResources().getString(R.string.tv_execute_list_number_of_products) + " " + DAndDActivity.foodListProducts.size() + " " + getResources().getString(R.string.products));
        aaExecuteListProducts = new ExecuteListProductsAdapter(this, R.layout.execute_product_row_layout,DAndDActivity.foodListProducts);
        //aaExecuteListProducts = new ProductListAdapter(this, R.layout.execute_product_row_layout,DAndDActivity.foodListProducts);
        executeListProducts.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        //executeListProducts.setSelector(R.color.orangeRed);
        executeListProducts.setAdapter(aaExecuteListProducts);
        executeListProducts.setOnItemClickListener(this);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //To change body of implemented methods use File | Settings | File Templates.
        selectedProductPosition = i;
        DialogFragment executeDialog = new ExecuteListDialog();
        executeDialog.show(getFragmentManager(), "ExecuteListDialog");
    }

    @Override
    public void onExecuteListDialogPositiveClick(float productPrice) {
        //To change body of implemented methods use File | Settings | File Templates.
        Product product =  DAndDActivity.foodListProducts.get(selectedProductPosition);
       // DAndDActivity.foodListProducts.get(selectedProductPosition).setPrice(productPrice);
        product.setPrice(productPrice);
        allListProductsSum += product.getProductSum();
        listSum.setText(getResources().getString(R.string.tv_execute_list_sum) + " " +allListProductsSum + " " + getResources().getString(R.string.currency));
        aaExecuteListProducts.notifyDataSetChanged();
    }

    @Override
    public void onExecuteListDialogNegativeClick() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    private void getListName(){
        Intent intent = getIntent();
        listName = intent.getStringExtra("LIST_NAME");
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

    @Override
    public void onBackPressed() {
       super.onBackPressed();
//        Intent intent = new Intent(this, RedyListproducts.class);
//        intent.putExtra("LIST_NAME", listName);
//        startActivity(intent);
//        finish();
//        return;
            //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    protected void onStop() {
        super.onStop();
        //ExecuteListProductsAdapter.checkedProducts.clear();
    }
}
package com.fatzombie.cooklist;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: NTS Two
 * Date: 11/20/13
 * Time: 3:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestDB extends Activity implements View.OnClickListener{

    private TextView result;

    private DBAdapter dbAdapter;

    private ListView listProducts;
    private ArrayList<Product> allistProducts;
    private ProductListAdapter aaListProducts;
    private Button button;
    private Button button2;
    private EditText enterField;
    public static final String TEXT = "text";
    public static final String PREFS_NAME = "MyPrefsFile";
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_db);
        init();
    }

    private void init(){
        result = (TextView) findViewById(R.id.tvResult);
        enterField = (EditText) findViewById(R.id.etTest);
        result.setText(enterField.getText().toString());
        button = (Button) findViewById(R.id.bTestButton);
        button2 = (Button) findViewById(R.id.bTestButton2);
        button.setOnClickListener(this);
        button2.setOnClickListener(this);
       // initDB();
        //setResult();

//        allistProducts = dbAdapter.getAllListProductsForListWithProductName("v");
//        listProducts = (ListView) findViewById(R.id.lvListProductsTest);
//        aaListProducts = new ProductListAdapter(this, R.layout.product_row_layout, allistProducts);
//        listProducts.setAdapter(aaListProducts);

    }

    private void initDB(){
        dbAdapter = new DBAdapter(this);
        dbAdapter.open();
        //dbAdapter.populateDB();
    }

    private void setResult(){
        //dbAdapter.insertNewListProduct(0,0,0,5,4,"kg","hello", "lv");
        result.setText("" + dbAdapter.getAlllistNames());
    }

    @Override
    public void onClick(View view) {
        //To change body of implemented methods use File | Settings | File Templates.
        if (view.getId() == R.id.bTestButton){
            Intent intent = new Intent(this, Test2.class);
            startActivity(intent);
        }else {
            result.setText(enterField.getText().toString());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();    //To change body of overridden methods use File | Settings | File Templates.
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String reCreate = settings.getString(TEXT, null);
        result.setText(reCreate);

    }

    @Override
    protected void onPause() {
        super.onPause();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    protected void onStop() {
        super.onStop();    //To change body of overridden methods use File | Settings | File Templates.
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(TEXT, result.getText().toString());
        editor.commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);    //To change body of overridden methods use File | Settings | File Templates.
        outState.putString(TEXT, result.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        String restoredText = savedInstanceState.getString(TEXT);
        result.setText(restoredText);
    }
}
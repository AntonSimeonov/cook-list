package com.fatzombie.cooklist;

import android.annotation.TargetApi;
import android.app.Activity;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created with IntelliJ IDEA.
 * User: NTS Two
 * Date: 11/19/13
 * Time: 11:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class NewList extends ActionBarActivity implements View.OnClickListener{

    private EditText newListName;
    private TextView newListNameTextView;
    private Button okButton;
    private Button cancelButton;

    //@TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_list);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
    }

    private void init(){
        newListName = (EditText) findViewById(R.id.etNewListName);
        newListNameTextView = (TextView) findViewById(R.id.tvNewListName);
//        Typeface font = Typeface.createFromAsset(getAssets(), "Roboto-Black.ttf");
//        newListNameTextView.setTypeface(font);
        okButton = (Button) findViewById(R.id.bSendNewListName);
        okButton.setOnClickListener(this);
        cancelButton = (Button) findViewById(R.id.bCancelNewListName);
        cancelButton.setOnClickListener(this);
        DAndDActivity.foodListProducts.clear();
    }

    @Override
    public void onClick(View v) {
        //To change body of implemented methods use File | Settings | File Templates.
        if(v.getId() == R.id.bSendNewListName){
        if(newListName.getText().toString() != null){
        Intent intent = new Intent(this, DAndDActivity.class);
        intent.putExtra("ENTERT_LIST_NAME", newListName.getText().toString());
        startActivity(intent);
        }
        }else if(v.getId() == R.id.bCancelNewListName){
            Intent intent = new Intent(this, MainMenu.class);
            startActivity(intent);
        }

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
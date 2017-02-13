package com.fatzombie.cooklist;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created with IntelliJ IDEA.
 * User: NTS Two
 * Date: 12/9/13
 * Time: 1:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class MainMenu extends Activity implements View.OnClickListener{

    private TextView mainMenuNewList;
    private TextView mainMenuSavedLists;


    //@TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
       // getActionBar().setDisplayHomeAsUpEnabled(true);

        init();
    }

    private void init(){

        mainMenuNewList = (TextView) findViewById(R.id.tvMainMenuNewList);
        mainMenuSavedLists = (TextView) findViewById(R.id.tvMainMenuSavedlists);
        //Typeface font = Typeface.createFromAsset(getAssets(), "Roboto-Black.ttf");
//        mainMenuNewList.setTypeface(font);
//        mainMenuSavedLists.setTypeface(font);
        mainMenuNewList.setOnClickListener(this);
        mainMenuSavedLists.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        Intent intent;
        if(v.getId() == R.id.tvMainMenuNewList){

            intent = new Intent(this, NewList.class);

        }else if(v.getId() == R.id.tvMainMenuSavedlists){

            intent = new Intent(this, SavedListsTestActivity.class);

        } else{
            intent = new Intent(this, NewList.class);
        }

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
            case R.id.miMainMenu:
                intent = new Intent(this, Settings.class);
                startActivity(intent);
                return true;
            default:
                return true;
        }
    }

}

package com.fatzombie.cooklist;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: NTS Two
 * Date: 12/9/13
 * Time: 3:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class SavedLists extends Activity implements AdapterView.OnItemClickListener{

    private ListView savedLists;
    private ArrayList<String> alSavedLists;
    private ArrayAdapter<String> aaSavedLists;

    private TextView numberOfLists;

    private DBAdapter dbAdapter;

   // @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saved_lists);
       // getActionBar().setDisplayHomeAsUpEnabled(true);

        init();
    }

    private void init(){
        initDB();
        getAllListFromDB();

        savedLists = (ListView) findViewById(R.id.lvSavedListsInDB);
        savedLists.setOnItemClickListener(this);
        aaSavedLists = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, alSavedLists);
        numberOfLists = (TextView) findViewById(R.id.tvSavedListsNumberOfLists2);
        numberOfLists.setText("You" + alSavedLists.size());
        //savedLists.addHeaderView(numberOfLists);
        savedLists.setAdapter(aaSavedLists);
    }

    private void initDB(){
        dbAdapter = new DBAdapter(this);
        dbAdapter.open();
    }
    //must make asynk query
    private void getAllListFromDB(){
        alSavedLists = dbAdapter.getAlllistNamesInList();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //To change body of implemented methods use File | Settings | File Templates.

       Intent intent = new Intent(this, RedyListproducts.class);
       intent.putExtra("LIST_NAME",alSavedLists.get(position));
       startActivity(intent);

    }

    @Override
    protected void onResume() {
        super.onResume();    //To change body of overridden methods use File | Settings | File Templates.
        dbAdapter.open();
    }

    @Override
    protected void onStop() {
        dbAdapter.close();
    }
}
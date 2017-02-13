package com.fatzombie.cooklist;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


/**
 * Created with IntelliJ IDEA.
 * User: anton
 * Date: 12/14/13
 * Time: 7:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class SavedListsTestActivity extends ActionBarActivity implements AdapterView.OnItemClickListener{

    private ListView savedLists;
    private ArrayList<String> alSavedLists = new ArrayList<String>();
    //private ArrayAdapter<String> aaSavedLists;
    private SavedListsAdapter aaSavedLists;

    private TextView numberOfLists;
    private TextView savedListsStatement;

    private DBAdapter dbAdapter;

   // @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saved_lists);
//       ActionBar a = getSupportActionBar();
//        a.setDisplayHomeAsUpEnabled(true);
        //getActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
    }



    private void init(){
        initDBTest();
        getAllListFromDB();

        savedLists = (ListView) findViewById(R.id.lvSavedListsInDB);
//
        //aaSavedLists = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, alSavedLists);
        savedListsStatement = (TextView) findViewById(R.id.tvSavedListsStatement);
        Typeface font = Typeface.createFromAsset(getAssets(), "Roboto-Bold.ttf");
        savedListsStatement.setTypeface(font);
        numberOfLists = (TextView) findViewById(R.id.tvSavedListsNumberOfLists2);
        Typeface fontThin = Typeface.createFromAsset(getAssets(), "Roboto-Thin.ttf");
        numberOfLists.setTypeface(fontThin);
        Collections.sort(alSavedLists);
        aaSavedLists = new SavedListsAdapter(this, R.layout.saved_lists_adapter, alSavedLists);
        savedLists.setAdapter(aaSavedLists);
        savedLists.setOnItemClickListener(this);

       // numberOfLists.setText("You have  " + alSavedLists.size() + " lists");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.saved_lists, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
//                intent = new Intent(this, MainMenu.class);
//                startActivity(intent);
                //Toast.makeText(getApplicationContext(), "home", Toast.LENGTH_LONG).show();
                return true;
            case R.id.miNewList:
                intent = new Intent(this, NewList.class);
                startActivity(intent);
                return true;
            case R.id.miDeleteLists:
                intent = new Intent(this, DeleteLists.class);
                startActivity(intent);
                return true;
            case R.id.miSettings:
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

    private void initDBTest(){
        dbAdapter = new DBAdapter(this);
        dbAdapter.open();
    }

    private void getAllListFromDB(){
        //alSavedLists = dbAdapter.getAlllistNamesInList();
        GetAllLists allProducts = new GetAllLists();
        allProducts.execute();
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
        //dbAdapter.open();
    }

    @Override
    protected void onStop() {
        super.onStop();
        dbAdapter.close();
    }

    @Override
    protected void onRestart() {
        super.onRestart();    //To change body of overridden methods use File | Settings | File Templates.
        dbAdapter.open();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();    //To change body of overridden methods use File | Settings | File Templates.
       // dbAdapter.close();
    }

    private class GetAllLists extends AsyncTask<Void, Void, ArrayList<String>>{

        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
            //ArrayList<String> allLists = new ArrayList<String>();

            //allLists = dbAdapter.getAlllistNamesInList();
            return dbAdapter.getAlllistNamesInList();  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            super.onPostExecute(strings);    //To change body of overridden methods use File | Settings | File Templates.
            if(alSavedLists.isEmpty() && strings != null){
                alSavedLists.addAll(strings);
                numberOfLists.setText(getResources().getString(R.string.you_have) + " " + alSavedLists.size()+ " " + getResources().getString(R.string.lists));
            }

        }
    }


}

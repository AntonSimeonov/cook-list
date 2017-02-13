package com.fatzombie.cooklist;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by anton on 1/28/14.
 */
public class DeleteLists extends Activity implements View.OnClickListener{

    private TextView title;
    private Button cancelButton;
    private Button okButton;
    private ListView lists;
    private ArrayList<String> alLists = new ArrayList<String>();
    private DeleteItem aaLists;

    private ArrayList<String> listForDeletion;

    private DBAdapter dbAdapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_lists);
        initDB();
        GetAllListNames allListNames = new GetAllListNames();
        allListNames.execute(null);

    }

    private void init(){
        title = (TextView) findViewById(R.id.tvActualItemsForDeleteon);
        title.setText(getResources().getString(R.string.delete_lists));
        lists = (ListView) findViewById(R.id.lvItemsForDeletion);
        aaLists = new DeleteItem(getApplicationContext(), R.layout.delete_item, alLists);
        lists.setAdapter(aaLists);

        okButton = (Button) findViewById(R.id.bOkDeleteItems);
        okButton.setOnClickListener(this);
        cancelButton = (Button) findViewById(R.id.bCancelDeleteItems);
        cancelButton.setOnClickListener(this);

    }

    private void initDB() {
        dbAdapter = new DBAdapter(this);
        dbAdapter.open();
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.bOkDeleteItems){
            DeleteSelectedLists deleteSelectedLists = new DeleteSelectedLists();
            deleteSelectedLists.execute(null);
        }else {
            Intent intent = new Intent(this, SavedListsTestActivity.class);
            startActivity(intent);
        }

    }


    private class GetAllListNames extends AsyncTask<Void, Void, ArrayList<String>>{

        @Override
        protected ArrayList<String> doInBackground(Void... voids) {

            return dbAdapter.getAlllistNamesInList();
        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            super.onPostExecute(strings);
            alLists.addAll(strings);

            init();
        }
    }

    private class DeleteSelectedLists extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {

            ArrayList<String> listsNames = aaLists.getDeletedItems();
            int numberOfListForDeletion = listsNames.size();
            long listId;
            for(int i = 0; i < numberOfListForDeletion; i++){
                listId = dbAdapter.getListIdFromListName(listsNames.get(i));
                dbAdapter.deleteListProducttWithId("" + listId);
                dbAdapter.deleteListtWithName(listsNames.get(i));
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
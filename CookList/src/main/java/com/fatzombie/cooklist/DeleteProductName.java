package com.fatzombie.cooklist;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by anton on 1/29/14.
 */
public class DeleteProductName extends Activity implements View.OnClickListener{


    private Button cancelButton;
    private Button okButton;
    private ListView productNames;
    private ArrayList<String> alProductNames = new ArrayList<String>();
    private DeleteItem aaProductNames;

    private ArrayList<String> listForDeletion;

    private DBAdapter dbAdapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_lists);
        initDB();
        GetAllProductNames allProductNames = new GetAllProductNames();
        allProductNames.execute(null);

    }

    private void init(){

        productNames = (ListView) findViewById(R.id.lvItemsForDeletion);
        aaProductNames = new DeleteItem(getApplicationContext(), R.layout.delete_item, alProductNames);
        productNames.setAdapter(aaProductNames);

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
            DeleteSelectedProductNames deleteSelectedProductNames = new DeleteSelectedProductNames();
            deleteSelectedProductNames.execute(null);
        }else {

        }

    }


    private class GetAllProductNames extends AsyncTask<Void, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(Void... voids) {

            return dbAdapter.getAlllistNamesInList();
        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            super.onPostExecute(strings);
            alProductNames.addAll(strings);

            init();
        }
    }

    private class DeleteSelectedProductNames extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {

            ArrayList<String> listsNames = aaProductNames.getDeletedItems();
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

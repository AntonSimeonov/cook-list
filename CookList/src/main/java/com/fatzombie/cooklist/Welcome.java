package com.fatzombie.cooklist;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

/**
 * Created by anton on 1/6/14.
 */
public class Welcome extends Activity {

    private DBAdapter dbAdapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcom);
        init();
        splashScreenDuration();

    }

    private void init(){
        initDB();
    }

    private void initDB(){
        dbAdapter = new DBAdapter(this);
        dbAdapter.open();
        // dbAdapter.populateDB();
        // populateDbOnStart();
    }

    private void splashScreenDuration(){

        ShowWelcomeScreenInDuration show = new ShowWelcomeScreenInDuration();
        show.execute(null);

    }



    private class ShowWelcomeScreenInDuration extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {

            if(dbAdapter.getAllCategoriesInList() == null){
                //Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.populate_db), Toast.LENGTH_LONG).show();
                populateDbOnStart();
            }

            try{
                Thread.sleep(2000);
                Intent intent = new Intent(getApplicationContext(), MainMenu.class);
                startActivity(intent);
                }catch (Exception ex){

                }

            return null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    private void populateDbOnStart(){
        String[] categories = getResources().getStringArray(R.array.categories);
        populateCategoryOnStart(categories[0], getResources().getStringArray(R.array.p_vegetables));
        populateCategoryOnStart(categories[1], getResources().getStringArray(R.array.p_fruits));
        populateCategoryOnStart(categories[2], getResources().getStringArray(R.array.p_meat));
        populateCategoryOnStart(categories[3], getResources().getStringArray(R.array.p_cheese));
        populateCategoryOnStart(categories[4], getResources().getStringArray(R.array.p_milk));
        populateCategoryOnStart(categories[5], getResources().getStringArray(R.array.p_eggs));
        populateCategoryOnStart(categories[6], getResources().getStringArray(R.array.p_salads));
        populateCategoryOnStart(categories[7], getResources().getStringArray(R.array.p_baked));
        populateCategoryOnStart(categories[8], getResources().getStringArray(R.array.p_sea_food));
        // populateCategoryOnStart(categories[9], getResources().getStringArray(R.array.p_world_cuisine));
        //  populateCategoryOnStart(categories[10], getResources().getStringArray(R.array.p_fast_food));
        populateCategoryOnStart(categories[9], getResources().getStringArray(R.array.p_junk_food));
        populateCategoryOnStart(categories[10], getResources().getStringArray(R.array.p_sauces));
        populateCategoryOnStart(categories[11], getResources().getStringArray(R.array.p_spices));
        populateCategoryOnStart(categories[12], getResources().getStringArray(R.array.p_candies));
        populateCategoryOnStart(categories[13], getResources().getStringArray(R.array.p_bevereges));
        populateCategoryOnStart(categories[14], getResources().getStringArray(R.array.p_alcohol));

        populateProductOnStart(getResources().getStringArray(R.array.v_beans));
        populateProductOnStart(getResources().getStringArray(R.array.v_carrots));
        populateProductOnStart(getResources().getStringArray(R.array.v_cucumbers));
        populateProductOnStart(getResources().getStringArray(R.array.v_garlic));
        populateProductOnStart(getResources().getStringArray(R.array.v_onion));
        populateProductOnStart(getResources().getStringArray(R.array.v_peppers));
        populateProductOnStart(getResources().getStringArray(R.array.v_potatoes));
        populateProductOnStart(getResources().getStringArray(R.array.v_tomatoes));

        populateProductOnStart(getResources().getStringArray(R.array.f_apples));
        populateProductOnStart(getResources().getStringArray(R.array.f_bananas));
        populateProductOnStart(getResources().getStringArray(R.array.f_lemon));
        populateProductOnStart(getResources().getStringArray(R.array.f_mandarin));
        populateProductOnStart(getResources().getStringArray(R.array.f_oranges));
        populateProductOnStart(getResources().getStringArray(R.array.f_peaches));

        populateProductOnStart(getResources().getStringArray(R.array.m_chicken));
        populateProductOnStart(getResources().getStringArray(R.array.m_duck));
        populateProductOnStart(getResources().getStringArray(R.array.m_goose));
        populateProductOnStart(getResources().getStringArray(R.array.m_lamb));
        populateProductOnStart(getResources().getStringArray(R.array.m_turkey));
        populateProductOnStart(getResources().getStringArray(R.array.m_pig));
        populateProductOnStart(getResources().getStringArray(R.array.m_veal));

        populateProductOnStart(getResources().getStringArray(R.array.b_bread));

        populateProductOnStart(getResources().getStringArray(R.array.bev_juice));
        populateProductOnStart(getResources().getStringArray(R.array.bev_tea));
    }

    private void populateCategoryOnStart(String category, String[] caetgoryProducts){
        long categoryId = dbAdapter.insertNewCategory(category);
        int size = caetgoryProducts.length;
        for (int i = 0; i < size; i++){
            dbAdapter.insertNewProduct(caetgoryProducts[i], categoryId);
        }

    }

    private void populateProductOnStart(String[] brands){
        int i = 0;
        long productId = dbAdapter.getProductIdFromProduct(brands[i]);
        if(productId != -1){
            int size = brands.length;
            i++;
            for (; i < size; i++){
                dbAdapter.insertNewProductName(brands[i],productId);
            }
        }


    }
}
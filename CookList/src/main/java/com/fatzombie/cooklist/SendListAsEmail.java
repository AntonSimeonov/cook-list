package com.fatzombie.cooklist;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
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

/**
 * Created with IntelliJ IDEA.
 * User: anton
 * Date: 12/24/13
 * Time: 11:34 AM
 * To change this template use File | Settings | File Templates.
 */
public class SendListAsEmail extends ActionBarActivity {

    private EditText resiverEmailAddress;
    private EditText message;
    private Button sendEmail;
    private Button cancelSendindEmail;

    private String listName;
    private String emailText = "";

   // @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_list_as_email);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
    }

    private void init(){
        getListName();
        resiverEmailAddress = (EditText) findViewById(R.id.etResiverEmail);
        message = (EditText) findViewById(R.id.etResiverEmailMessage);
        sendEmail = (Button) findViewById(R.id.bSendEmail);
        cancelSendindEmail = (Button) findViewById(R.id.bCancelSendingEmail);
        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                composeEmail();
            }
        });

        cancelSendindEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DAndDActivity.foodListProducts.clear();
                Intent intent = new Intent(getApplicationContext(), RedyListproducts.class);
                intent.putExtra("LIST_NAME", listName);
                startActivity(intent);

            }
        });

    }

    private void getListName(){
        Intent intent = getIntent();
        listName = intent.getStringExtra("LIST_NAME");
    }

    private void composeEmail(){
        makeEmailText();
        Intent sendEmail = new Intent(Intent.ACTION_SEND);
        sendEmail.setType("message/rfc822");
        sendEmail.putExtra(Intent.EXTRA_EMAIL  , new String[]{resiverEmailAddress.getText().toString()});
        sendEmail.putExtra(Intent.EXTRA_SUBJECT, listName);
        sendEmail.putExtra(Intent.EXTRA_TEXT   , emailText + "\n" + "\n" + message.getText().toString());
        try {
            startActivity(Intent.createChooser(sendEmail, getResources().getString(R.string.send_email)));
        } catch (android.content.ActivityNotFoundException ex) {

        }
        DAndDActivity.foodListProducts.clear();
    }

    private void makeEmailText(){

        int size = DAndDActivity.foodListProducts.size();
        Product product;
        for(int i = 0; i < size; i++){

            product =  DAndDActivity.foodListProducts.get(i);
            if(product.getProductName() == null){
                emailText += i + 1 + ". " + product.getProduct() + ", " +
                        product.getQuantity() + " " + product.getUnit() + "\n" + getResources().getString(R.string.price_in_eamail) + " " + product.getPrice() +
                        " " + product.getCurrency() + "\n\n";
            }else {
            emailText += i + 1 + ". "  +  product.getProductName() + " - " + product.getProduct() + ", " +
                    product.getQuantity() + " " + product.getUnit() + "\n" + getResources().getString(R.string.price_in_eamail) + " " + product.getPrice() +
                    " " + product.getCurrency() + "\n\n";
            }

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
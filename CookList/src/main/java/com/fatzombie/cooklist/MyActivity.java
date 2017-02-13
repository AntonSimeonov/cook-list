package com.fatzombie.cooklist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;

public class MyActivity extends Activity implements View.OnClickListener{

    private DBAdapter dbAdapter;

    private TextView listName;
    private TextView categoryInfo;
    private TextView productInfo;
    private AutoCompleteTextView autoCompleteCategories;
    private AutoCompleteTextView autoCompleteProducts;
    private AutoCompleteTextView autoCompleteProductNames;
    private EditText quantity;
    private EditText unit;
    private EditText price;
    private Button addProductButton;
    private Button saveProductsList;

    private ArrayAdapter<String> aaCategory;
    private ArrayAdapter<String> aaProduct;
    private ArrayAdapter<String> aaProductName;

    private ArrayList<String> alCategories;
    private ArrayList<String> alProducts;
    private ArrayList<String> alProductNames;

    public static final ArrayList<Product> selectedProducts = new ArrayList<Product>();

    public long listId;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_activity);
        init();
        //test1();

    }

    private void test1(){
        String categories;
        String products;
        categories = dbAdapter.getAllCategories();
        //products = dbAdapter.getAllProducts();
        products = dbAdapter.getAllproductsForCategory("Category 2");
        categoryInfo.setText(categories);
        productInfo.setText(products);
    }

    private void init(){
        listName = (TextView) findViewById(R.id.tvListNameOfList);
        categoryInfo = (TextView) findViewById(R.id.categoryInfo);
        productInfo = (TextView) findViewById(R.id.productInfo);
        autoCompleteCategories = (AutoCompleteTextView) findViewById(R.id.acCategory);
        autoCompleteProducts = (AutoCompleteTextView) findViewById(R.id.acProduct);
        autoCompleteProductNames = (AutoCompleteTextView) findViewById(R.id.acProductName);
        quantity = (EditText)  findViewById(R.id.etQuantity);
        unit = (EditText) findViewById(R.id.etUnits);
        price = (EditText) findViewById(R.id.etPrice);
        addProductButton = (Button) findViewById(R.id.bOk);
        saveProductsList = (Button) findViewById(R.id.saveProductsList);

        addProductButton.setOnClickListener(this);
        listName.setText(getListName());

        initDB();
        setAdapters();
        setCategoryTextWatcher();
        setProductTextWatcher();
        saveProductsList();
    }

    private void saveProductsList(){
        saveProductsList.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //To change body of implemented methods use File | Settings | File Templates.
                Intent intent = new Intent(getBaseContext(), SavePeoductsList.class);
                startActivity(intent);
            }
        });
    }

    private void setAdapters(){
        setCategoryAdapter();
        setProductAdapter();
        setProductNameAdapter();
    }

    private String getListName(){
       return getIntent().getExtras().getString("LIST_NAME");
    }

    private void setCategoryTextWatcher(){
        autoCompleteCategories.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //To change body of implemented methods use File | Settings | File Templates.
                String category = editable.toString();
                if(alCategories.contains(category)){
                    aaProduct.clear();
                    aaProduct.notifyDataSetChanged();
                    aaProduct.addAll(dbAdapter.getAllproductsForCategoryInList(category));
                    aaProduct.notifyDataSetChanged();
                }

            }
        });
    }

    private void setProductTextWatcher(){
        autoCompleteProducts.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void afterTextChanged(Editable s) {
                //To change body of implemented methods use File | Settings | File Templates.
                String product = s.toString();
                if (alProducts.contains(product)) {
                    aaProductName.clear();
                    aaProductName.notifyDataSetChanged();
                    aaProductName.addAll(dbAdapter.getAllProductNamesForProductInList(product));
                    aaProductName.notifyDataSetChanged();
                }
            }
        });
    }

    private void setProductNameAdapter(){
        alProductNames = dbAdapter.getAllProductNamesForProductInList("Product 1");
        aaProductName = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, alProductNames);
        autoCompleteProductNames.setAdapter(aaProductName);
    }

    private void setProductAdapter() {
        //To change body of created methods use File | Settings | File Templates.
        alProducts = dbAdapter.getAllproductsForCategoryInList("Category 1");
        aaProduct = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, alProducts);
        autoCompleteProducts.setAdapter(aaProduct);
    }

    private void setCategoryAdapter() {
        //To change body of created methods use File | Settings | File Templates.
        alCategories = dbAdapter.getAllCategoriesInList();
        aaCategory = new ArrayAdapter <String>(this,android.R.layout.simple_list_item_1, alCategories);
        autoCompleteCategories.setAdapter(aaCategory);
    }

    private void initDB(){
        dbAdapter = new DBAdapter(this);
        dbAdapter.open();
        dbAdapter.populateDB();
    }

    @Override
    protected void onResume() {
        super.onResume();    //To change body of overridden methods use File | Settings | File Templates.
    }

    private void addNewCategory(String category){

        if(!alCategories.contains(category)){

            dbAdapter.insertNewCategory(category);
            aaCategory.add(category);
            aaCategory.notifyDataSetChanged();

        }

    }

    private void addNewProduct(String category, String product){

        if(!alProducts.contains(product)){

            long categoryId = dbAdapter.getCategoryIdFromCategoryName(category);
            dbAdapter.insertNewProduct(product, categoryId);
            aaProduct.add(product);
            aaProduct.notifyDataSetChanged();

        }
    }

    private void addNewProductName(String productName, String product){

        if(!alProductNames.contains(productName)){
            long productId = dbAdapter.getProductIdFromProduct(product);
            dbAdapter.insertNewProductName(productName, productId);
            aaProductName.add(productName);
            aaProductName.notifyDataSetChanged();
        }

    }

    private void createNewList(){
        listId = dbAdapter.insertNewList(getListName());
    }

    private void addNewProductDetiles(long productNameId, float price, float quantity, String unit, String note){

        //dbAdapter.insertNewListProduct(listId, productNameId, price, quantity, unit, note);

    }

    @Override
        public void onClick(View v) {
        //To change body of implemented methods use File | Settings | File Templates.
        createNewList();
        String selectedCategory = autoCompleteCategories.getText().toString();
        String selectedProduct = autoCompleteProducts.getText().toString();
        String selectdProductName = autoCompleteProductNames.getText().toString();
        String selectedQuantity = quantity.getText().toString();
        String selectedUnit = unit.getText().toString();
        String selectdPrice = price.getText().toString();

        addNewCategory(selectedCategory);
        addNewProduct(selectedCategory, selectedProduct);
        addNewProductName(selectdProductName, selectedProduct);

        selectedProducts.add(new Product(selectedCategory, selectedProduct, Float.parseFloat(selectdPrice), selectdProductName, Float.parseFloat(selectedQuantity), selectedUnit));
        addNewProductDetiles(dbAdapter.getProductNameIdFormProductName(selectdProductName), Float.parseFloat(selectdPrice), Float.parseFloat(selectedQuantity), selectedUnit, null);
    }
}

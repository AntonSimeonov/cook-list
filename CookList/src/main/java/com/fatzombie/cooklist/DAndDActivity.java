package com.fatzombie.cooklist;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.*;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.*;
import android.widget.*;


import java.util.ArrayList;

public class DAndDActivity extends ActionBarActivity implements CreateNewCategoryDialog.CreateNewCategoryListener, CreateNewProductDialog.CreateNewProductListener, CreateNewListProductDialog.CreateNewListProductInterface, CreateNewProductNameDialog.CreateNewProductNameListener, ProductInformationFragment.IformationFragment, DefaultQuantityDialog.DefaultQuantityDialogInterface, DefaultUnitDialog.DefaultUnitDialogInterface
, DafaultQuantityValues.GetDefaultQuantityValues{

    private TextView dropZone;
    private GridView productsGrid;
    private ListView categories;
    private View dropFragment;

    //private ArrayAdapter<String> aaCategories;
    private CustomSimpleAdapter aaCategories;
    private static ArrayList<String> alCategories;
   // private ArrayAdapter<String> aaProducts;
  //  private CustomSimpleAdapter aaProducts;
    private CustomSimpleGridProductAdapter aaProducts;
    private static ArrayList<String> alProducts;
    private ArrayList<String> allProductNames;

    private TextView listNameTextView;
    private TextView addNewCategoryTextView;
    private TextView addNewProductTextView;
    private TextView addNewProductNameTextView;
    private TextView curentCategory;
    private TextView tvCurentProduct;
    private TextView liftDZBraket;
    private TextView rightDZBraket;

    private DBAdapter dbAdapter;

    private boolean inCategory = true;
    private boolean inProduct = false;
    private boolean inProductName = false;

    private String category;
    private String product;
    private String productName;

    private Product currentProduct;
    private float selectdProductQuantity;

    public static String listName;
    public static final String STATE_PREF = "state_pref";
    public static final String LIST_NAMEB = "listNameb";

    public static final ArrayList<Product> foodListProducts = new ArrayList<Product>();

    //private FragmentManager fragmentManager;
   // private FragmentTransaction fragmentTransaction;

    /**
     * Called when the activity is first created.
     */
   // @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
       // getActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init(savedInstanceState);
        initSecond();
        setUpDrag();
        setUpDragListener2();
        setCategoryClickListener();
        setUpNewCategoryDialog();
        setUpNewProductDialog();
        setUpNewProductNameDialog();
        setUpDetiles();
        initFragment();

    }

    private void init(Bundle savedInstanceState){


        initDB();
//
        productsGrid = (GridView) findViewById(R.id.gvProducts);
        categories = (ListView) findViewById(R.id.lvCategory);
        dropZone = (TextView) findViewById(R.id.tvDropZone);
        curentCategory = (TextView) findViewById(R.id.tvCurentCategory);
        tvCurentProduct = (TextView) findViewById(R.id.tvCurentProduct);
//        alProducts = new ArrayList<String>();
//        alCategories = new ArrayList<String>();
        //asynk inits the db
        InitDB initDB = new InitDB();
        initDB.execute(null);
//        if(alCategories.isEmpty()){
//        alCategories = dbAdapter.getAllCategoriesInList();
//        }
//        if(alProducts.isEmpty()){
//        alProducts = dbAdapter.getAllproductsForCategoryInList("Vegetables");
//        }
        //aaCategories = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, alCategories);
//        aaCategories = new CustomSimpleAdapter(this, R.layout.custom_simple_adapter, alCategories);
//        categories.setAdapter(aaCategories);
//       // aaProducts = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, alProducts);
//        //aaProducts = new CustomSimpleAdapter(this, R.layout.custom_simple_adapter, alProducts);
//        aaProducts = new CustomSimpleGridProductAdapter(this, R.layout.custom_simple_grid_product_adapter, alProducts);
//        productsGrid.setAdapter(aaProducts);
//
//        addNewCategoryTextView = (TextView) findViewById(R.id.tvAddNewCategory);
//        addNewProductTextView = (TextView) findViewById(R.id.tvAddNewProduct);
//        addNewProductNameTextView = (TextView) findViewById(R.id.tvAddNewProductName);
//        curentCategory = (TextView) findViewById(R.id.tvCurentCategory);
//        Typeface font = Typeface.createFromAsset(getAssets(), "Roboto-Black.ttf");
//        curentCategory.setTypeface(font);
//        curentCategory.setText("Vegetables");
//        listNameTextView = (TextView) findViewById(R.id.tvListName);
//        liftDZBraket = (TextView) findViewById(R.id.tvLeftDZBraket);
//        rightDZBraket = (TextView) findViewById(R.id.tvRightDZBraket);
//        liftDZBraket.setText("< ");
//        rightDZBraket.setText(" >");
//     //   if(savedInstanceState == null){
//        getListNameExtra();
//            listNameTextView.setText(listName);
////        }else{
////
////        listName = savedInstanceState.getString(LIST_NAMEB);
////            listNameTextView.setText(savedInstanceState.getString(LIST_NAMEB));
////        }
//
//       // dropZone.setTextColor(getResources().getColor(R.color.greenish));
//
//        categories.setDividerHeight(1);


//        setUpDrag();
//        setUpDragListener();
//        setCategoryClickListener();
//        setUpNewCategoryDialog();
//        setUpNewProductDialog();
//        setUpNewProductNameDialog();
//        setUpDetiles();
//        initFragment();

//        SharedPreferences previusState = PreferenceManager.getDefaultSharedPreferences(this);
//        listName = previusState.getString("litsName", listName);
//        listNameTextView.setText(listName);

    }

    private void initSecond(){
        dropFragment = (View) findViewById(R.id.flDefoltQuantityValues);
        addNewCategoryTextView = (TextView) findViewById(R.id.tvAddNewCategory);
        addNewProductTextView = (TextView) findViewById(R.id.tvAddNewProduct);
        addNewProductNameTextView = (TextView) findViewById(R.id.tvAddNewProductName);

        Typeface font = Typeface.createFromAsset(getAssets(), "Roboto-Black.ttf");
        Typeface fontThin = Typeface.createFromAsset(getAssets(), "Roboto-Thin.ttf");
        curentCategory.setTypeface(font);
        //curentCategory.setText("Vegetables");
        listNameTextView = (TextView) findViewById(R.id.tvListName);
        liftDZBraket = (TextView) findViewById(R.id.tvLeftDZBraket);
        rightDZBraket = (TextView) findViewById(R.id.tvRightDZBraket);
        liftDZBraket.setText(" ");
        rightDZBraket.setText(" ");

        liftDZBraket.setTypeface(fontThin);
        rightDZBraket.setTypeface(fontThin);
        dropZone.setTypeface(fontThin);
        listNameTextView.setTypeface(fontThin);
       // dropZone.setText("                        + ");

        getListNameExtra();
        listNameTextView.setText(listName);
        setOnClickListenerForCurentCategory();

        categories.setDividerHeight(1);

    }

    private void setOnClickListenerForCurentCategory(){
        curentCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetAllProductsForCategory2 allProducts = new GetAllProductsForCategory2();
                allProducts.execute(curentCategory.getText().toString());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.saveList:
                saveList();
                return true;
            case R.id.miSettingsMenu:
                Intent intent = new Intent(this, Settings.class);
                startActivity(intent);
                return true;
            case R.id.miMenu:
                intent = new Intent(this, MainMenu.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void initFragment(){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
       // ProductInformationFragment fragment = new ProductInformationFragment();
        AddListProductFragment fragment = new AddListProductFragment();
        fragmentTransaction.add(R.id.flDefoltQuantityValues, fragment);
        fragmentTransaction.commit();
    }

    private void getListNameExtra(){
        Intent intent = getIntent();
        listName = intent.getStringExtra("ENTERT_LIST_NAME");
    }

    private void setUpNewCategoryDialog(){

        addNewCategoryTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //To change body of implemented methods use File | Settings | File Templates.
                buildNewCategoryDialog();

            }
        });

    }

    private void  setUpnewProductNameDialog(){
        addNewProductNameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void buildNewCategoryDialog(){

        DialogFragment newCategoryDialog = new CreateNewCategoryDialog();
        newCategoryDialog.show(getFragmentManager(), "NewCategory");

    }

    private void setUpNewProductDialog(){
        addNewProductTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //To change body of implemented methods use File | Settings | File Templates.
                buildNewProductDialog();

            }
        });
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void buildNewProductDialog(){
        DialogFragment newProductDialog = new CreateNewProductDialog();
        newProductDialog.show(getFragmentManager(), "NewProduct");
    }

    private void setUpNewProductNameDialog(){
        addNewProductNameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //To change body of implemented methods use File | Settings | File Templates.
                buildNewProductNameDialog();
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void buildNewProductNameDialog(){
        DialogFragment newProductNameDialog = new CreateNewProductNameDialog();
        newProductNameDialog.show(getFragmentManager(), "NewProductNameDialog");
    }

    private void initDB(){
        dbAdapter = new DBAdapter(this);
        dbAdapter.open();
       // dbAdapter.populateDB();
       // populateDbOnStart();
    }


    //Set up drag listener for the products for a category.
    private void setUpDrag(){

        productsGrid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                String productName = alProducts.get(i);

                ClipData.Item item = new ClipData.Item(productName);
                ClipData dragData = new ClipData(productName, new String[] {ClipDescription.MIMETYPE_TEXT_PLAIN}, item);
                //View.DragShadowBuilder productSadow = new View.DragShadowBuilder(view);
                View.DragShadowBuilder productSadow = new ProductShadowBuilder(view);
                view.startDrag(dragData, productSadow, null, 0);
                return true;  //To change body of implemented methods use File | Settings | File Templates.
            }
        });

    }

//    private void setUpDrag(){
//
//
//
//        productsGrid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                String productName = alProducts.get(i);
//
//                ClipData.Item item = new ClipData.Item(productName);
//                ClipData dragData = new ClipData(productName, new String[] {ClipDescription.MIMETYPE_TEXT_PLAIN}, item);
//                //View.DragShadowBuilder productSadow = new View.DragShadowBuilder(view);
//                View.DragShadowBuilder productSadow = new ProductShadowBuilder(view);
//                view.startDrag(dragData, productSadow, null, 0);
//                return true;  //To change body of implemented methods use File | Settings | File Templates.
//            }
//        });
//
//    }


    //Set up click listener on the products for a category.
    private void setUpDetiles(){
        productsGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if(inCategory){
                  //  productsGrid.setOnItemClickListener(null);
                    GetAllProductNamesForProduct productNames = new GetAllProductNamesForProduct();
                    productNames.execute(alProducts.get(i));
                    inCategory = false;
                    product = alProducts.get(i);
                    addNewProductNameTextView.setText(getResources().getString(R.string.brand_variety));
                    addNewProductTextView.setText(" ");
                    tvCurentProduct.setText(product);
                }else{
                    product = alProducts.get(i);
                    tvCurentProduct.setText(product);
                }
            //To change body of implemented methods use File | Settings | File Templates.

        }
    });
}



    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setUpDragListener(){
        dropZone.setOnDragListener(new View.OnDragListener() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public boolean onDrag(View view, DragEvent dragEvent) {

                final int action = dragEvent.getAction();
                String lastEnterdProduct = dropZone.getText().toString();
                    switch (action){
                    case DragEvent.ACTION_DRAG_STARTED:
                        //dropZone.setShadowLayer(20, 0, 0, R.color.greysh);
                        //dropZone.setHighlightColor(Color.WHITE);
                      // dropZone.setTextColor(getResources().getColor(R.color.greenish));
                        //lastEnterdProduct = dropZone.getText().toString();
                       dropZone.setText(getResources().getString(R.string.tv_main_drop_zone));
                        liftDZBraket.setText("<");
                        rightDZBraket.setText("<");
                        break;
                    case DragEvent.ACTION_DRAG_ENTERED:

                        break;
                    case DragEvent.ACTION_DRAG_LOCATION:
                        break;
                    case DragEvent.ACTION_DRAG_EXITED:
                        dropZone.setText(lastEnterdProduct);
                        break;
                    case DragEvent.ACTION_DROP:
                         //Crates new dialog for entering a product quantity.
                        //setNewListProductDialog();
                       // dropZone.setTextColor(Color.WHITE);
                        ClipData.Item drageditem = dragEvent.getClipData().getItemAt(0);
                        dropZone.setText(drageditem.getText().toString());

                        //Add fragment to activity

                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        DafaultQuantityValues fragment = new DafaultQuantityValues();

                        fragmentTransaction.replace(R.id.flDefoltQuantityValues, fragment);
                        fragmentTransaction.commit();
                        //End of adding fragment

                        break;
                    case DragEvent.ACTION_DRAG_ENDED:
                        //dropZone.setText(drageditem.getText().toString());
                        break;
                }

                return true;  //To change body of implemented methods use File | Settings | File Templates.
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setUpDragListener2(){
        dropFragment.setOnDragListener(new View.OnDragListener() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public boolean onDrag(View view, DragEvent dragEvent) {

                final int action = dragEvent.getAction();
                String lastEnterdProduct = dropZone.getText().toString();
                switch (action){
                    case DragEvent.ACTION_DRAG_STARTED:
                        //dropZone.setShadowLayer(20, 0, 0, R.color.greysh);
                        //dropZone.setHighlightColor(Color.WHITE);
                        // dropZone.setTextColor(getResources().getColor(R.color.greenish));
                        //lastEnterdProduct = dropZone.getText().toString();
                       // dropZone.setText(getResources().getString(R.string.tv_main_drop_zone));

                        break;
                    case DragEvent.ACTION_DRAG_ENTERED:

                        break;
                    case DragEvent.ACTION_DRAG_LOCATION:
                        break;
                    case DragEvent.ACTION_DRAG_EXITED:
                        dropZone.setText(lastEnterdProduct);
                        break;
                    case DragEvent.ACTION_DROP:
                        //Crates new dialog for entering a product quantity.
                        //setNewListProductDialog();
                        // dropZone.setTextColor(Color.WHITE);
                        liftDZBraket.setText("");
                        rightDZBraket.setText("");
                        ClipData.Item drageditem = dragEvent.getClipData().getItemAt(0);
                        dropZone.setText(drageditem.getText().toString());

                        //Add fragment to activity

                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        DafaultQuantityValues fragment = new DafaultQuantityValues();

                        fragmentTransaction.replace(R.id.flDefoltQuantityValues, fragment);
                        fragmentTransaction.commit();
                        //End of adding fragment

                        break;
                    case DragEvent.ACTION_DRAG_ENDED:
                        //dropZone.setText(drageditem.getText().toString());
                        break;
                }

                return true;  //To change body of implemented methods use File | Settings | File Templates.
            }
        });
    }

    private void setQuantityForSelectedProductInFragment(float quantity){
//        Fragment frag = getFragmentManager().findFragmentById(R.id.flDefoltQuantityValues);
//        ((TextView) frag.getView().findViewById(R.id.tvProductInfoFragmentQuantity)).setText("" + quantity);
    }
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setQuantityForSelectedProductInFragment2(float quantity){
//        Fragment frag = getFragmentManager().findFragmentById(R.id.flDefoltQuantityValues);
//        ((TextView) frag.getView().findViewById(R.id.tvProductInfoFragmentQuantity)).setText("" + quantity);

       ProductInformationFragment fragment_obj = (ProductInformationFragment)getFragmentManager().
                findFragmentById(R.id.flDefoltQuantityValues);
        fragment_obj.updateSelectedProductQuantityFragment(quantity);
    }
    //Sets up fragment maniger and fragment transaction members.
    private void setUpFragmentsMainComponents(){
       //fragmentManager = getFragmentManager();
        //fragmentTransaction = fragmentManager.beginTransaction();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setNewListProductDialog(){
        DialogFragment newListProductDialog = new CreateNewListProductDialog();
        newListProductDialog.show(getFragmentManager(), "NewListProduct");
    }

        private void setCategoryClickListener(){

        categories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //To change body of implemented methods use File | Settings | File Templates.
              //  setUpDetiles();
                view.setSelected(true);
                tvCurentProduct.setText(getResources().getString(R.string.current_product));
                curentCategory.setText(alCategories.get(i));
                addNewProductNameTextView.setText("");
                addNewProductTextView.setText(getResources().getString(R.string.tv_main_add_product));
                inCategory = true;
                product = null;
                //aaProducts.clear();
                //aaProducts.addAll(dbAdapter.getAllproductsForCategoryInList(alCategories.get(i)));
                //aaProducts.notifyDataSetChanged();
                GetAllProductsForCategory allProducts = new GetAllProductsForCategory();
                allProducts.execute(alCategories.get(i));

            }
        });
    }

    private void populateProducts(){

        for(int i = 0; i < 20; i++){
            alProducts.add("Product " + (i + 1));
        }
    }

    private void populateCategories(){

        for (int i = 0; i < 40; i++){
            alCategories.add("Category " + (i + 1));
        }

    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String newCatName) {
        //To change body of implemented methods use File | Settings | File Templates.
       // aaCategories.add(dialog.getString(R.id.etAddNewCategoryDialog).toString());
        //EditText categoryName = (EditText) dialog.getView().findViewById(R.id.etAddNewCategoryDialog);
        //aaCategories.add(categoryName.getText().toString());
        dbAdapter.insertNewCategory(newCatName);
        aaCategories.add(newCatName);
        aaCategories.notifyDataSetChanged();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        //To change body of implemented methods use File | Settings | File Templates.

    }



    @Override
    public void onNewProductDialogPositiveClick(DialogFragment dialog, String newProduct) {
        //To change body of implemented methods use File | Settings | File Templates.
        long categoryId = dbAdapter.getCategoryIdFromCategoryName(curentCategory.getText().toString());
        dbAdapter.insertNewProduct(newProduct, categoryId);
        aaProducts.add(newProduct);
        aaProducts.notifyDataSetChanged();
    }

    @Override
    public void onNewProductDialogNegativeClick(DialogFragment dialog) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onNewListProductDialogPositiveClick(DialogFragment dialog, String brand, float quantity, String quantityUnits, float price, String currency) {
        //To change body of implemented methods use File | Settings | File Templates.

//            if(product != null){
//                foodListProducts.add(new Product(curentCategory.getText().toString(), product, price, dropZone.getText().toString(), quantity, quantityUnits, getResources().getString(R.string.currency)));
//            }else{
//
//                foodListProducts.add(new Product(curentCategory.getText().toString(), dropZone.getText().toString(), price, null, quantity, quantityUnits, getResources().getString(R.string.currency)));
//            }
//        product = null;
    }


    @Override
    public void onNewListProductDialogNegativeClick(DialogFragment dialog) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    //DefaultQuantityvalue interface implementation.
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void getDefaultQuantityValue(String quan) {
        //To change body of implemented methods use File | Settings | File Templates.
        //The value -25 means that the user whants manualy to enter quantity trough the quantity dialog.

        float quantity;
        try{
          quantity = Float.parseFloat(quan);
        }catch (Exception ex){
            quantity = 0;
            Toast.makeText(this, R.string.toast_incorect_value, Toast.LENGTH_LONG).show();
        }

//        if(quantity == -25){
//            setNewListProductDialog();
//        }  else{
//
//            if(product != null){
//                foodListProducts.add(new Product(curentCategory.getText().toString(), product, 0, dropZone.getText().toString(), quantity, null, getResources().getString(R.string.currency)));
//            }else{
//
//                foodListProducts.add(new Product(curentCategory.getText().toString(), dropZone.getText().toString(), 0, null, quantity, null, getResources().getString(R.string.currency)));
//            }
//           // product = null;
//
//        }

        if(quantity == -25){
            createDefaultQuantityDialog();
        }else {
            selectdProductQuantity = quantity;
            liftDZBraket.setText(Float.toString(quantity));
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            ProductInformationFragment fragment = new ProductInformationFragment();

            fragmentTransaction.replace(R.id.flDefoltQuantityValues, fragment);
            fragmentTransaction.commit();
        }



    }

    //new product name
    @Override
    public void onNewProductNameDialogPositiveClick(DialogFragment dialog, String newProductName) {
        //To change body of implemented methods use File | Settings | File Templates.
        long productId = dbAdapter.getProductIdFromProduct(product);
        dbAdapter.insertNewProductName(newProductName, productId);
        aaProducts.add(newProductName);
        aaProducts.notifyDataSetChanged();

    }

    @Override
    public void onNewProductNameDialogNegativeClick(DialogFragment dialog) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void getDefoultUnits(String units) {

        if(units.equals("Keyboard")){
            createDefaultUnitDialog();
        }else {

        if(product != null){
                foodListProducts.add(new Product(curentCategory.getText().toString(), product, 0, dropZone.getText().toString(), selectdProductQuantity, units, getResources().getString(R.string.currency)));
            }else{

                foodListProducts.add(new Product(curentCategory.getText().toString(), dropZone.getText().toString(), 0, null, selectdProductQuantity, units, getResources().getString(R.string.currency)));
            }
            rightDZBraket.setText(units);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        AddListProductFragment fragment = new AddListProductFragment();

        fragmentTransaction.replace(R.id.flDefoltQuantityValues, fragment);
        fragmentTransaction.commit();
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void createDefaultQuantityDialog(){
        DialogFragment defaultQuantityDialog = new DefaultQuantityDialog();
        defaultQuantityDialog.show(getFragmentManager(), "DefaultQuantity");

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void createDefaultUnitDialog(){
        DialogFragment defaultUnitDialog = new DefaultUnitDialog();
        defaultUnitDialog.show(getFragmentManager(), "DefaultUnit");

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void defaultQuantityDialogPositiveClick(float quantity) {


            selectdProductQuantity = quantity;

            liftDZBraket.setText("" + quantity);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ProductInformationFragment fragment = new ProductInformationFragment();

        fragmentTransaction.replace(R.id.flDefoltQuantityValues, fragment);
        fragmentTransaction.commit();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void defaultUnitDialogPositiveClick(String unit) {
        if(product != null){
            foodListProducts.add(new Product(curentCategory.getText().toString(), product, 0, dropZone.getText().toString(), selectdProductQuantity, unit, getResources().getString(R.string.currency)));
        }else{

            foodListProducts.add(new Product(curentCategory.getText().toString(), dropZone.getText().toString(), 0, null, selectdProductQuantity, unit, getResources().getString(R.string.currency)));
        }
        product = null;
        rightDZBraket.setText(unit);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        AddListProductFragment fragment = new AddListProductFragment();

        fragmentTransaction.replace(R.id.flDefoltQuantityValues, fragment);
        fragmentTransaction.commit();
    }

    private class GetAllProductsForCategory extends AsyncTask<String, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(String... params) {
            String categoryName = params[0];
            ArrayList<String> allproducts;
            allproducts = dbAdapter.getAllproductsForCategoryInList(categoryName);
            return allproducts;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            //super.onPostExecute(strings);    //To change body of overridden methods use File | Settings | File Templates.
            aaProducts.clear();
            aaProducts.addAll(strings);

            //productsGrid.setOnItemClickListener(null);
            setUpDetiles();

            aaProducts.notifyDataSetChanged();

        }
    }



    private class GetAllProductsForCategory2 extends AsyncTask<String, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(String... params) {
            String categoryName = params[0];
            ArrayList<String> allproducts;
            allproducts = dbAdapter.getAllproductsForCategoryInList(categoryName);
            return allproducts;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            //super.onPostExecute(strings);    //To change body of overridden methods use File | Settings | File Templates.
            aaProducts.clear();
            aaProducts.addAll(strings);
            //
            addNewProductNameTextView.setText("");
            addNewProductTextView.setText(getResources().getString(R.string.tv_main_add_product));
            inCategory = true;
            product = null;
            //
            //productsGrid.setOnItemClickListener(null);
            setUpDetiles();

            aaProducts.notifyDataSetChanged();

        }
    }


    private class GetAllProductNamesForProduct extends AsyncTask<String, Void, ArrayList<String>>{

        @Override
        protected ArrayList<String> doInBackground(String... params) {
            String product = params[0];
            ArrayList<String> allProductNames;
            allProductNames = dbAdapter.getAllProductNamesForProductInList(product);
            return allProductNames;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        @Override
        protected void onPostExecute(ArrayList<String> strings) {

            aaProducts.clear();
            aaProducts.addAll(strings);
            //makes the produkt names not clickable
            productsGrid.setOnItemClickListener(null);

            aaProducts.notifyDataSetChanged();

        }
    }

    private void saveList(){

        Intent intent = new Intent(this, SavePeoductsList.class);
        intent.putExtra("LIST_NAME", listName);
        startActivity(intent);

    }



    @Override
    protected void onStop() {
        super.onStop();    //To change body of overridden methods use File | Settings | File Templates.
        //dbAdapter.close();
        //listNameTextView.setText(listName);
        SharedPreferences settings = getSharedPreferences(STATE_PREF, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(LIST_NAMEB, listNameTextView.getText().toString());
        editor.commit();


    }

    @Override
    protected void onPause() {
        super.onPause();    //To change body of overridden methods use File | Settings | File Templates.


    }

    @Override
    protected void onRestart() {
        super.onRestart();    //To change body of overridden methods use File | Settings | File Templates.
        //dbAdapter.open();


    }

    @Override
    protected void onStart() {
        super.onStart();    //To change body of overridden methods use File | Settings | File Templates.
        //listNameTextView.setText(listName);

    }

    @Override
    protected void onResume() {
        super.onResume();    //To change body of overridden methods use File | Settings | File Templates.
        if(listName == null){
        SharedPreferences settings = getSharedPreferences(STATE_PREF, 0);
        String lName = settings.getString(LIST_NAMEB, listName);
        listNameTextView.setText(lName);
        listName = lName;
        }

    }

    protected void onDestroy() {
        super.onDestroy();    //To change body of overridden methods use File | Settings | File Templat
        dbAdapter.close();
        //foodListProducts.clear();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {


        super.onSaveInstanceState(outState);    //To change body of overridden methods use File | Settings | File Templates.
        outState.putString(LIST_NAMEB, listNameTextView.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {


        super.onRestoreInstanceState(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        listName = savedInstanceState.getString(LIST_NAMEB);
        listNameTextView.setText(savedInstanceState.getString(LIST_NAMEB));
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

    private class InitDB extends AsyncTask<Void, Void, Void>{
        private String categoriNameAtStart;
        @Override
        protected Void doInBackground(Void... voids) {

            if(dbAdapter.getAllCategoriesInList() == null){
              //  Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.populate_db), Toast.LENGTH_LONG).show();
                populateDbOnStart();
            }


            alCategories = dbAdapter.getAllCategoriesInList();
            //alProducts = dbAdapter.getAllproductsForCategoryInList("Vegetables");
            alProducts = dbAdapter.getFirstCategoryInDataBase();
            categoriNameAtStart = dbAdapter.getFirstCategoryNameInDataBase();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
 //           super.onPostExecute(aVoid);
            curentCategory.setText(categoriNameAtStart);
            aaCategories = new CustomSimpleAdapter(getApplicationContext(), R.layout.custom_simple_adapter, alCategories);
            categories.setAdapter(aaCategories);
            aaProducts = new CustomSimpleGridProductAdapter(getApplicationContext(), R.layout.custom_simple_grid_product_adapter, alProducts);
            productsGrid.setAdapter(aaProducts);
//

        }
    }
}

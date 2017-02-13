package com.fatzombie.cooklist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: anton
 * Date: 11/16/13
 * Time: 12:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class DBAdapter {

    //Helper class witch is creating the database, opens and closes connection to the database
    //and when the version changes upgardes the database.

    private class DBHelper extends SQLiteOpenHelper {

        public static final String DATABASE_NAME = "product_list";
        public static final int DATABASE_VERSION = 1;

        public DBHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);

        }

        @Override

        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            //To change body of implemented methods use File | Settings | File Templates.
            sqLiteDatabase.execSQL(DBTableCategory.CREATE_TABLE);
            sqLiteDatabase.execSQL(DBTableProduct.CREATE_TABLE);
            sqLiteDatabase.execSQL(DBTableProductName.CREATE_TABLE);
            sqLiteDatabase.execSQL(DBtableListProduct.CREATE_TABLE);
            sqLiteDatabase.execSQL(DBTableList.CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
            //To change body of implemented methods use File | Settings | File Templates.
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DBTableCategory.TABLE_NAME);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DBTableProduct.TABLE_NAME);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DBTableProductName.TABLE_NAME);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DBtableListProduct.TABLE_NAME);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DBTableList.TABLE_NAME);
            onCreate(sqLiteDatabase);
        }

    }

    DBHelper helper;
    SQLiteDatabase db;
    Context context;

    public DBAdapter(Context c){
        context = c;
        helper  = new DBHelper(context);
    }

    public void open(){

        try {

            db = helper.getWritableDatabase();

        }  catch(Exception ex){
            db = helper.getReadableDatabase();
        }

    }

    public void close(){
        helper.close();
    }


    //Inserts new list product by giving, list id and productName Id.
    public long insertNewListProduct(long listId, long productNameId, float productId,float price, float listProductQuantity, String unit, String note, String currency){

        ContentValues newListProduct = new ContentValues();
        newListProduct.put(DBtableListProduct.KEY_LIST_ID, listId);
        newListProduct.put(DBtableListProduct.KEY_PRODUCT_NAME_ID, productNameId);
        newListProduct.put(DBtableListProduct.KEY_PRODUCT_ID, productId);
        newListProduct.put(DBtableListProduct.KEY_LIST_PRODUCT_PRICE, price);
        newListProduct.put(DBtableListProduct.KEY_LIST_PRODUCT_QUANTITY, listProductQuantity);
        newListProduct.put(DBtableListProduct.KEY_LIST_PRODUCT_UNITS, unit);
        newListProduct.put(DBtableListProduct.KEY_LIST_PRODUCT_CURRENCY, currency);
        newListProduct.put(DBtableListProduct.KEY_LIST_PRODUCT_NOTES, note);
        return db.insert(DBtableListProduct.TABLE_NAME, null, newListProduct);

    }

    //Inserts new list product with product id, not with product name id.
    public long insertNewListProductWithOnlyProduct(long listId, long productId, float listProductQuantity, String unit){

        ContentValues newListProduct = new ContentValues();
        newListProduct.put(DBtableListProduct.KEY_LIST_ID, listId);
        newListProduct.put(DBtableListProduct.KEY_PRODUCT_ID, productId);
        newListProduct.put(DBtableListProduct.KEY_PRODUCT_NAME_ID, -1);
        //newListProduct.put(DBtableListProduct.KEY_LIST_PRODUCT_PRICE, price);
        newListProduct.put(DBtableListProduct.KEY_LIST_PRODUCT_QUANTITY, listProductQuantity);
        newListProduct.put(DBtableListProduct.KEY_LIST_PRODUCT_UNITS, unit);
        //newListProduct.put(DBtableListProduct.KEY_LIST_PRODUCT_NOTES, note);
        return db.insert(DBtableListProduct.TABLE_NAME, null, newListProduct);

    }



    //Inserts new list with the appropriet name, and put it in database.
    public long insertNewList(String listName){
        ContentValues newList = new ContentValues();
        newList.put(DBTableList.KEY_LIST_NAME, listName);
        return db.insert(DBTableList.TABLE_NAME, null, newList);
    }


    //Inserts new category in the database.
    public long insertNewCategory(String categoryName){

        ContentValues newCategory = new ContentValues();
        newCategory.put(DBTableCategory.KEY_CATEGORY_NAME, categoryName);
        return db.insert(DBTableCategory.TABLE_NAME, null, newCategory);

    }


    //Inserts new product with name and category id.
    public long insertNewProduct(String productName, long catergoryId){

        ContentValues newProduct = new ContentValues();
        newProduct.put(DBTableProduct.KEY_PRODUCT_NAME, productName);
        newProduct.put(DBTableProduct.KEY_CATEGORY_ID, catergoryId);
        return db.insert(DBTableProduct.TABLE_NAME, null, newProduct);

    }

    public long insertNewProductName(String productName, long productId){

        ContentValues newProductName = new ContentValues();
        newProductName.put(DBTableProductName.KEY_PRODUCT_NAME, productName);
        newProductName.put(DBTableProductName.KEY_PRODUCT_ID, productId);
        return db.insert(DBTableProductName.TABLE_NAME, null, newProductName);

    }

    public long getCategoryIdFromCategoryName(String categoryName){
        String[] wildCard = {categoryName};
        String[] columns = {DBTableCategory.KEY_CATEGORY_ID};
        //String query = "select category_id from category where category_name = ?";
        //Cursor cursor = db.rawQuery(query, wildCard);
         Cursor cursor = db.query(DBTableCategory.TABLE_NAME, columns, DBTableCategory.KEY_CATEGORY_NAME + " = ?", wildCard, null,
                 null, null);
        int numberOfColumnOFCategoryId = cursor.getColumnIndex(DBTableCategory.KEY_CATEGORY_ID);

        cursor.moveToFirst();
        long categoryId = cursor.getLong(numberOfColumnOFCategoryId);
        cursor.close();
        return categoryId;
    }

    public long getProductNameIdFormProductName(String productName){
        String[] wildCard = {productName};
        String[] columns = {DBTableProductName.KEY_PRODUCT_NAME_ID};

        Cursor cursor = db.query(DBTableProductName.TABLE_NAME, columns, DBTableProductName.KEY_PRODUCT_NAME + " = ?",
                wildCard, null, null, null);

        int numberOfColumnProductNameId = cursor.getColumnIndex(DBTableProductName.KEY_PRODUCT_NAME_ID);

        cursor.moveToFirst();
        long productNameId = -2;
        try{
            productNameId = cursor.getLong(numberOfColumnProductNameId);
        }catch (Exception ex){

        }

        cursor.close();

        return productNameId;
    }

    public long getProductIdFromProduct(String product){
        String[] wildCard = {product};
        String[] columns = {DBTableProduct.KEY_PRODUCT_ID};
        Cursor cursor = db.query(DBTableProduct.TABLE_NAME, columns, DBTableProduct.KEY_PRODUCT_NAME + " = ?",
                wildCard, null, null, null);
        int numbreOFColumnOfProductId = cursor.getColumnIndex(DBTableProduct.KEY_PRODUCT_ID);
        long productId;
        cursor.moveToFirst();
        if(cursor.getCount() != 0){
            productId = cursor.getLong(numbreOFColumnOfProductId);
        cursor.close();
        }else{
            productId = -2;
        }
        return productId;

    }

    public long getListIdFromListName(String listName){
        String[] wildCard = {listName};
        String[] columns = {DBTableList.KEY_LIST_ID};

        Cursor cursor = db.query(DBTableList.TABLE_NAME, columns, DBTableList.KEY_LIST_NAME + " = ?",
                wildCard, null, null, null);

        int numberOfColumnListId = cursor.getColumnIndex(DBTableList.KEY_LIST_ID);

        cursor.moveToFirst();

        long listId = cursor.getLong(numberOfColumnListId);
        cursor.close();

        return listId;
    }

    public void populateDB(){
        long categoryId = 0;
        long productId = 0;
        int productCounter = 0;
        int productNameCounter = 0;
        for(int i = 0; i < 5; i++){
            categoryId = insertNewCategory("Category " + i);
            for(int j = 0; j < 5; j++){
              productId = insertNewProduct("Product " + productCounter++, categoryId);
              for(int z = 0; z < 5; z++){
                insertNewProductName("Product name " + productNameCounter++, productId);
              }
            }
        }
    }


    //Returnrs all the categories in the database as a string.
    public String getAllCategories(){
        String result = "";
        String[] columns = {DBTableCategory.KEY_CATEGORY_NAME};
        Cursor cursor = db.query(DBTableCategory.TABLE_NAME, columns, null, null, null, null, null);

        int numberOfColumCategoryName = cursor.getColumnIndex(DBTableCategory.KEY_CATEGORY_NAME);
        cursor.moveToFirst();

        do{
           result += cursor.getString(numberOfColumCategoryName) + ", ";
        }while (cursor.moveToNext());
        cursor.close();
        return result;
    }


    //Returns all the Categories in he database as a List.
    public ArrayList<String> getAllCategoriesInList(){
        ArrayList<String> result = new ArrayList<String>();
        String[] columns = {DBTableCategory.KEY_CATEGORY_NAME};
        Cursor cursor = db.query(DBTableCategory.TABLE_NAME, columns, null, null, null, null, null);

        int numberOfColumCategoryName = cursor.getColumnIndex(DBTableCategory.KEY_CATEGORY_NAME);
        cursor.moveToFirst();
        if(cursor.getCount() != 0){
        do{
            result.add(cursor.getString(numberOfColumCategoryName));
        }while (cursor.moveToNext());
        cursor.close();
        }else {
            return null;
        }
        return result;
    }

    //Returns all the products from the database as a String.
    public String getAllProducts(){
        String result = "";
        String[] columns = {DBTableProduct.KEY_PRODUCT_NAME};
        Cursor cursor = db.query(DBTableProduct.TABLE_NAME, columns, null, null, null, null, null);

        int numberOFColumnProductName = cursor.getColumnIndex(DBTableProduct.KEY_PRODUCT_NAME);
        cursor.moveToFirst();

        do{
            result += cursor.getString(numberOFColumnProductName) + ", ";
        }while (cursor.moveToNext());
        cursor.close();
        return result;
    }


    //Returns all the products for a category as a String
    public String getAllproductsForCategory(String categoryName){
        String result = "";
        String[] wildCard = {categoryName};
                Cursor cursor = db.rawQuery("select product.name from category join product"
                + " where category.category_name = ?" + " and " +
                "category.category_id = product.category_id", wildCard);

        int numberOFColumnProductName = cursor.getColumnIndex(DBTableProduct.KEY_PRODUCT_NAME);
        cursor.moveToFirst();

        do{
            result += cursor.getString(numberOFColumnProductName) + ", ";
        }while (cursor.moveToNext());
        cursor.close();
        return result;
    }


    //Returns all the product names for a product as a List.
    public ArrayList<String> getAllProductNamesForProductInList(String product){
        ArrayList<String> result = new ArrayList<String>();
        String[] wildCard = {product};
        Cursor cursor = db.rawQuery("select product_name.product_name from product_name join product" +
                " where product.name = ? " +
                "and product.product_id = product_name.product_id", wildCard);
        int numberOFColumnProductName = cursor.getColumnIndex(DBTableProductName.KEY_PRODUCT_NAME);

        if(cursor.getCount() != 0){

            cursor.moveToFirst();

            do{
                result.add(cursor.getString(numberOFColumnProductName));
            }while (cursor.moveToNext());

        }else{
            result.add("");
           // return null;
        }
        cursor.close();
        return result;
    }


    //Returns all the products for a category as a List.
    public ArrayList<String> getAllproductsForCategoryInList(String categoryName){
        ArrayList<String> result = new ArrayList<String>();
        String[] wildCard = {categoryName};
        Cursor cursor = db.rawQuery("select product.name from category join product"
                + " where category.category_name = ?" + " and " +
                "category.category_id = product.category_id", wildCard);

        int numberOFColumnProductName = cursor.getColumnIndex(DBTableProduct.KEY_PRODUCT_NAME);

        if(cursor.getCount() != 0){

            cursor.moveToFirst();

        do{

            result.add(cursor.getString(numberOFColumnProductName));

        }while (cursor.moveToNext());

        cursor.close();

        }else{
            result.add("");
           // return null;
        }
        return result;
    }

    public ArrayList<Product> getAllListProductsForListWithProductName(String listName){
        ArrayList<Product> savedListProductsForlist = new ArrayList<Product>();

        //long listId = getListIdFromListName(listName);
        String[] wildCard = {listName};

//        String query = "select " + DBTableCategory.KEY_CATEGORY_NAME + ", " + DBTableProduct.KEY_PRODUCT_NAME + ", " +
//                DBTableProductName.KEY_PRODUCT_NAME + ", " + DBtableListProduct.KEY_LIST_PRODUCT_QUANTITY + ", " +
//                DBtableListProduct.KEY_LIST_PRODUCT_UNITS + ", " + DBtableListProduct.KEY_LIST_PRODUCT_PRICE + ", " +
//                DBtableListProduct.KEY_LIST_PRODUCT_CURRENCY + " from " + DBTableList.TABLE_NAME + " join " + DBTableCategory.TABLE_NAME + " join " +
//                DBTableProduct.TABLE_NAME + " join " + DBTableProductName.TABLE_NAME + " join " +
//                DBtableListProduct.TABLE_NAME + " where " + DBtableListProduct.KEY_PRODUCT_ID + " = -1" + " and " + DBTableList.KEY_LIST_NAME + " = ?" + " and" +
//                DBTableList.KEY_LIST_ID + " = " + DBtableListProduct.KEY_LIST_ID + " and " +
//                DBtableListProduct.KEY_PRODUCT_NAME_ID + " = " + DBTableProductName.KEY_PRODUCT_NAME_ID + " and " +
//                DBTableProductName.KEY_PRODUCT_ID + " = " + DBTableProduct.KEY_PRODUCT_ID + " and " +
//                DBTableProduct.KEY_CATEGORY_ID + " = " + DBTableCategory.KEY_CATEGORY_ID;
        String query = "select category.category_name, product.name, product_name.product_name, list_product.list_product_quantity, " +
                "list_product.list_product_units, list_product.list_product_price, list_product.list_product_currency " +
                "from list, category, product, product_name, list_product where list_product.product_id " +
                "= -1 and list.list_name = ? and list.list_id = list_product.list_id and list_product.product_name_id " +
                "= product_name.product_name_id and product_name.product_id = product.product_id and " +
                "product.category_id = category.category_id";

        Cursor cursor = db.rawQuery(query, wildCard);

        int numberOfCategoryName = cursor.getColumnIndex(DBTableCategory.KEY_CATEGORY_NAME);
        int numberOfProduct = cursor.getColumnIndex(DBTableProduct.KEY_PRODUCT_NAME);
        int numberOfProductName = cursor.getColumnIndex(DBTableProductName.KEY_PRODUCT_NAME);
        int numberOfListProductQuantity = cursor.getColumnIndex(DBtableListProduct.KEY_LIST_PRODUCT_QUANTITY);
        int numberOfListProductUnits = cursor.getColumnIndex(DBtableListProduct.KEY_LIST_PRODUCT_UNITS);
        int numberOfListProductPrice = cursor.getColumnIndex(DBtableListProduct.KEY_LIST_PRODUCT_PRICE);
        int numberOfListProductCurrency = cursor.getColumnIndex(DBtableListProduct.KEY_LIST_PRODUCT_CURRENCY);

        if(cursor.getCount() != 0){
            cursor.moveToFirst();

            do{

                savedListProductsForlist.add(new Product(cursor.getString(numberOfCategoryName), cursor.getString(numberOfProduct), cursor.getFloat(numberOfListProductPrice),
                cursor.getString(numberOfProductName), cursor.getFloat(numberOfListProductQuantity), cursor.getString(numberOfListProductUnits),
                cursor.getString(numberOfListProductCurrency)));

            }while(cursor.moveToNext());
        }
        cursor.close();
        return  savedListProductsForlist;
    }

    public ArrayList<Product> getAllListProductsForListWithProduct(String listName){
        ArrayList<Product> savedListProductsForlist = new ArrayList<Product>();

        //long listId = getListIdFromListName(listName);
        String[] wildCard = {listName};

//        String query = "select " + DBTableCategory.KEY_CATEGORY_NAME + " ," + DBTableProduct.KEY_PRODUCT_NAME + " ," +
//                DBTableProductName.KEY_PRODUCT_NAME + " ," + DBtableListProduct.KEY_LIST_PRODUCT_QUANTITY + " ," +
//                DBtableListProduct.KEY_LIST_PRODUCT_UNITS + " ," + DBtableListProduct.KEY_LIST_PRODUCT_PRICE + " ," +
//                DBtableListProduct.KEY_LIST_PRODUCT_CURRENCY + " from " + DBTableList.TABLE_NAME + " join " + DBTableCategory.TABLE_NAME + " join " +
//                DBTableProduct.TABLE_NAME + " join " + DBTableProductName.TABLE_NAME + " join " +
//                DBtableListProduct.TABLE_NAME + " where " + DBtableListProduct.KEY_PRODUCT_NAME_ID + " = -1" + " and "  + DBTableList.KEY_LIST_NAME + " = ?" + " and" +
//                DBTableList.KEY_LIST_ID + " = " + DBtableListProduct.KEY_LIST_ID + " and " +
//                DBtableListProduct.KEY_PRODUCT_ID + " = " + DBTableProduct.KEY_PRODUCT_ID + " and " +
//                DBTableProduct.KEY_CATEGORY_ID + " = " + DBTableCategory.KEY_CATEGORY_ID;

        String query = "select category.category_name, product.name, list_product.list_product_quantity, " +
                "list_product.list_product_units, list_product.list_product_price, list_product.list_product_currency " +
                "from list, category, product, list_product where" +
                " list.list_name = ? and list_product.product_name_id = -1 and list.list_id = list_product.list_id and list_product.product_id = product.product_id and " +
                "product.category_id = category.category_id";

        Cursor cursor = db.rawQuery(query, wildCard);

        int numberOfCategoryName = cursor.getColumnIndex(DBTableCategory.KEY_CATEGORY_NAME);
        int numberOfProduct = cursor.getColumnIndex(DBTableProduct.KEY_PRODUCT_NAME);
        int numberOfProductName = cursor.getColumnIndex(DBTableProductName.KEY_PRODUCT_NAME);
        int numberOfListProductQuantity = cursor.getColumnIndex(DBtableListProduct.KEY_LIST_PRODUCT_QUANTITY);
        int numberOfListProductUnits = cursor.getColumnIndex(DBtableListProduct.KEY_LIST_PRODUCT_UNITS);
        int numberOfListProductPrice = cursor.getColumnIndex(DBtableListProduct.KEY_LIST_PRODUCT_PRICE);
        int numberOfListProductCurrency = cursor.getColumnIndex(DBtableListProduct.KEY_LIST_PRODUCT_CURRENCY);

        if(cursor.getCount() != 0){
            cursor.moveToFirst();

            do{

                savedListProductsForlist.add(new Product(cursor.getString(numberOfCategoryName), cursor.getString(numberOfProduct), cursor.getFloat(numberOfListProductPrice),
                        null, cursor.getFloat(numberOfListProductQuantity), cursor.getString(numberOfListProductUnits),
                        cursor.getString(numberOfListProductCurrency)));

            }while(cursor.moveToNext());
        }
        cursor.close();
        return  savedListProductsForlist;
    }

    public ArrayList<String> getAlllistNamesInList(){
        ArrayList<String> result = new ArrayList<String>();
        Cursor cursor = db.query(DBTableList.TABLE_NAME, null,null,null,null,null,null);

        int numberOfListName = cursor.getColumnIndex(DBTableList.KEY_LIST_NAME);
        if(cursor.getCount() != 0){
        cursor.moveToFirst();
        do{

            result.add(cursor.getString(numberOfListName));

        }while (cursor.moveToNext());
        cursor.close();
        return result;
        }else{
        return null;
        }
    }

    //Delete functions

    public boolean deleteListWithName(String listName){
        String[] column = {listName};
        return db.delete(DBTableList.TABLE_NAME, DBTableList.KEY_LIST_NAME + " = ?", column) > 0;
    }

    public boolean deleteCategoryWithName(String categoryName){
        String[] column = {categoryName};
        return db.delete(DBTableCategory.TABLE_NAME, DBTableCategory.KEY_CATEGORY_NAME + " = ?", column) > 0;
    }

    public boolean deleteProductWithName(String productName){
        String[] column = {productName};
        return db.delete(DBTableProduct.TABLE_NAME, DBTableProduct.KEY_PRODUCT_NAME + " = ?", column) > 0;
    }

    public boolean deleteProducNametWithName(String productName){
        String[] column = {productName};
        return db.delete(DBTableProductName.TABLE_NAME, DBTableProductName.KEY_PRODUCT_NAME + " = ?", column) > 0;
    }

    public boolean deleteListtWithName(String listName){
        String[] column = {listName};
        return db.delete(DBTableList.TABLE_NAME, DBTableList.KEY_LIST_NAME + " = ?", column) > 0;
    }

    public boolean deleteListProducttWithId(String listProductName){
        String[] column = {listProductName};
        return db.delete(DBtableListProduct.TABLE_NAME, DBtableListProduct.KEY_LIST_ID + " = ?", column) > 0;
    }

    public boolean deleteListProducttWithProductNameId(String productNameId){
        String[] column = {productNameId};
        return db.delete(DBtableListProduct.TABLE_NAME, DBtableListProduct.KEY_PRODUCT_NAME_ID + " = ?", column) > 0;
    }

    public boolean deleteListProducttWithProductId(String productId){
        String[] column = {productId};
        return db.delete(DBtableListProduct.TABLE_NAME, DBtableListProduct.KEY_PRODUCT_ID + " = ?", column) > 0;
    }

    //TEST FUNCTIONS
    public String testListProductContentDBTable(String listName){
        String[] wildCard = {listName};
        //String[] columns = {DBtableListProduct.KEY_LIST_ID};

        Cursor cursor = db.query(DBtableListProduct.TABLE_NAME, null, DBtableListProduct.KEY_LIST_ID + " = ?",
                wildCard, null, null, null);

        int numberOfColumnListId = cursor.getColumnIndex(DBTableList.KEY_LIST_ID);

        cursor.moveToFirst();

        long listId = cursor.getLong(numberOfColumnListId);
        cursor.close();
        return null;
    }


    public String getAlllistNames(){
        String result = "";
        Cursor cursor = db.query(DBTableList.TABLE_NAME, null,null,null,null,null,null);

        int numberOfListName = cursor.getColumnIndex(DBTableList.KEY_LIST_NAME);
        cursor.moveToFirst();
        do{

            result += cursor.getString(numberOfListName) + "\n";

        }while (cursor.moveToNext());
        cursor.close();
        return result;
    }

    public ArrayList<String> getFirstCategoryInDataBase(){
        String[] wildCard = {""};
        String[] columns = {DBTableCategory.KEY_CATEGORY_NAME};

        Cursor cursor = db.query(DBTableCategory.TABLE_NAME, columns, DBTableCategory.KEY_CATEGORY_NAME + " != ?",
                wildCard, null, null, null);

        int numberOfColumn = cursor.getColumnIndex(DBTableCategory.KEY_CATEGORY_NAME);

        cursor.moveToFirst();

        String categoryName = cursor.getString(numberOfColumn);
        ArrayList<String> categories = getAllproductsForCategoryInList(categoryName);
        cursor.close();

        return categories;
    }

    public String getFirstCategoryNameInDataBase(){
        String[] wildCard = {""};
        String[] columns = {DBTableCategory.KEY_CATEGORY_NAME};

        Cursor cursor = db.query(DBTableCategory.TABLE_NAME, columns, DBTableCategory.KEY_CATEGORY_NAME + " != ?",
                wildCard, null, null, null);

        int numberOfColumn = cursor.getColumnIndex(DBTableCategory.KEY_CATEGORY_NAME);

        cursor.moveToFirst();

        String categoryName = cursor.getString(numberOfColumn);

        cursor.close();

        return categoryName;
    }

    public String getAllListProducts(){
        String result = "";
        Cursor cursor = db.query(DBtableListProduct.TABLE_NAME, null,null,null,null,null,null);

        int numberOfListId = cursor.getColumnIndex(DBtableListProduct.KEY_LIST_ID);
        int numberOfProductId = cursor.getColumnIndex(DBtableListProduct.KEY_PRODUCT_ID);
        int numberOfProductNameId = cursor.getColumnIndex(DBtableListProduct.KEY_PRODUCT_NAME_ID);
        int numberOfQuantity = cursor.getColumnIndex(DBtableListProduct.KEY_LIST_PRODUCT_QUANTITY);
        int numberofUnits = cursor.getColumnIndex(DBtableListProduct.KEY_LIST_PRODUCT_UNITS);
        int numberOfPrice = cursor.getColumnIndex(DBtableListProduct.KEY_LIST_PRODUCT_PRICE);
        int numberOfCurrency = cursor.getColumnIndex(DBtableListProduct.KEY_LIST_PRODUCT_CURRENCY);
        int numberOfNotes = cursor.getColumnIndex(DBtableListProduct.KEY_LIST_PRODUCT_NOTES);
        cursor.moveToFirst();
        do{

            result += "LIST ID: " + cursor.getLong(numberOfListId) + "PRODUCT ID: " + cursor.getLong(numberOfProductId) +
                    " PRODUCT NAME ID :" + cursor.getLong(numberOfProductNameId) + " QUANTITY :" +
                    cursor.getFloat(numberOfQuantity)+ "UNITS :" + cursor.getString(numberofUnits)+
                    "PRICE :" + cursor.getFloat(numberOfPrice) + "CURRENCY :" + cursor.getString(numberOfCurrency)
                    + "NOTES :"+ cursor.getString(numberOfNotes) + "\n";

        }while (cursor.moveToNext());
        cursor.close();
        return result;
    }

}

package com.fatzombie.cooklist;

/**
 * Created with IntelliJ IDEA.
 * User: NTS Two
 * Date: 11/19/13
 * Time: 10:55 AM
 * To change this template use File | Settings | File Templates.
 */
public class DBtableListProduct {
    public static final String TABLE_NAME = "list_product";
    public static final String KEY_LIST_PRODUCT_ID = "list_product_id";
    public static final String KEY_PRODUCT_NAME_ID = "product_name_id";
    public static final String KEY_PRODUCT_ID = "product_id";
    public static final String KEY_LIST_ID = "list_id";
    public static final String KEY_LIST_PRODUCT_QUANTITY = "list_product_quantity";
    public static final String KEY_LIST_PRODUCT_UNITS = "list_product_units";
    public static final String KEY_LIST_PRODUCT_PRICE = "list_product_price";
    public static final String KEY_LIST_PRODUCT_CURRENCY = "list_product_currency";
    public static final String KEY_LIST_PRODUCT_NOTES = "list_product_notes";
    public static final String CREATE_TABLE = "create table " + TABLE_NAME +
            " (" + KEY_LIST_PRODUCT_ID + " integer primary key autoincrement, " +
            KEY_PRODUCT_NAME_ID + " integer, " +
            KEY_PRODUCT_ID + " integer, " +
            KEY_LIST_ID + " integer, " +
            KEY_LIST_PRODUCT_PRICE + " real, " +
            KEY_LIST_PRODUCT_QUANTITY + " real," +
            KEY_LIST_PRODUCT_UNITS + " text, "  +
            KEY_LIST_PRODUCT_CURRENCY + " text, " +
            KEY_LIST_PRODUCT_NOTES + " text);";


}

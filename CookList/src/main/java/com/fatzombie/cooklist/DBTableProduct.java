package com.fatzombie.cooklist;

/**
 * Created with IntelliJ IDEA.
 * User: anton
 * Date: 11/16/13
 * Time: 1:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class DBTableProduct {

    public static final String TABLE_NAME = "product";
    public static final String KEY_PRODUCT_ID = "product_id";
    public static final String KEY_PRODUCT_NAME = "name";
    public static final String KEY_CATEGORY_ID = "category_id";
    public static final String CREATE_TABLE = "create table " + TABLE_NAME +
            " (" + KEY_PRODUCT_ID + " integer primary key autoincrement, " +
            KEY_PRODUCT_NAME + " text, " +
            KEY_CATEGORY_ID + " integer);";



}

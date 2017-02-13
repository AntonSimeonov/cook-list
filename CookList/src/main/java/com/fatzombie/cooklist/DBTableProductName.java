package com.fatzombie.cooklist;

/**
 * Created with IntelliJ IDEA.
 * User: NTS Two
 * Date: 11/18/13
 * Time: 3:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class DBTableProductName {

    public static final String TABLE_NAME = "product_name";
    public static final String KEY_PRODUCT_NAME_ID = "product_name_id";
    public static final String KEY_PRODUCT_NAME = "product_name";
    public static final String KEY_PRODUCT_ID = "product_id";
    public static final String CREATE_TABLE = "create table " + TABLE_NAME +
            " (" + KEY_PRODUCT_NAME_ID + " integer primary key autoincrement, "  +
            KEY_PRODUCT_NAME + " text, " +
            KEY_PRODUCT_ID + " integer);";

}

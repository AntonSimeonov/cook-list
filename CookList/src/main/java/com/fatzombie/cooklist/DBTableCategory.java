package com.fatzombie.cooklist;

/**
 * Created with IntelliJ IDEA.
 * User: anton
 * Date: 11/16/13
 * Time: 1:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class DBTableCategory {

    public static final String TABLE_NAME = "category";
    public static final String KEY_CATEGORY_ID = "category_id";
    public static final String KEY_CATEGORY_NAME = "category_name";
    public static final String CREATE_TABLE = "create table " + TABLE_NAME +
            " (" + KEY_CATEGORY_ID + " integer primary key autoincrement, " +
            KEY_CATEGORY_NAME + " text);";


}

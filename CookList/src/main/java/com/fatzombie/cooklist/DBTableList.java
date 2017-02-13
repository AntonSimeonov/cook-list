package com.fatzombie.cooklist;

/**
 * Created with IntelliJ IDEA.
 * User: NTS Two
 * Date: 11/19/13
 * Time: 11:32 AM
 * To change this template use File | Settings | File Templates.
 */
public class DBTableList {

    public static final String TABLE_NAME = "list";
    public static final String KEY_LIST_ID = "list_id";
    public static final String KEY_LIST_NAME = "list_name";
    public static final String KEY_LIST_NOTES = "list_notes";
    public static final String CREATE_TABLE = "create table " + TABLE_NAME +
            " (" + KEY_LIST_ID + " integer primary key autoincrement, " +
            KEY_LIST_NAME + " text," +
            KEY_LIST_NOTES + " text);";

}

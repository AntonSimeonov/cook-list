package com.fatzombie.cooklist;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;

/**
 * Created with IntelliJ IDEA.
 * User: anton
 * Date: 12/28/13
 * Time: 2:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class Test2 extends Activity {
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_2);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
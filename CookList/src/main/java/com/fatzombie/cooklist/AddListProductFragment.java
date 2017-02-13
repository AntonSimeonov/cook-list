package com.fatzombie.cooklist;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by anton on 1/11/14.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class AddListProductFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layOut = inflater.inflate(R.layout.add_list_product_fragment,container, false);
        return layOut;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
}

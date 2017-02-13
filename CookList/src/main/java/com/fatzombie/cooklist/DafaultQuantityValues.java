package com.fatzombie.cooklist;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created with IntelliJ IDEA.
 * User: NTS Two
 * Date: 12/12/13
 * Time: 11:24 AM
 * To change this template use File | Settings | File Templates.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class DafaultQuantityValues extends Fragment implements View.OnClickListener{

    private GetDefaultQuantityValues listener;
    TextView defaultQuantity0;
    TextView defaultQuantity1;
    TextView defaultQuantity2;
    TextView defaultQuantity3;
    TextView defaultQuantity4;
    TextView defaultQuantity010;
    TextView defaultQuantity025;
    TextView defaultQuantity050;
    TextView defaultQuantity075;
    TextView defaultQuantityManualy;

    @Override
    public void onClick(View view) {
        //To change body of implemented methods use File | Settings | File Templates.
        String defaultQuantityValue = "0";
        switch (view.getId()){
            case R.id.tvDefaultQuantity0:
                defaultQuantityValue = "0";
                break;
            case R.id.tvDefaultQuantity1:
                defaultQuantityValue = "1";
                break;
            case R.id.tvDefaultQuantity2:
                defaultQuantityValue = "2";
                break;
            case R.id.tvDefaultQuantity3:
                defaultQuantityValue = "3";
                break;
            case R.id.tvDefaultQuantity4:
                defaultQuantityValue = "4";
                break;
            case R.id.tvDefaultQuantityManualy:
                defaultQuantityValue = "-25";
                break;
            case R.id.tvDefaultQuantity010:
                defaultQuantityValue = "0.100";
                break;
            case R.id.tvDefaultQuantity025:
                defaultQuantityValue = "0.250";
                break;
            case R.id.tvDefaultQuantity050:
                defaultQuantityValue = "0.500";
                break;
            case R.id.tvDefaultQuantity075:
                defaultQuantityValue = "0.750";
                break;
            default: defaultQuantityValue = "0";
                break;
        }

         listener.getDefaultQuantityValue(defaultQuantityValue);



    }

    public interface GetDefaultQuantityValues{
         void getDefaultQuantityValue(String quantity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View layOut = inflater.inflate(R.layout.default_quantity_values2,container, false);

        defaultQuantity0 = (TextView) layOut.findViewById(R.id.tvDefaultQuantity0);
        defaultQuantity1 = (TextView) layOut.findViewById(R.id.tvDefaultQuantity1);
        defaultQuantity2 = (TextView) layOut.findViewById(R.id.tvDefaultQuantity2);
        defaultQuantity3 = (TextView) layOut.findViewById(R.id.tvDefaultQuantity3);
        defaultQuantity4 = (TextView) layOut.findViewById(R.id.tvDefaultQuantity4);

        defaultQuantity010 = (TextView) layOut.findViewById(R.id.tvDefaultQuantity010);
        defaultQuantity025 = (TextView) layOut.findViewById(R.id.tvDefaultQuantity025);
        defaultQuantity050 = (TextView) layOut.findViewById(R.id.tvDefaultQuantity050);
        defaultQuantity075 = (TextView) layOut.findViewById(R.id.tvDefaultQuantity075);

        defaultQuantityManualy = (TextView) layOut.findViewById(R.id.tvDefaultQuantityManualy);
        Typeface fontThin = Typeface.createFromAsset(getActivity().getAssets(), "Roboto-Thin.ttf");
        defaultQuantity0.setOnClickListener(this);
        defaultQuantity0.setTypeface(fontThin);
        defaultQuantity1.setOnClickListener(this);
        defaultQuantity1.setTypeface(fontThin);
        defaultQuantity2.setOnClickListener(this);
        defaultQuantity2.setTypeface(fontThin);
        defaultQuantity3.setOnClickListener(this);
        defaultQuantity3.setTypeface(fontThin);
        defaultQuantity4.setOnClickListener(this);
        defaultQuantity4.setTypeface(fontThin);

        defaultQuantity010.setOnClickListener(this);
        defaultQuantity010.setTypeface(fontThin);
        defaultQuantity025.setOnClickListener(this);
        defaultQuantity025.setTypeface(fontThin);
        defaultQuantity050.setOnClickListener(this);
        defaultQuantity050.setTypeface(fontThin);
        defaultQuantity075.setOnClickListener(this);
        defaultQuantity075.setTypeface(fontThin);

        defaultQuantityManualy.setOnClickListener(this);
        defaultQuantityManualy.setTypeface(fontThin);

        return layOut;    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);    //To change body of overridden methods use File | Settings | File Templates.

        try{

            listener = (GetDefaultQuantityValues) activity;

        }catch (Exception ex){

        }

    }

    @Override
    public void onDetach() {
        super.onDetach();    //To change body of overridden methods use File | Settings | File Templates.


    }
}

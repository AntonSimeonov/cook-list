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

/**
 * Created with IntelliJ IDEA.
 * User: anton
 * Date: 12/14/13
 * Time: 1:19 PM
 * To change this template use File | Settings | File Templates.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ProductInformationFragment extends Fragment implements View.OnClickListener{

    IformationFragment listener;

    TextView quantityOfSelectedProduct;
    TextView unitOfSelectedProduct;

    TextView none;
    TextView kg;
    TextView gr;
    TextView ltr;
    TextView mLtr;
    TextView oz;
    TextView lb;
    TextView defoultUnitManualy;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View layOut = inflater.inflate(R.layout.product_information_fragment,container, false);
//        quantityOfSelectedProduct = (TextView) layOut.findViewById(R.id.tvProductInfoFragmentQuantity);
//        unitOfSelectedProduct = (TextView) layOut.findViewById(R.id.tvProductInfoFragmentUnits);
        none = (TextView) layOut.findViewById(R.id.tvProductInfoFragmentUnitNone);
        kg = (TextView) layOut.findViewById(R.id.tvProductInfoFragmentUnitKg);
        gr = (TextView) layOut.findViewById(R.id.tvProductInfoFragmentUnitGr);
        ltr = (TextView) layOut.findViewById(R.id.tvProductInfoFragmentUnitLitr);
        mLtr = (TextView) layOut.findViewById(R.id.tvProductInfoFragmentUnitMlitr);
        oz = (TextView) layOut.findViewById(R.id.tvProductInfoFragmentUnitOz);
        lb = (TextView) layOut.findViewById(R.id.tvProductInfoFragmentUnitLb);
        defoultUnitManualy = (TextView) layOut.findViewById(R.id.tvProductInfoFragmentUnitKeyBoard);
        Typeface fontThin = Typeface.createFromAsset(getActivity().getAssets(), "Roboto-Thin.ttf");
        none.setOnClickListener(this);
        none.setTypeface(fontThin);
        kg.setOnClickListener(this);
        kg.setTypeface(fontThin);
        gr.setOnClickListener(this);
        gr.setTypeface(fontThin);
        ltr.setOnClickListener(this);
        ltr.setTypeface(fontThin);
        mLtr.setOnClickListener(this);
        mLtr.setTypeface(fontThin);
        oz.setOnClickListener(this);
        oz.setTypeface(fontThin);
        lb.setOnClickListener(this);
        lb.setTypeface(fontThin);
        defoultUnitManualy.setOnClickListener(this);
        defoultUnitManualy.setTypeface(fontThin);
        return layOut;

    }

    @Override
    public void onClick(View view) {
        String defoultUnit = "";

        switch (view.getId()){
            case R.id.tvProductInfoFragmentUnitNone:
                defoultUnit = "";
                break;
            case R.id.tvProductInfoFragmentUnitKg:
                defoultUnit = getActivity().getResources().getString(R.string.kg);
                break;
            case R.id.tvProductInfoFragmentUnitGr:
                defoultUnit = getActivity().getResources().getString(R.string.grams);
                break;
            case R.id.tvProductInfoFragmentUnitLitr:
                defoultUnit = getActivity().getResources().getString(R.string.litr);
                break;
            case R.id.tvProductInfoFragmentUnitMlitr:
                defoultUnit = getActivity().getResources().getString(R.string.mlitr);
                break;
            case R.id.tvProductInfoFragmentUnitOz:
                defoultUnit = getActivity().getResources().getString(R.string.ounce);
                break;
            case R.id.tvProductInfoFragmentUnitLb:
                defoultUnit = getActivity().getResources().getString(R.string.pound);
                break;
            case R.id.tvProductInfoFragmentUnitKeyBoard:
                defoultUnit = getActivity().getResources().getString(R.string.keyboard);
                break;
        }
        listener.getDefoultUnits(defoultUnit);
    }

    public  interface IformationFragment{
        void getDefoultUnits(String units);
    }

    public void updateSelectedProductQuantityFragment(float quantity){
        quantityOfSelectedProduct.setText("" + quantity);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);    //To change body of overridden methods use File | Settings | File Templates.

        try{

            listener = (IformationFragment) activity;

        }catch (Exception ex){

        }

    }
}

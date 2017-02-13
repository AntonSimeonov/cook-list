package com.fatzombie.cooklist;

import android.annotation.TargetApi;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by anton on 1/18/14.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ProductShadowBuilder extends View.DragShadowBuilder {

    private static View shadow;

    public ProductShadowBuilder(View v){
        super(v);
        shadow = v;
    }

    @Override
    public void onProvideShadowMetrics(Point shadowSize, Point shadowTouchPoint) {
       // super.onProvideShadowMetrics(shadowSize, shadowTouchPoint);

        int width, height;

        // Sets the width of the shadow to half the width of the original View
        width = getView().getWidth();

        // Sets the height of the shadow to half the height of the original View
        height = getView().getHeight();

//        ImageView arrow = (ImageView) getView().findViewById(R.id.ivCustomSimpleGridProductAdapter);
//        arrow.setImageResource(R.drawable.product_arrow_blue);

        // The drag shadow is a ColorDrawable. This sets its dimensions to be the same as the
        // Canvas that the system will provide. As a result, the drag shadow will fill the
        // Canvas.
       // shadow.setBounds(0, 0, width, height);

        // Sets the size parameter's width and height values. These get back to the system
        // through the size parameter.
        shadowSize.set(width * 2, height * 2);

        // Sets the touch point's position to be in the middle of the drag shadow
        shadowTouchPoint.set(width / 2, height);

    }

    @Override
    public void onDrawShadow(Canvas canvas) {
        //super.onDrawShadow(canvas);
        ImageView arrow = (ImageView) getView().findViewById(R.id.ivCustomSimpleGridProductAdapter);
        arrow.setImageResource(R.drawable.product_arrow_blue);
        shadow.draw(canvas);
    }
}

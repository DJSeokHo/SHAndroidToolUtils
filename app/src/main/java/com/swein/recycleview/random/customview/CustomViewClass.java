package com.swein.recycleview.random.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.widget.TextView;

/**
 * Created by seokho on 06/03/2017.
 */

public class CustomViewClass extends TextView {

    private Context context;

    public CustomViewClass( Context context ) {
        super( context );
        this.context = context;
    }


    @Override
    protected void onDraw( Canvas canvas ) {
        super.onDraw( canvas );


    }
}

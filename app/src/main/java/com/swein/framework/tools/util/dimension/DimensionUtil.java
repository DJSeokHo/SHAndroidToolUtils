package com.swein.framework.tools.util.dimension;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by seokho on 22/01/2018.
 */

/**
 * dpi - > px
 */
public class DimensionUtil {
    public static int dimension( int value, Context context ) {
        if(context == null) {
            return -1;
        }

        return (int) TypedValue.applyDimension( TypedValue.COMPLEX_UNIT_DIP, value, context.getResources()
                .getDisplayMetrics() );
    }
}

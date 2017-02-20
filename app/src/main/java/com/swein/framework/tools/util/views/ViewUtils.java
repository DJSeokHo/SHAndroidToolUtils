package com.swein.framework.tools.util.views;

import android.view.View;

/**
 * Created by seokho on 25/12/2016.
 */

public class ViewUtils {

    public static void viewSetClickListener(View v, View.OnClickListener onClickListener) {
        v.setOnClickListener(onClickListener);
    }

}

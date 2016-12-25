package com.swein.framework.tools.util.views;

import android.view.View;

/**
 * Created by seokho on 25/12/2016.
 */

public class ViewUtils {

    public static void viewSetClickListener(View v, final Runnable runnable) {

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runnable.run();
            }
        });
    }

}

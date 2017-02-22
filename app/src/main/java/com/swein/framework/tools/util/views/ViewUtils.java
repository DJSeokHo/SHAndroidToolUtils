package com.swein.framework.tools.util.views;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.swein.shandroidtoolutils.R;

/**
 * Created by seokho on 25/12/2016.
 */

public class ViewUtils {

    public static void viewSetClickListener(View v, View.OnClickListener onClickListener) {
        v.setOnClickListener(onClickListener);
    }

    public static void shakeView(Context context, View view) {
        Animation shake = AnimationUtils.loadAnimation(context, R.anim.shake);
        view.startAnimation( shake );
    }

}

package com.swein.framework.tools.util.views.textview;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.widget.TextView;

/**
 * Created by seokho on 18/08/2017.
 */

public class TextViewUtils {



    public static void getSpannableString(Context context, String string, TextView textView) {
        SpannableString spannableString = new SpannableString(string);


        spannableString.setSpan(new RelativeSizeSpan(1.0f), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new RelativeSizeSpan(1.0f), 2, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        textView.setText(spannableString, TextView.BufferType.SPANNABLE);
        textView.setText(string);
    }

    public static void getSpannableString(Context context, int resourceId, TextView textView) {
        SpannableString spannableString = new SpannableString(context.getString(resourceId));

        spannableString.setSpan(new RelativeSizeSpan(1.0f), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new RelativeSizeSpan(1.0f), 2, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView.setText(spannableString, TextView.BufferType.SPANNABLE);
        textView.setText(context.getString(resourceId));
    }



}

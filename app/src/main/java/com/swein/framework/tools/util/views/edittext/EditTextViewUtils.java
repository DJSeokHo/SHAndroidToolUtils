package com.swein.framework.tools.util.views.edittext;

import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

/**
 * Created by seokho on 15/02/2017.
 */

public class EditTextViewUtils {

    public static void editTextViewAddTextWatcher(EditText editText, TextWatcher textWatcher) {
        editText.addTextChangedListener(textWatcher);
    }

    public static boolean isEditTextViewHasFocus(EditText editText) {

        if(editText.hasFocus()) {
            return true;
        }
        else {
            return false;
        }
    }

    public static void editTextViewSetFocusChangeListener(EditText editText, View.OnFocusChangeListener onFocusChangeListener) {
        editText.setOnFocusChangeListener(onFocusChangeListener);
    }

}

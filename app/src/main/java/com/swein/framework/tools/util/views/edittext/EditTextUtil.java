package com.swein.framework.tools.util.views.edittext;

import android.widget.EditText;

public class EditTextUtil {

    public static void setEditTextSelectionToEnd(EditText editText) {
        editText.setSelection(editText.getText().toString().trim().length());
    }
}

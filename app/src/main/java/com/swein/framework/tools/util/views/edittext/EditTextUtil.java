package com.swein.framework.tools.util.views.edittext;

import android.text.InputFilter;
import android.text.Spanned;
import android.widget.EditText;

import com.swein.framework.tools.util.debug.log.ILog;

import java.util.regex.Pattern;

public class EditTextUtil {

    public final static String KO_EN_NUM_FILTER = "^[a-zA-Z0-9ㄱ-ㅎㅏ-ㅣ가-힣]+$";

    public static void setEditTextSelectionToEnd(EditText editText) {
        editText.setSelection(editText.getText().toString().trim().length());
    }

    public static void setFilter(EditText editText) {
        editText.setFilters(new InputFilter[]{filter});
    }

    public static boolean checkFilter(String nickname) {
        Pattern ko = Pattern.compile(KO_EN_NUM_FILTER);
        ILog.iLogDebug("", nickname);
        if (ko.matcher(nickname).matches()) {
            return true;
        }
        else {
            return false;
        }
    }

    private static InputFilter filter = new InputFilter() {
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            Pattern ko = Pattern.compile(KO_EN_NUM_FILTER);
            ILog.iLogDebug("", source);
            if (ko.matcher(source).matches()) {

                return source;
            }
            else {

                return "";
            }
        }
    };
}

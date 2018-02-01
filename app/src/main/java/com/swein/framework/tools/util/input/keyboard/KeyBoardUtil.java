package com.swein.framework.tools.util.input.keyboard;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by seokho on 07/02/2017.
 */

public class KeyBoardUtil {

    public static void dismissKeyboard( Activity activity ) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if(view == null) {
            view = new View(activity);
        }
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void dismissKeyboardForView( View focusView ) {
        if (focusView == null) {
            return;
        }

        dismissKeyboard( (Activity) focusView.getContext() );
    }

    public static void clearFocusOnView(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService( Context.INPUT_METHOD_SERVICE );
        imm.hideSoftInputFromWindow( v.getWindowToken() , 0 );
        v.setFocusable( false );
        v.setFocusableInTouchMode( true );
    }

    public static void showKeyboardForView( View view ) {
        if (view == null) return;

        view.requestFocus();
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService( Context.INPUT_METHOD_SERVICE );
        imm.showSoftInput( view , InputMethodManager.SHOW_FORCED );
    }

    public static boolean isSHowKeyboard(Context context, View v) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE);
        if (imm.hideSoftInputFromWindow(v.getWindowToken(), 0)) {
            imm.showSoftInput(v, 0);

            //soft keyboard shown
            return true;
        } else {

            //soft keyboard dismissed
            return false;
        }
    }

}

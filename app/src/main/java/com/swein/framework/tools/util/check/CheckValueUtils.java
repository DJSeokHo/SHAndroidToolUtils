package com.swein.framework.tools.util.check;

/**
 * Created by seokho on 03/01/2017.
 */

public class CheckValueUtils {

    public static boolean checkIsObjectNull(Object object) {
        if(null == object) {
            return true;
        }
        return false;
    }

    public static boolean checkIsStringBlank(String string) {
        if(string.equals("")) {
            return true;
        }
        return false;
    }

    public static boolean checkIsIntegerZero(int i) {
        if(0 == i) {
            return true;
        }
        return false;
    }

    public static boolean checkIsFloatZero(float f) {
        if(0.0 == f) {
            return true;
        }
        return false;
    }

    public static boolean checkIsDoubleZero(double d) {
        if(0.0 == d) {
            return true;
        }
        return false;
    }

}

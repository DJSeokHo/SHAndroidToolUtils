package com.swein.data.singleton.key;

/**
 * Created by seokho on 31/12/2016.
 */

public class KeyData {

    private KeyData() {}

    private static KeyData instance = new KeyData();

    public static KeyData getInstance() {
        return instance;
    }

    public final static String BUNDLE_TRANSMIT_STRING_VALUE = "BUNDLE_TRANSMIT_STRING_VALUE";
    public final static String ACTIVITY_RESULT_STRING_VALUE = "ACTIVITY_RESULT_STRING_VALUE";

}

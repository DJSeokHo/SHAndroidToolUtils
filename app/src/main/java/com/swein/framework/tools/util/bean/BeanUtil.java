package com.swein.framework.tools.util.bean;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class BeanUtil {

    private final static String TAG = "BeanUtil";

    public static boolean isBeanUsable(Object object, CheckOption checkOption) {

        try {

            if(object != null) {

                if(checkOption == null) {
                    return true;
                }

                if(checkOption.getCheckOption().isEmpty()) {
                    return true;
                }

                HashMap<String, String> option = checkOption.getCheckOption();

                Field[] fields = object.getClass().getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);

                    for (Map.Entry<String, String> entry : option.entrySet()) {

                        if (!checkFieldValue(field, entry.getKey(), entry.getValue(), object)) {
                            // can not use
                            return false;
                        }
                    }
                }

                // can use
                return true;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        // can not use
        return false;
    }

    private static boolean checkFieldValue(Field field, String wishedFieldName, String wishedFieldValue, Object object) throws IllegalAccessException {

        if(field.getName().equals(wishedFieldName)) {

            return field.get(object).toString().equals(wishedFieldValue);
        }

        return true;
    }

}

package com.swein.framework.tools.reflect.getclassparameter;

import java.lang.reflect.Field;

public class GetClassParameterDemo {

    public GetClassParameterDemo(Object object) {

        Class c = object.getClass();

        Field[] publicFields = c.getFields();

        for(Field publicField : publicFields) {
            Class fieldType = publicField.getType();
            System.out.println(fieldType.getName() + " " + publicField.getName());
        }


        Field[] allFields = c.getDeclaredFields();

        for(Field allField : allFields) {
            Class fieldType = allField.getType();
            System.out.println(fieldType.getName() + " " + allField.getName());
        }

    }
}

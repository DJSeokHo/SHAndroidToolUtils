package com.swein.framework.template.reflect;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class ReflectMainDemo {

    public static void main(String[] args) {

//        new ClassDemo();

//        new DynamicLoadClassDemo("SubClass");
//        new DynamicLoadClassDemo("SubOneClass");

//        String string = "hello";
//        new GetClassMethodDemo(string);

//        String string = "hello";
//        new GetClassParameterDemo(string);

//        new UseMethodDemo();

        ArrayList<String> list = new ArrayList<>();
        list.add("haha");

        Class c = list.getClass();

        try {

            Method method = c.getDeclaredMethod("add", Object.class);
            method.invoke(list, 200);

            System.out.println(list.size());
            System.out.println(list);

            for(Object object : list) {
                System.out.println(object.toString() + " " + object.getClass().getName());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

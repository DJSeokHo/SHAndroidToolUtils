package com.swein.framework.template.reflect.dynamicloadclassdemo;

import com.swein.framework.template.reflect.dynamicloadclassdemo.subclass.PrintAble;

public class DynamicLoadClassDemo {

    public DynamicLoadClassDemo(String className) {

        Class c = null;
        try {
            /* dynamic load class */
            c = Class.forName("com.swein.framework.template.reflect.dynamicloadclassdemo.subclass." + className);

            /* create new instance */
            PrintAble pa = (PrintAble) c.newInstance();
            pa.print();
        }
        catch (Exception e) {
            e.printStackTrace();
        }



    }
}

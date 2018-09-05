package com.swein.framework.template.reflect.usemethod;

import java.lang.reflect.Method;

/**
 * method.invoke(...)
 */
public class UseMethodDemo {

    public UseMethodDemo() {

        Sample sample = new Sample();

        try {

            Class c = sample.getClass();

            Method method = c.getDeclaredMethod("print", int.class, int.class);
            method.invoke(sample, 1, 2);

            Method method1 = c.getDeclaredMethod("print", String.class, String.class);
            method1.invoke(sample, "Hello", "Hello");

            Method method2 = c.getDeclaredMethod("print");
            method2.invoke(sample);
        }
        catch (Exception e) {
            e.printStackTrace();
        }


    }
}

class Sample {

    public void print() {
        System.out.println("Hello");
    }

    public void print(int a, int b) {
        System.out.println(a + b);
    }

    public void print(String a, String b) {
        System.out.println(a.toUpperCase() + " " + b.toLowerCase());
    }

}
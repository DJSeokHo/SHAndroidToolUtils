package com.swein.framework.tools.reflect.classdemo;

public class ClassDemo {

    public ClassDemo() {

        Class c1 = Foo.class;

        Foo foo1 = new Foo();
        Class c2 = foo1.getClass();

        System.out.println(c1 == c2);

        Class c3 = null;

        try {
            c3 = Class.forName("com.swein.framework.tools.reflect.classdemo.Foo");
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println(c2 == c3);

        Foo foo2 = null;
        try {
            /* to create a new instance */
            foo2 = (Foo) c1.newInstance();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(c3 == foo2.getClass());

        foo2.print();

    }
}

class Foo{

    void print() {
        System.out.println(this.getClass().getSimpleName());
    }

}
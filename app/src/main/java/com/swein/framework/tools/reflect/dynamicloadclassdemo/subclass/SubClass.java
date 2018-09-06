package com.swein.framework.tools.reflect.dynamicloadclassdemo.subclass;

public class SubClass implements PrintAble {

    @Override
    public void print() {
        System.out.println("Run " + this.getClass().getSimpleName());
    }
}

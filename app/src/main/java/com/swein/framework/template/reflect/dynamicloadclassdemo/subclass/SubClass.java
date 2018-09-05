package com.swein.framework.template.reflect.dynamicloadclassdemo.subclass;

public class SubClass implements PrintAble {

    @Override
    public void print() {
        System.out.println("Run " + this.getClass().getSimpleName());
    }
}

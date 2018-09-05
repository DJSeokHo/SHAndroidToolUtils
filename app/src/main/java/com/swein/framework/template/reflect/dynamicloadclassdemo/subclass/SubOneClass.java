package com.swein.framework.template.reflect.dynamicloadclassdemo.subclass;

public class SubOneClass implements PrintAble{

    @Override
    public void print() {
        System.out.println("Run " + this.getClass().getSimpleName());
    }
}

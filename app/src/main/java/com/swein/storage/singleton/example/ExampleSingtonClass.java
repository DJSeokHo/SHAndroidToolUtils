package com.swein.storage.singleton.example;

/**
 * Created by seokho on 16/11/2016.
 */

public class ExampleSingtonClass {

    private String string = "";

    private static ExampleSingtonClass instance = new ExampleSingtonClass();

    public static ExampleSingtonClass getInstance() {
        return instance;
    }

    public void setmString(String string) {
        this.string = string;
    }

    public String getmString() {
        return this.string;
    }


    //use this when destroy if you need clear
    public void clearStringParam() {
        this.string = "";
    }

}

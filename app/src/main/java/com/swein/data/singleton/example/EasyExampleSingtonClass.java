package com.swein.data.singleton.example;

/**
 * Created by seokho on 16/11/2016.
 */

public class EasyExampleSingtonClass {

    private String string = "";

    private EasyExampleSingtonClass() {}

    private static EasyExampleSingtonClass instance = new EasyExampleSingtonClass();

    public static EasyExampleSingtonClass getInstance() {

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

package com.swein.data.singleton.example;

/**
 * Created by seokho on 16/11/2016.
 */

public class ExampleSingtonClass {

    private String string = "";



    private ExampleSingtonClass() {}

    private static ExampleSingtonClass instance = null;

    private static Object obj= new Object();

    public static ExampleSingtonClass getInstance() {

        if(null == instance){

            synchronized(ExampleSingtonClass.class){

                if(null == instance){

                    instance = new ExampleSingtonClass();

                }
            }
        }

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

package com.swein.shandroidtoolutils.example;

/**
 * Created by seokho on 16/11/2016.
 */

public class ExampleSingtonClass {

//    private boolean isMyListFragmentShow = false;

    private static ExampleSingtonClass instance = new ExampleSingtonClass();

    public static ExampleSingtonClass getInstance() {
        return instance;
    }

//    public void setIsMyListFragmentShow(boolean isMyListFragmentShow) {
//        this.isMyListFragmentShow = isMyListFragmentShow;
//    }
//
//    public boolean getIsMyListFragmentShow() {
//        return this.isMyListFragmentShow;
//    }

    /**
     * use this when destroy
     */
    public void clearParam() {

    }

}

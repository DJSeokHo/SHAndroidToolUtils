package com.swein.framework.module.easyscreenrecord.data.singleton;

import android.util.DisplayMetrics;

/**
 * Created by seokho on 02/02/2017.
 */

public class DataCenter  {

    //record mode status
    private boolean recordModeOn = false;
    private DisplayMetrics metrics;

    private DataCenter() {}

    private static DataCenter instance = null;

    private static Object obj= new Object();

    public static DataCenter getInstance() {

        if(null == instance){

            synchronized(DataCenter.class){

                if(null == instance){

                    instance = new DataCenter();

                }
            }
        }

        return instance;
    }



    public void setRecordModeOn(boolean recordModeOn) {
        this.recordModeOn = recordModeOn;
    }

    public boolean getIsRecordModeOn() {
        return this.recordModeOn;
    }

    public void setDisplayMetrics(DisplayMetrics metrics) {
        this.metrics = metrics;
    }

    public DisplayMetrics getDisplayMetrics() {
        return this.metrics;
    }

    //use this when destroy if you need clear
    public void clearStringParam() {
        this.recordModeOn = false;
    }

}

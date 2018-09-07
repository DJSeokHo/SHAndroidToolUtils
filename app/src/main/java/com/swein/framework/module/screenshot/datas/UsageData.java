package com.swein.framework.module.screenshot.datas;

/**
 * Created by seokho on 8/26/16.
 */
public class UsageData {

    private String packageName;
    private long lastUsedTime;

    public UsageData() {
        this.packageName = "";
        this.lastUsedTime = 0;
    }

    public UsageData(String packageName, long lastUsedTime) {
        this.packageName = packageName;
        this.lastUsedTime = lastUsedTime;
    }

    public void setPackageName(String packageName){
        this.packageName = packageName;
    }
    public String getPackageName(){
        return this.packageName;
    }

    public void setLastUsedTime(long lastUsedTime){
        this.lastUsedTime = lastUsedTime;
    }
    public long getLastUsedTime(){
        return this.lastUsedTime;
    }
}

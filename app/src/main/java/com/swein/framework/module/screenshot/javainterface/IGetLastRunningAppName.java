package com.swein.framework.module.screenshot.javainterface;

public interface IGetLastRunningAppName {

    String getTopAppPackageName();

    String getAppNameByPackageName(String packageName);

}

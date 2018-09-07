package com.swein.framework.module.screenshot.javainterface;

import android.net.Uri;

public interface IGetScreenShotsInfo extends IGetLastRunningAppName{

    void doScreenCapture();

    void checkImageMode(Uri uri);

    void getPerMarshmallowCaptureImageInfo(Uri uri);

    void getAftMarshmallowCaptureImageInfo(Uri uri);

    void doRegister();

    void unRegister();

}

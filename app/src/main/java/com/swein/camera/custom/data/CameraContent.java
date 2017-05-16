package com.swein.camera.custom.data;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.view.Surface;

/**
 * Created by seokho on 16/05/2017.
 */

public class CameraContent {



    public final static int TOUCH_FOCUS_AREA_RECT_SIZE_EDGE_LENGTH = 100;
    public final static int TOUCH_FOCUS_AREA_WEIGHT = 1000;
    public final static int DEFAULT_WIDTH = 1920;
    public final static int DEFAULT_HEIGHT = 1080;

    public static int getCorrectOrientation(Context context, int cameraId) {
        android.hardware.Camera.CameraInfo info = new android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo(cameraId, info);
        int rotation = ((Activity)context).getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360; // compensate the mirror
        } else { // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        return result;
    }

}

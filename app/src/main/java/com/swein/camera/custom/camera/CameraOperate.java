package com.swein.camera.custom.camera;

import android.content.Context;
import android.graphics.Rect;
import android.hardware.Camera;
import android.view.SurfaceHolder;

import com.swein.framework.tools.util.debug.log.ILog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.swein.camera.custom.data.CameraContent.DEFAULT_HEIGHT;
import static com.swein.camera.custom.data.CameraContent.DEFAULT_WIDTH;
import static com.swein.camera.custom.data.CameraContent.TOUCH_FOCUS_AREA_WEIGHT;

/**
 * Created by seokho on 16/05/2017.
 */

public class CameraOperate implements SurfaceHolder.Callback {

    private final static String TAG = "CameraOperate";

    private Camera camera;
    public Camera.Parameters cameraParameters;
    private SurfaceHolder surfaceHolder;

    private Context context;
    private int cameraId;

    public boolean isCameraOpen = false;

    public CameraOperate(Context context, int cameraId) {
        this.context = context;
        this.cameraId = cameraId;
    }

    private boolean openCamera() {

        if (isCameraOpen) {
            releaseCamera();
        }

        camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);

        if (camera == null) {
            return false;
        }

        cameraParameters = camera.getParameters();
        cameraParameters.setPreviewSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        try {
            camera.setParameters(cameraParameters);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        try {
//            camera.setDisplayOrientation(getCorrectOrientation(context, cameraId));
            camera.setPreviewDisplay(surfaceHolder);
        } catch (IOException e) {
            camera.release();
            camera = null;
            return false;
        }

        camera.startPreview();

        isCameraOpen = true;
        return true;
    }

    public boolean isCameraOpen() {
        return isCameraOpen;
    }

    public void releaseCamera() {
        if (camera != null) {
            camera.stopPreview();
            camera.setPreviewCallback(null);
            camera.release();
            camera = null;
        }
        isCameraOpen = false;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        surfaceHolder = holder;

        isCameraOpen = openCamera();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        releaseCamera();
    }

    /**
     * Called from CameraSurfaceView to set touch focus.
     */
    public void doTouchFocus(final Rect focusRect) {

        try {
            final List<Camera.Area> focusList = new ArrayList<Camera.Area>();
            Camera.Area focusArea = new Camera.Area(focusRect, TOUCH_FOCUS_AREA_WEIGHT);
            focusList.add(focusArea);

            Camera.Parameters para = camera.getParameters();
            para.setFocusAreas(focusList);
            para.setMeteringAreas(focusList);
            camera.setParameters(para);
            camera.autoFocus(myAutoFocusCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * AutoFocus callback
     */
    Camera.AutoFocusCallback myAutoFocusCallback = new Camera.AutoFocusCallback(){

        @Override
        public void onAutoFocus(boolean success, Camera arg1) {
            if (success){
                camera.cancelAutoFocus();
                ILog.iLogDebug(TAG, "focus success");
            }
        }
    };
}

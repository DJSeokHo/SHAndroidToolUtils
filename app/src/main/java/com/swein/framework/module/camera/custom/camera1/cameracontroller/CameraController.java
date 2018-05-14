package com.swein.framework.module.camera.custom.camera1.cameracontroller;

import android.app.Activity;
import android.content.Context;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.os.Environment;

import com.swein.framework.tools.util.bitmaps.BitmapUtil;
import com.swein.framework.tools.util.device.DeviceInfoUtil;
import com.swein.framework.tools.util.sensor.SensorUtil;
import com.swein.framework.tools.util.thread.ThreadUtil;

import java.util.List;

public class CameraController {

    private Camera camera;
    private Context context;
    private boolean isLandscape = false;

    public CameraController(Context context){
        this.context = context;
    }

    public void openCamera(int id) {

        try {
            camera = Camera.open(id);

            Camera.Parameters parameters = camera.getParameters();
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
            camera.setParameters(parameters);
        }
        catch (Exception e) {
            camera = null;
        }

    }

    public Camera getCamera() {
        return camera;
    }

    public void releaseCamera() {

        if (camera != null) {
            camera.setPreviewCallback(null);
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }

    public void checkAngle(final boolean withFinish) {

        SensorUtil.isLandscape(((Activity) context), new Runnable() {
            @Override
            public void run() {
                isLandscape = true;

            }
        }, new Runnable() {
            @Override
            public void run() {
                isLandscape = false;
            }
        });

        takePicture(withFinish, isLandscape);
    }

    private void takePicture(final boolean withFinish, final boolean isLandscape) {

        if(camera == null) {
            return;
        }

        Camera.Parameters parameters = camera.getParameters();

        parameters.setPictureFormat(ImageFormat.JPEG);

        List<Camera.Size> sizeList = parameters.getSupportedPreviewSizes();

        parameters.setPictureSize(sizeList.get(0).width,sizeList.get(0).height);

        parameters.setFlashMode(Camera.Parameters.FOCUS_MODE_AUTO);

        if(DeviceInfoUtil.isAutoFocusSupported(parameters)) {
            camera.autoFocus(new Camera.AutoFocusCallback() {

                @Override
                public void onAutoFocus(boolean success, Camera camera) {
                    capture(camera, withFinish, isLandscape);
                }
            });
        }
        else {
            capture(camera, withFinish, isLandscape);
        }

    }

    private void capture(Camera camera, final boolean withFinish, final boolean isLandscape) {

        camera.takePicture(null, null, new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {

                final String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + System.currentTimeMillis() + ".png";
                BitmapUtil.saveBitmapToJpeg(BitmapUtil.getBitmapFromByte(data), path);


                ThreadUtil.startThread(new Runnable() {
                    @Override
                    public void run() {

                        if(!isLandscape) {
                            BitmapUtil.rotateImage(path, 90);
                        }
                    }
                });

                if(withFinish) {
                    ((Activity)context).finish();
                }
                else {
                    camera.startPreview();
                }

            }
        });
    }

}

package com.swein.framework.module.camera.custom.camera1.preview;

import android.content.Context;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/**
 *
 * if you add a surface view into fragment dynamically
 * it will flash a black screen after second
 *
 * so you should
 *
 * add "setZOrderOnTop(true);" to here
 * add "surfaceHolder.setFormat(PixelFormat.TRANSPARENT);" to here
 *
 * and add "getWindow().setFormat(PixelFormat.TRANSLUCENT);" to your activity
 *
 */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder surfaceHolder;
    private Camera camera;
    private int previewDegree;

    public CameraPreview(Context context, Camera camera, int previewDegree) {
        super(context);

        setZOrderOnTop(true);

        this.camera = camera;
        this.previewDegree = previewDegree;

        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        surfaceHolder = getHolder();

        surfaceHolder.setFormat(PixelFormat.TRANSPARENT);

        surfaceHolder.addCallback(this);
        // deprecated setting, but required on Android versions prior to 3.0
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // The Surface has been created, now tell the camera where to draw the preview.
        try {
            camera.setPreviewDisplay(holder);
            camera.setDisplayOrientation(previewDegree);
            camera.startPreview();
            camera.cancelAutoFocus();
        }
        catch (IOException e) {
          e.printStackTrace();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // empty. Take care of releasing the Camera preview in your activity.
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.

        if (surfaceHolder.getSurface() == null){
            // preview surface does not exist
            return;
        }

        // stop preview before making changes
        try {
            camera.stopPreview();
        } catch (Exception e){
            // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here

        // start preview with new settings
        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}

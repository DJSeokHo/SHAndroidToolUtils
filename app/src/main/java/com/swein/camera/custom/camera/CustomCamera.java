package com.swein.camera.custom.camera;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import com.swein.shandroidtoolutils.R;

import java.io.IOException;

/**
 * Created by seokho on 11/05/2017.
 */

public class CustomCamera extends Activity implements SurfaceHolder.Callback{

    private Button buttonCapture;
    private SurfaceView preview;
    private Camera camera;

    private SurfaceHolder surfaceHolder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_camera);

        preview = (SurfaceView)findViewById(R.id.preview);
        surfaceHolder = preview.getHolder();
        surfaceHolder.addCallback(this);
    }

    public void capture(View view) {

    }

    /**
     * get a camera object with init
     * @return a camera object
     */
    private Camera getCamera() {
        try {
            camera = Camera.open();
        }
        catch (Exception e) {
            camera = null;
            e.printStackTrace();
        }
        return camera;
    }

    /**
     * show image from camera in real-time
     */
    private void setStartPreview(Camera camera, SurfaceHolder surfaceHolder) {
        try {
            camera.setPreviewDisplay(surfaceHolder);
            //turn system camera preview degree
            camera.setDisplayOrientation(90);
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * release camera resource
     */
    private void releaseCamera() {
        if(camera != null) {
            camera.setPreviewCallback(null);
            camera.stopPreview();
            camera.release();
            camera = null;
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(camera == null) {
            camera = getCamera();
            if(surfaceHolder != null) {
                setStartPreview(camera, surfaceHolder);
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        setStartPreview(camera, holder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        camera.stopPreview();
        setStartPreview(camera, surfaceHolder);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        releaseCamera();
    }
}

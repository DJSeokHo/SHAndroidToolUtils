package com.swein.framework.module.camera.custom.camera1.preview;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.view.TextureView;

import java.io.IOException;

public class CameraPreview extends TextureView implements TextureView.SurfaceTextureListener {

    private Camera camera;
    private int previewDegree;

    public CameraPreview(Context context) {
        super(context);

        setSurfaceTextureListener(this);
    }

    public void init(Camera camera, int previewDegree) {

        this.camera = camera;
        this.previewDegree = previewDegree;

    }

    @Override
    public void onSurfaceTextureAvailable(final SurfaceTexture surface, int width, int height) {

        try {
            camera.setPreviewTexture(surface);
            camera.setDisplayOrientation(previewDegree);
            camera.startPreview();
            camera.cancelAutoFocus();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

        if (surface == null){
            // preview surface does not exist
            return;
        }

        // stop preview before making changes
        try {
            camera.stopPreview();
        }
        catch (Exception e){
            // ignore: tried to stop a non-existent preview
        }

        // start preview with new settings
        try {
            camera.setPreviewTexture(surface);
            camera.startPreview();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

}

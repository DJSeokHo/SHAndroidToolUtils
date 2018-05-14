package com.swein.framework.module.camera.custom.camera1.preview;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.view.TextureView;

/**
 * for flash a black or white screen
 * use this...
 */
public class FakeSurfaceView  extends TextureView implements TextureView.SurfaceTextureListener {

    public FakeSurfaceView(Context context) {
        super(context);
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }
}

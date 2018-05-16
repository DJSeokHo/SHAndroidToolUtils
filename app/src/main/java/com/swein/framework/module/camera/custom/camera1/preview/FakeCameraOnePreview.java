package com.swein.framework.module.camera.custom.camera1.preview;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class FakeCameraOnePreview extends SurfaceView implements SurfaceHolder.Callback {

    public FakeCameraOnePreview(Context context) {
        super(context);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}

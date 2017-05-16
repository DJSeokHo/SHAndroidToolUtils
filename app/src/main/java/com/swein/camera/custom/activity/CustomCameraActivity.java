package com.swein.camera.custom.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.ImageFormat;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.ImageButton;

import com.swein.camera.custom.camera.CameraOperate;
import com.swein.camera.custom.draw.FocusAreaView;
import com.swein.camera.custom.preview.CameraSurfaceView;
import com.swein.camera.custom.result.ResultActivity;
import com.swein.shandroidtoolutils.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.swein.camera.custom.data.CameraContent.DEFAULT_HEIGHT;
import static com.swein.camera.custom.data.CameraContent.DEFAULT_WIDTH;

/**
 * Created by seokho on 15/05/2017.
 */

public class CustomCameraActivity extends Activity {

    private CameraSurfaceView preview;
    private CameraOperate cameraOperate;
    private FocusAreaView focusAreaView;

    private final String TAG = "CustomCameraActivity";

    private int frontCameraId;
    private int backCameraId;

    private ImageButton imageButtonCapture;
    private Camera camera;

    private Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            File file = new File("/sdcard/temp.png");
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(data);
                fileOutputStream.close();

                Intent intent = new Intent(CustomCameraActivity.this, ResultActivity.class);
                intent.putExtra("picPath", file.getAbsolutePath());
                startActivity(intent);
                CustomCameraActivity.this.finish();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_camera);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFormat(PixelFormat.UNKNOWN);
        availableCamera();
        preview = (CameraSurfaceView) findViewById(R.id.preview);
        SurfaceHolder surfaceHolder = preview.getHolder();

        cameraOperate = new CameraOperate(this, backCameraId);
        surfaceHolder.addCallback(cameraOperate);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        preview.setListener(cameraOperate);

        focusAreaView = (FocusAreaView) findViewById(R.id.focusAreaView);
        preview.FocusAreaView(focusAreaView);

        imageButtonCapture = (ImageButton) findViewById(R.id.imageButtonCapture);
        imageButtonCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capture(v);
            }

        });
    }

    public void capture(View view) {

//        final int displayOrientation = getCorrectOrientation(this, backCameraId);

        Camera.Parameters parameters = camera.getParameters();
        parameters.setPictureFormat(ImageFormat.JPEG);
        parameters.setPreviewSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
//        parameters.setRotation(displayOrientation);
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        camera.autoFocus(new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean success, Camera camera) {
                if(success) {
                    camera.takePicture(null, null, pictureCallback);
                }
            }
        });
    }

    /**
     * check available camera
     */
    private void availableCamera() {
        Camera.CameraInfo info = new Camera.CameraInfo();
        int numCamera = Camera.getNumberOfCameras();
        for (int i = 0; i < numCamera; i++) {
            Camera.getCameraInfo(i, info);

            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                frontCameraId = info.facing;
            }

            if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                backCameraId = info.facing;
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

}

package com.swein.framework.module.camera.custom.camera1.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.swein.framework.module.camera.custom.camera1.cameracontroller.CameraController;
import com.swein.framework.module.camera.custom.camera1.constants.CameraOneConstants;
import com.swein.framework.module.camera.custom.camera1.preview.CameraPreview;
import com.swein.framework.tools.util.device.DeviceInfoUtil;
import com.swein.framework.tools.window.WindowUtil;
import com.swein.shandroidtoolutils.R;

/**
 *
 * put
 * <uses-feature android:name="android.hardware.camera" android:required="true"/>
 * <uses-feature android:name="android.hardware.camera.autofocus" android:required="false"/>
 *
 * <uses-permission android:name="android.permission.CAMERA" />
 * <uses-permission android:name="android.permission.WEITE_EXTERNAL_STORAGE" />
 *
 * into your manifest.xml
 *
 */
public class CameraOneActivity extends FragmentActivity {

    private CameraController cameraController;

    private FrameLayout frameLayout;
    private ImageButton buttonSwitchCamera;
    private ImageButton buttonCapture;

    private CameraOneConstants.CameraId cameraId = CameraOneConstants.CameraId.BACK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_one);

        if(!DeviceInfoUtil.hasCameraDevice(this)) {
            return;
        }

        cameraController = new CameraController(this);

        findView();
    }


    private void findView() {

        frameLayout = (FrameLayout) findViewById(R.id.fl_camera);
        buttonSwitchCamera = (ImageButton) findViewById(R.id.btn_switch_camera);
        buttonCapture = (ImageButton) findViewById(R.id.btn_capture);

        buttonSwitchCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchCamera();
            }
        });

        buttonCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraController.checkAngle(false);
            }
        });
    }

    private void switchCamera() {
        if(CameraOneConstants.CameraId.BACK == cameraId) {
            cameraId = CameraOneConstants.CameraId.FRONT;
        }
        else {
            cameraId = CameraOneConstants.CameraId.BACK;
        }

        releaseCameraPreview();
        setCameraPreview();
    }

    @Override
    public void onResume() {
        WindowUtil.fullScreen(this);
        setCameraPreview();
        super.onResume();
    }

    private void setCameraPreview() {

        cameraController.openCamera(cameraId.ordinal());
        CameraPreview cameraPreview = new CameraPreview(this);
        cameraPreview.init(cameraController.getCamera(), 90);
        frameLayout.addView(cameraPreview);
    }


    @Override
    public void onPause() {
        releaseCameraPreview();
        super.onPause();
    }

    private void releaseCameraPreview() {
        frameLayout.removeAllViews();
        cameraController.releaseCamera();
    }

    @Override
    public void onDestroy() {
        cameraController = null;
        super.onDestroy();
    }


}

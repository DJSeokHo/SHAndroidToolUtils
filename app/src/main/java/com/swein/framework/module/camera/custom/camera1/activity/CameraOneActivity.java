package com.swein.framework.module.camera.custom.camera1.activity;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.swein.framework.module.camera.custom.camera1.fragment.CameraOneFragment;
import com.swein.framework.tools.util.activity.ActivityUtil;
import com.swein.framework.tools.util.device.DeviceUtil;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_one);

        if(!DeviceUtil.hasCameraDevice(this)) {
            return;
        }

        if(savedInstanceState == null) {

            ActivityUtil.addFragment(this, R.id.container, new CameraOneFragment(), false);

        }
    }


}
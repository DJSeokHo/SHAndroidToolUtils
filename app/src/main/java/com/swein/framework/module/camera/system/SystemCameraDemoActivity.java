package com.swein.framework.module.camera.system;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.swein.shandroidtoolutils.MainActivity;
import com.swein.shandroidtoolutils.R;

public class SystemCameraDemoActivity extends AppCompatActivity {

    private SHCameraIntent shCameraIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_camera_demo);

        shCameraIntent = new SHCameraIntent(this);
        shCameraIntent.openCamera();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        shCameraIntent.onActivityResult(requestCode, resultCode);

        finish();
    }

}

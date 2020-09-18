package com.swein.framework.module.basicpermission.demo;

import android.Manifest;
import android.os.Bundle;

import com.swein.framework.module.basicpermission.BasicPermissionActivity;
import com.swein.framework.module.basicpermission.PermissionManager;
import com.swein.framework.module.basicpermission.RequestPermission;
import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.shandroidtoolutils.R;

public class PermissionDemoActivity extends BasicPermissionActivity {

    private final static String TAG = "PermissionDemoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_demo);

        findViewById(R.id.textView).setOnClickListener(view -> runThis());
    }

    @RequestPermission(permissionCode= PermissionManager.PERMISSION_REQUEST_CAMERA_CODE)
    private void runThis() {

        if(PermissionManager.getInstance().requestPermission(this, true, PermissionManager.PERMISSION_REQUEST_CAMERA_CODE,
                Manifest.permission.CAMERA)) {

            ILog.iLogDebug(TAG, "run run run");
        }
        else {
            ILog.iLogDebug(TAG, "nonono");
        }

    }
}
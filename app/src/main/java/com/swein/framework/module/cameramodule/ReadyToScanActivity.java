package com.swein.framework.module.cameramodule;

import android.Manifest;
import android.os.Bundle;

import com.swein.framework.module.basicpermission.BasicPermissionActivity;
import com.swein.framework.module.basicpermission.PermissionManager;
import com.swein.framework.module.basicpermission.RequestPermission;
import com.swein.framework.module.cameramodule.scan.ScannerActivity;
import com.swein.framework.tools.util.activity.ActivityUtil;
import com.swein.shandroidtoolutils.R;

public class ReadyToScanActivity extends BasicPermissionActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ready_to_scan);

        findViewById(R.id.textViewScanBankCard).setOnClickListener(view -> scanBankCard());
        findViewById(R.id.textViewScanQRCode).setOnClickListener(view -> scanQRCode());
    }

    @RequestPermission(permissionCode= PermissionManager.PERMISSION_REQUEST_CAMERA_CODE)
    private void scanBankCard() {

        if(PermissionManager.getInstance().requestPermission(this, false, PermissionManager.PERMISSION_REQUEST_CAMERA_CODE,
                Manifest.permission.CAMERA)) {

            Bundle bundle = new Bundle();
            bundle.putInt("type", 1);
            ActivityUtil.startNewActivityWithoutFinish(this, ScannerActivity.class, bundle);
        }

    }

    @RequestPermission(permissionCode= PermissionManager.PERMISSION_REQUEST_CAMERA_CODE)
    private void scanQRCode() {

        if(PermissionManager.getInstance().requestPermission(this, false, PermissionManager.PERMISSION_REQUEST_CAMERA_CODE,
                Manifest.permission.CAMERA)) {

            Bundle bundle = new Bundle();
            bundle.putInt("type", 0);
            ActivityUtil.startNewActivityWithoutFinish(this, ScannerActivity.class, bundle);

        }

    }
}
package com.swein.framework.module.cameramodule;

import android.Manifest;
import android.os.Bundle;

import com.swein.framework.module.basicpermission.BasicPermissionActivity;
import com.swein.framework.module.basicpermission.PermissionManager;
import com.swein.framework.module.basicpermission.RequestPermission;
import com.swein.framework.module.cameramodule.scan.ScanBankCardActivity;
import com.swein.framework.tools.util.activity.ActivityUtil;
import com.swein.shandroidtoolutils.R;

public class BindingBankCardActivity extends BasicPermissionActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binding_bank_card);

        findViewById(R.id.textViewScanBankCard).setOnClickListener(view -> scanBankCard());
    }

    @RequestPermission(permissionCode= PermissionManager.PERMISSION_REQUEST_CAMERA_CODE)
    private void scanBankCard() {

        if(PermissionManager.getInstance().requestPermission(this, false, PermissionManager.PERMISSION_REQUEST_CAMERA_CODE,
                Manifest.permission.CAMERA)) {
            ActivityUtil.startNewActivityWithoutFinish(this, ScanBankCardActivity.class);
        }

    }
}
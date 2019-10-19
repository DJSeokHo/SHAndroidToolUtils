package com.swein.framework.template.permission;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.swein.shandroidtoolutils.R;

import java.util.ArrayList;
import java.util.List;

public class PermissionTemplateActivity extends AppCompatActivity {

    private final static int REQUEST_CODE = 101;

    private List<String> deniedPermissionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_template);

        /* Add permissions that you want in here */
        requestRuntimePermission(new String[] {
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO
        });
    }

    private void requestRuntimePermission(String[] permissions) {

        List<String> permissionList = new ArrayList<>();

        for(String permission: permissions) {
            if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permission);
            }
        }

        if(permissionList.isEmpty()) {
            return;
        }

        ActivityCompat.requestPermissions(this, permissionList.toArray(new String[permissionList.size()]), REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        /*
            grantResults: 0: agree,  1: reject
         */
        if(REQUEST_CODE == requestCode) {
            if(grantResults.length == 0) {
                return;
            }

            for(int i = 0; i < grantResults.length; i++) {
                /* 用户选择了不再显示该权限请求，并且拒绝以后 */
                boolean shouldShowAgant = ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i]);
                int grantResult = grantResults[i];
                String permission = permissions[i];

                if(PackageManager.PERMISSION_GRANTED !=grantResult) {
                    if(shouldShowAgant) {
                        /* 用户没有彻底禁止弹出权限请求 */
                        deniedPermissionList.add(permission);
                    }
                    else {
                        /* 用户彻底禁止弹出权限请求，再次申请的话需要帮助用户去到设置页面 */

                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("");
                        builder.setMessage("");
                        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                /* 这里跳转到具体设置页面 */
                            }
                        });

                        builder.create();
                        builder.show();
                    }
                }
            }

            if(deniedPermissionList.isEmpty()) {
                /* 权限通过成功 */
            }
            else {
                ActivityCompat.requestPermissions(this,
                        deniedPermissionList.toArray(new String[deniedPermissionList.size()]), REQUEST_CODE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /* 从设置界面回来的话会进入这里检查权限是否获取 */
    }
}

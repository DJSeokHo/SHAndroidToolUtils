package com.swein.framework.module.easyscreenrecord.manager.permission;

import android.Manifest;
import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.swein.framework.tools.util.dialog.DialogUtil;


/**
 * Created by seokho on 04/02/2017.
 */

public class PermissionManager {

    public static final int RECORD_REQUEST_CODE  = 101;
    public static final int STORAGE_REQUEST_CODE = 102;
    public static final int AUDIO_REQUEST_CODE   = 103;

    public static int MY_PERMISSIONS_REQUEST_PACKAGE_USAGE_STATUS = 1101;

    //Only need when version > M in Activity
    public static void doRequestPermissions(Context context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity)context,
                    new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_REQUEST_CODE);
        }

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity)context,
                    new String[] {Manifest.permission.RECORD_AUDIO}, AUDIO_REQUEST_CODE);
        }
    }

    public static void onRequestPermissionsResult(Context context, int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_REQUEST_CODE || requestCode == AUDIO_REQUEST_CODE) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                ((Activity)context).finish();
            }
        }
    }

    public static void getRuntimeReadStoragePermission(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ((Activity)context).requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_PACKAGE_USAGE_STATUS);
        }
    }

    public static boolean checkAppsWithUsageAccessPermission(Context context) {

        if (null == context) {

            return false;

        }

        AppOpsManager appOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        int mode = 0;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, android.os.Process.myUid(), context.getPackageName());

        }

        return mode == AppOpsManager.MODE_ALLOWED;
    }

    public static void requestPackageUsageStatus(final Context context) {
        DialogUtil.createNormalDialogWithTwoButton(
                context,
                "package",
                "package",
                false,
                "go set",
                "cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((Activity)context).startActivityForResult(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS), PermissionManager.MY_PERMISSIONS_REQUEST_PACKAGE_USAGE_STATUS);
                        dialog.dismiss();
                    }
                },
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }
        );
    }

    //Fragment
    public static void goAppsWithUsageAccessPermissionOperationPage(final Context context, int resourceID, Runnable runnable) {
        getRuntimeReadStoragePermission(context);

        if (!checkAppsWithUsageAccessPermission(context)) {

            requestPackageUsageStatus(context);

        } else {

            runnable.run();

        }
    }
}

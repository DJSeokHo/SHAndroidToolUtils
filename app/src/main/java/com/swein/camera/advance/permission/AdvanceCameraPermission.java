package com.swein.camera.advance.permission;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import com.swein.framework.tools.util.dialog.DialogUtils;

import static com.swein.camera.advance.data.AdvanceCameraContent.FRAGMENT_DIALOG;

/**
 * Created by seokho on 15/05/2017.
 */

public class AdvanceCameraPermission {

    public static final int REQUEST_CAMERA_PERMISSION = 1;

    public static void requestCameraPermission(final Activity activity) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA)) {
            DialogUtils.createNormalDialogWithTwoButton(activity, FRAGMENT_DIALOG, "request permission",
                    false, activity.getString(android.R.string.ok), activity.getString(android.R.string.cancel),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(
                                    activity,
                                    new String[]{Manifest.permission.CAMERA},
                                    REQUEST_CAMERA_PERMISSION);
                        }
                    },
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (activity != null) {
                                activity.finish();
                            }
                        }
                    });
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA_PERMISSION);
        }
    }

    public static void requestPermissionsResult(final Activity activity, int requestCode, @NonNull String[] permissions,
                                                  @NonNull int[] grantResults) {

        if (grantResults.length != 1 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {

            DialogUtils.createNormalDialogWithOneButton(activity, FRAGMENT_DIALOG, "request permission", false,
                    activity.getString(android.R.string.ok),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            activity.finish();
                        }
                    });
        }
    }

}

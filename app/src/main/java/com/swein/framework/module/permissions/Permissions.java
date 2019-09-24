package com.swein.framework.module.permissions;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Permissions {

    private final static String[] PERMISSIONS = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    private final static int PERMISSIONS_REQUEST_CODE = 999;

    private static Permissions instance = new Permissions();
    public static Permissions getInstance() {
        return instance;
    }

    private Permissions() {

    }

    public void requestPermission(Activity activity) {

        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(activity, PERMISSIONS, PERMISSIONS_REQUEST_CODE);
        }
    }

    public void onRequestPermissionsResult(Activity activity, int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case PERMISSIONS_REQUEST_CODE:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                        && (grantResults[1] == PackageManager.PERMISSION_GRANTED)
                        && (grantResults[2] == PackageManager.PERMISSION_GRANTED)
                        && (grantResults[3] == PackageManager.PERMISSION_GRANTED)
                        && (grantResults[4] == PackageManager.PERMISSION_GRANTED)
                        && (grantResults[5] == PackageManager.PERMISSION_GRANTED)) {
                }
                else {
                    activity.finish();
                }
                break;

            default:
                break;
        }
    }

}

package com.swein.framework.module.camera.cameraandalbum;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.swein.framework.tools.util.debug.log.ILog;

import java.io.File;

public class CameraAndAlbum {

    public interface CameraAndAlbumDelegate {
        void onCameraPhoto(String photoPath);
        void onAlbumPhoto(String photoPath);
    }

    private final static String TAG = "CameraAndAlbum";

    private final static String[] PERMISSIONS = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private final static int PERMISSIONS_REQUEST_CODE = 999;
    private final static int CAMERA_REQUEST_CODE = 901;
    private final static int ALBUM_REQUEST_CODE = 902;

    private final static String PACKAGE_NAME = "com.swein.shandroidtoolutils";

    public File cameraPhotoPath;
    public Uri uri;

    private static CameraAndAlbum instance = new CameraAndAlbum();
    public static CameraAndAlbum getInstance() {
        return instance;
    }
    /**
     * if api >= 24 put this in AndroidManifest.xml
     *
     * <provider
     *             android:name="android.support.v4.content.FileProvider"
     *             android:authorities="your_package_name.fileprovider"
     *             android:exported="false"
     *             android:grantUriPermissions="true">
     *             <meta-data
     *                 android:name="android.support.FILE_PROVIDER_PATHS"
     *                 android:resource="@xml/file_paths" />
     *         </provider>
     */
    private CameraAndAlbum() {

    }

    public void requestPermission(Activity activity) {

        if (ContextCompat.checkSelfPermission(activity, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(activity, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(activity, PERMISSIONS, PERMISSIONS_REQUEST_CODE);
        }
        else {
            activity.finish();
        }
    }


    public void onRequestPermissionsResult(Activity activity, int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case PERMISSIONS_REQUEST_CODE:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                        && (grantResults[1] == PackageManager.PERMISSION_GRANTED)
                        && (grantResults[2] == PackageManager.PERMISSION_GRANTED)) {

                }
                else {
                    activity.finish();
                }
                break;

            default:
                break;
        }
    }

    public void openPhotoAlbum(Activity activity) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        activity.startActivityForResult(intent, ALBUM_REQUEST_CODE);
    }

    public void openCamera(Activity activity) {

        cameraPhotoPath = new File(Environment.getExternalStorageDirectory().getPath() + "/" + System.currentTimeMillis() + ".jpg");

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(activity, PACKAGE_NAME + ".fileprovider", cameraPhotoPath);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        else {
            uri = Uri.fromFile(cameraPhotoPath);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        activity.startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }

    /**
     * put this before super
     */
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data, CameraAndAlbumDelegate cameraAndAlbumDelegate) {
        String photoPath;
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                photoPath = String.valueOf(cameraPhotoPath);
            }
            else {
                photoPath = uri.getEncodedPath();
            }

            ILog.iLogDebug(TAG, photoPath);
            cameraAndAlbumDelegate.onCameraPhoto(photoPath);

        }
        else if (requestCode == ALBUM_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            photoPath = PhotoFromAlbum.getRealPathFromUri(activity, data.getData());
            cameraAndAlbumDelegate.onAlbumPhoto(photoPath);
        }
    }

}

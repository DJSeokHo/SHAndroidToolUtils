package com.swein.framework.module.camera.system;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import com.swein.framework.tools.util.bitmaps.BitmapUtil;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * reference documentation
 * https://developer.android.com/training/camera/photobasics?hl=zh-cn
 */
public class SHCameraIntent {

    private Context context;

    private File photoFile;

    private final static int REQUEST_CODE_IMAGE_CHOOSER_BASE_64 = 101;
    private final static int IMAGE_COMPRESS_RATE = 100; // 100 means no compress

    public SHCameraIntent(Context context) {
        this.context = context;
    }

    public void openCamera() {
        dispatchTakePictureIntent();
    }

//    private void takePhoto() {
//        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if(takePhotoIntent.resolveActivity(getPackageManager())!=null) {
//            File imageFile = createImageFile();
//            if(imageFile!=null) {
//                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
//                    /*7.0以上要通过FileProvider将File转化为Uri*/
//                    mImageUri = FileProvider.getUriForFile(this,FILE_PROVIDER_AUTHORITY,imageFile);
//                }
//                else {
//                    /*7.0以下则直接使用Uri的fromFile方法将File转化为Uri*/
//                    mImageUri = Uri.fromFile(imageFile);
//                }
//                takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT,mImageUri);
//                startActivityForResult(takePhotoIntent, REQUEST_TAKE_PHOTO_CODE);
//            }
//        }
//    }

    private void dispatchTakePictureIntent() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        photoFile = null;

        try {
            photoFile = createImageFile();
            Uri uri;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                // 5.0 and 6.0
                uri = Uri.fromFile(photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            }
            else {
                // 7.0
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(MediaStore.Images.Media.DATA, photoFile.getAbsolutePath());
                uri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            }
            ((Activity)context).startActivityForResult(intent, REQUEST_CODE_IMAGE_CHOOSER_BASE_64);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    private File createImageFile() throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName,".jpg", storageDir);

        return image;
    }

    /**
     *
     * put this method on activity....you know it...
     *
     * photoFile.getPath() is photo storage path
     * @param requestCode
     * @param resultCode
     */
    public void onActivityResult(int requestCode, int resultCode) {

        if (REQUEST_CODE_IMAGE_CHOOSER_BASE_64 == requestCode && resultCode == Activity.RESULT_OK) {
            BitmapUtil.rotateImage(photoFile.getPath());
            BitmapUtil.ResizeImages(photoFile.getPath(), IMAGE_COMPRESS_RATE);
        }
    }


}

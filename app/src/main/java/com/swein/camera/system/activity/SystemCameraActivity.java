package com.swein.camera.system.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.swein.framework.tools.util.activity.ActivityUtils;
import com.swein.shandroidtoolutils.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SystemCameraActivity extends AppCompatActivity {

    final private static int SYSTEM_CAMERA_THUMB = 1;
    final private static int SYSTEM_CAMERA = 2;

    private Button buttonThumb;
    private Button buttonCamera;
    private ImageView imageView;

    private String filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_camera);

        buttonThumb = (Button)findViewById(R.id.buttonThumb);
        buttonCamera = (Button)findViewById(R.id.buttonCamera);
        imageView = (ImageView)findViewById(R.id.imageView);

        buttonThumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                ActivityUtils.startSystemIntentWithResultByRequestCode(SystemCameraActivity.this, intent, SYSTEM_CAMERA_THUMB);
            }
        });

        buttonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Uri photoUri = Uri.fromFile(new File(filePath));
                //EXTRA_OUTPUT:set system camera photo path
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                ActivityUtils.startSystemIntentWithResultByRequestCode(SystemCameraActivity.this, intent, SYSTEM_CAMERA);
            }
        });

        filePath = Environment.getExternalStorageDirectory().getPath();
        filePath = filePath + "/" + "temp.png";
    }


    /**
     *
     * @param requestCode
     * @param resultCode
     * @param data:can only get thumbnail image
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && SYSTEM_CAMERA_THUMB == requestCode && data != null) {
            Bundle bundle = data.getExtras();

            Bitmap bitmap = (Bitmap)bundle.get("data");
            imageView.setImageBitmap(bitmap);
        }
        else if(resultCode == RESULT_OK && SYSTEM_CAMERA == requestCode) {
            FileInputStream fileInputStream = null;
            try {
                fileInputStream = new FileInputStream(filePath);
                Bitmap bitmap = BitmapFactory.decodeStream(fileInputStream);
                imageView.setImageBitmap(bitmap);
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

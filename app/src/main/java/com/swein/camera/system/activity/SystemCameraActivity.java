package com.swein.camera.system.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.swein.framework.tools.util.activity.ActivityUtils;
import com.swein.shandroidtoolutils.R;

public class SystemCameraActivity extends AppCompatActivity {

    final private static int SYSTEM_CAMERA = 1;

    private Button buttonCamera;
    private ImageView imageView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_camera);

        buttonCamera = (Button)findViewById(R.id.buttonCamera);
        imageView = (ImageView)findViewById(R.id.imageView);

        buttonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                ActivityUtils.startSystemIntentWithResultByRequestCode(SystemCameraActivity.this, intent, SYSTEM_CAMERA);
            }
        });
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
        if(resultCode == RESULT_OK && SYSTEM_CAMERA == requestCode && data != null) {
            Bundle bundle = data.getExtras();

            Bitmap bitmap = (Bitmap)bundle.get("data");
            imageView.setImageBitmap(bitmap);
        }
    }
}

package com.swein.camera.custom.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.swein.camera.custom.camera.CustomCamera;
import com.swein.framework.tools.util.activity.ActivityUtils;
import com.swein.shandroidtoolutils.R;

public class CustomCameraActivity extends AppCompatActivity {

    private Button buttonCustomCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_camera);

        buttonCustomCamera = (Button)findViewById(R.id.buttonCustomCamera);

        buttonCustomCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.startNewActivityWithoutFinish(CustomCameraActivity.this, CustomCamera.class);
            }
        });
    }
}

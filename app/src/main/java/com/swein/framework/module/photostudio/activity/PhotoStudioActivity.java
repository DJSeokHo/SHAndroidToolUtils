package com.swein.framework.module.photostudio.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.swein.framework.module.photostudio.result.PrimaryColorActivity;
import com.swein.framework.tools.util.activity.ActivityUtils;
import com.swein.shandroidtoolutils.R;

public class PhotoStudioActivity extends AppCompatActivity {

    private Button buttonPrimaryColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_studio);

        buttonPrimaryColor = (Button) findViewById(R.id.buttonPrimaryColor);
        buttonPrimaryColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.startNewActivityWithoutFinish(PhotoStudioActivity.this, PrimaryColorActivity.class);
            }
        });



    }



}

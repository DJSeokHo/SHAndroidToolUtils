package com.swein.framework.module.camera.custom.camera2.activity;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.swein.framework.module.camera.custom.camera2.fragment.CameraTwoFragment;
import com.swein.framework.tools.util.activity.ActivityUtil;
import com.swein.shandroidtoolutils.R;

public class CameraTwoActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_two);

        if (null == savedInstanceState) {
            ActivityUtil.replaceFragment(this, R.id.container, new CameraTwoFragment());
        }
    }
}

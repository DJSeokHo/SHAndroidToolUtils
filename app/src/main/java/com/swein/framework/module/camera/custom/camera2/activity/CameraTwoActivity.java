package com.swein.framework.module.camera.custom.camera2.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.FrameLayout;

import com.swein.framework.module.camera.custom.camera2.fragment.CameraTwoFragment;
import com.swein.framework.tools.util.activity.ActivityUtil;
import com.swein.framework.tools.util.window.WindowUtil;
import com.swein.shandroidtoolutils.R;

public class CameraTwoActivity extends FragmentActivity {

    private FrameLayout frameLayoutProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_two);

        if (null == savedInstanceState) {
            ActivityUtil.replaceFragment(this, R.id.container, new CameraTwoFragment());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        WindowUtil.fullScreen(this);
    }


}

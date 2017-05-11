package com.swein.camera.advance.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.swein.camera.advance.fragment.CameraFragment;
import com.swein.framework.tools.util.fragment.FragmentUtils;
import com.swein.shandroidtoolutils.R;

/**
 * project name：${Advance Camera}
 * class： AdvanceCameraActivity
 * Create：${Seok Ho}
 * Create Date：${11/05/2017}
 * Fix：${}
 * Fix Date：${DATE}
 * Fix Note：
 * @version
 */
public class AdvanceCameraActivity extends FragmentActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advance_camera);

        CameraFragment cameraFragment = new CameraFragment();

        FragmentUtils.replaceFragmentv4Commit(this, cameraFragment, R.id.container);

    }

}

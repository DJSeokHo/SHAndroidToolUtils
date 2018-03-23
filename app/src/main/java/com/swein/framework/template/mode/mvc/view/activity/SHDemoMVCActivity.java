package com.swein.framework.template.mode.mvc.view.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.swein.framework.template.mode.mvc.view.fragment.SHDemoMVCFragment;
import com.swein.framework.tools.util.fragment.FragmentUtil;
import com.swein.shandroidtoolutils.R;

public class SHDemoMVCActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shdemo_mvc);

        FragmentUtil.replaceFragmentv4Commit(this, new SHDemoMVCFragment(), R.id.container);

    }
}

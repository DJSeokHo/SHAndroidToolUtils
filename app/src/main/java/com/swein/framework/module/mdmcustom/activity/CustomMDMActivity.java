package com.swein.framework.module.mdmcustom.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.swein.framework.module.mdmcustom.fragment.CustomMDMFragment;
import com.swein.framework.tools.util.activity.ActivityUtil;
import com.swein.shandroidtoolutils.R;

public class CustomMDMActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_mdm);

        if(savedInstanceState == null) {

            ActivityUtil.addFragment(this, R.id.container, new CustomMDMFragment(), false);

        }


    }
}

package com.swein.framework.template.tabhost.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.swein.framework.template.tabhost.fragment.SHTabHostFragment;
import com.swein.framework.tools.util.activity.ActivityUtils;
import com.swein.shandroidtoolutils.R;

public class SHTabHostActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shtab_host);

        if(savedInstanceState == null) {

            ActivityUtils.addFragment(this, R.id.shTabHostContainer, new SHTabHostFragment(), false);

        }

    }
}

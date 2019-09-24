package com.swein.framework.template.tabhost.activity;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.swein.framework.template.tabhost.fragment.SHTabHostFragment;
import com.swein.framework.tools.util.activity.ActivityUtil;
import com.swein.shandroidtoolutils.R;

public class SHTabHostActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shtab_host);

        if(savedInstanceState == null) {

            ActivityUtil.addFragment(this, R.id.shTabHostContainer, new SHTabHostFragment(), false);

        }

    }
}

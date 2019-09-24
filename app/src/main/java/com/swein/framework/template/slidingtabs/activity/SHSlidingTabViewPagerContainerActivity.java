package com.swein.framework.template.slidingtabs.activity;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.swein.framework.template.slidingtabs.fragment.SHSlidingTabViewPagerContainerFragment;
import com.swein.framework.tools.util.activity.ActivityUtil;
import com.swein.shandroidtoolutils.R;

public class SHSlidingTabViewPagerContainerActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shsliding_tab_view_pager_container);

        if(savedInstanceState == null) {

            ActivityUtil.addFragment(this, R.id.shSlidingTabViewPageContainer, new SHSlidingTabViewPagerContainerFragment(), false);

        }
    }
}

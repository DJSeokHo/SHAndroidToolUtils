package com.swein.framework.template.viewpagerfragment.activity;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.swein.framework.template.viewpagerfragment.mainfragment.SHViewPagerFragmentMainFragment;
import com.swein.framework.tools.util.activity.ActivityUtil;
import com.swein.shandroidtoolutils.R;

public class SHViewPagerFragmentActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shview_pager_fragment_activity);

        if(savedInstanceState == null) {

            ActivityUtil.addFragment(this, R.id.container, new SHViewPagerFragmentMainFragment(), false);

        }
    }
}

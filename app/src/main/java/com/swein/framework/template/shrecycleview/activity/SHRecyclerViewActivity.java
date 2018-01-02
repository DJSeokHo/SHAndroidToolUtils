package com.swein.framework.template.shrecycleview.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.swein.framework.template.shrecycleview.fragment.SHRecyclerViewFragment;
import com.swein.framework.tools.util.activity.ActivityUtils;
import com.swein.shandroidtoolutils.R;

public class SHRecyclerViewActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shrecycler_view);

        if(savedInstanceState == null) {

            ActivityUtils.addFragment(this, R.id.shRecyclerViewContainer, new SHRecyclerViewFragment(), false);

        }

    }
}

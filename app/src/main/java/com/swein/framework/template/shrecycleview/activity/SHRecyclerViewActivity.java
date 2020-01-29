package com.swein.framework.template.shrecycleview.activity;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.swein.framework.template.shrecycleview.normalmode.fragment.SHRecyclerViewFragment;
import com.swein.framework.tools.util.activity.ActivityUtil;
import com.swein.framework.tools.util.glide.SHGlide;
import com.swein.framework.tools.util.thread.ThreadUtil;
import com.swein.shandroidtoolutils.R;

public class SHRecyclerViewActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shrecycler_view);

        if(savedInstanceState == null) {

            ActivityUtil.addFragment(this, R.id.shRecyclerViewContainer, new SHRecyclerViewFragment(), false);

        }

    }

    @Override
    protected void onDestroy() {
        ThreadUtil.startThread(() -> {
            SHGlide.getInstance().clearDiskCache(this);
        });
        super.onDestroy();
    }
}

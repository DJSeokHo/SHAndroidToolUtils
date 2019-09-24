package com.swein.framework.template.cardview.activity;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.swein.framework.template.cardview.fragment.SHCardViewFragment;
import com.swein.framework.tools.util.activity.ActivityUtil;
import com.swein.shandroidtoolutils.R;

public class SHCardViewActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shcard_view);

        if(savedInstanceState == null) {

            ActivityUtil.addFragment(this, R.id.shCardViewContainer, new SHCardViewFragment(), false);

        }
    }
}

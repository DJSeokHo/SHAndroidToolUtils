package com.swein.framework.template.tabcustomslidinglayout.activity;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.swein.framework.template.tabcustomslidinglayout.fragments.SubOneFragment;
import com.swein.framework.template.tabcustomslidinglayout.fragments.SubThreeFragment;
import com.swein.framework.template.tabcustomslidinglayout.fragments.SubTwoFragment;
import com.swein.shandroidtoolutils.R;

import java.util.ArrayList;
import java.util.List;

public class TabCustomSlidingLayoutActivity extends FragmentActivity {

    private final static String TAG = "TabCustomSlidingLayoutActivity";

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private List<Fragment> fragmentList;
    private List<String> tabTitleList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_custom_sliding_layout);

        findView();
    }

    private void findView() {
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        tabTitleList = new ArrayList<>();
        tabTitleList.add("fragment 1");
        tabTitleList.add("fragment 2");
        tabTitleList.add("fragment 3");
        tabTitleList.add("fragment 1");
        tabTitleList.add("fragment 2");
        tabTitleList.add("fragment 3");

        fragmentList = new ArrayList<>();

        SubOneFragment subOneFragment = new SubOneFragment();
        SubTwoFragment subTwoFragment = new SubTwoFragment();
        SubThreeFragment subThreeFragment = new SubThreeFragment();
        fragmentList.add(subOneFragment);
        fragmentList.add(subTwoFragment);
        fragmentList.add(subThreeFragment);

        subOneFragment = new SubOneFragment();
        subTwoFragment = new SubTwoFragment();
        subThreeFragment = new SubThreeFragment();
        fragmentList.add(subOneFragment);
        fragmentList.add(subTwoFragment);
        fragmentList.add(subThreeFragment);

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager())
        {

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return tabTitleList.get(position);
            }

            @Override
            public int getCount()
            {
                return fragmentList.size();
            }

            @Override
            public android.support.v4.app.Fragment getItem(int arg0)
            {
                return fragmentList.get(arg0);
            }
        });

//        tabLayout.setTabMode (TabLayout.MODE_FIXED);
        tabLayout.setTabMode (TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorColor (Color.BLUE);
    }
}

package com.swein.framework.template.tabslidinghost.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.swein.framework.template.tabslidinghost.adapter.SHFragmentTabAdapter;
import com.swein.framework.template.tabslidinghost.subfragment.SHFriendFragment;
import com.swein.framework.template.tabslidinghost.subfragment.SHHomeFragment;
import com.swein.framework.template.tabslidinghost.subfragment.SHProfileFragment;
import com.swein.framework.template.tabslidinghost.subfragment.SHEventFragment;
import com.swein.shandroidtoolutils.R;

import java.util.ArrayList;
import java.util.List;

public class SHTabSlidingHostActivity extends FragmentActivity implements ViewPager.OnPageChangeListener, TabHost.OnTabChangeListener {

    private FragmentTabHost fragmentTabHost;
    private LayoutInflater layoutInflater;

    private List<Integer> imageResources;
    private List<String> stringResources;

    private List<Fragment> fragmentList;

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shtab_sliding_host);

        initConstants();

        findView();
        initPagerAdapter();
    }

    private void initConstants() {
        imageResources = new ArrayList<>();
        imageResources.add(R.drawable.tab_home_icon_state);
        imageResources.add(R.drawable.tab_event_icon_state);
        imageResources.add(R.drawable.tab_friend_icon_state);
        imageResources.add(R.drawable.tab_profile_icon_state);

        stringResources = new ArrayList<>();
        stringResources.add(getString(R.string.title_fragment_home));
        stringResources.add(getString(R.string.title_fragment_event));
        stringResources.add(getString(R.string.title_fragment_friends));
        stringResources.add(getString(R.string.title_fragment_profile));

        fragmentList = new ArrayList<>();
        SHHomeFragment shHomeFragment = new SHHomeFragment();
        SHEventFragment shEventFragment = new SHEventFragment();
        SHFriendFragment shFriendFragment = new SHFriendFragment();
        SHProfileFragment shProfileFragment = new SHProfileFragment();
        fragmentList.add(shHomeFragment);
        fragmentList.add(shEventFragment);
        fragmentList.add(shFriendFragment);
        fragmentList.add(shProfileFragment);
    }

    private void findView() {
        viewPager = (ViewPager) findViewById(R.id.pager);

        viewPager.addOnPageChangeListener(this);
        layoutInflater = LayoutInflater.from(this);


        fragmentTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        fragmentTabHost.setup(this, getSupportFragmentManager(), R.id.pager);

        fragmentTabHost.setOnTabChangedListener(this);

        int count = stringResources.size();

        for (int i = 0; i < count; i++) {

            TabHost.TabSpec tabSpec = fragmentTabHost.newTabSpec(stringResources.get(i)).setIndicator(getTabItemView(i));

            fragmentTabHost.addTab(tabSpec, fragmentList.get(i).getClass(), null);
            fragmentTabHost.setTag(i);

            fragmentTabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
        }
    }

    private void initPagerAdapter() {

        viewPager.setAdapter(new SHFragmentTabAdapter(getSupportFragmentManager(), fragmentList));
        fragmentTabHost.getTabWidget().setDividerDrawable(null);
    }

    private View getTabItemView(int i) {

        View view = layoutInflater.inflate(R.layout.tab_content, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.tab_imageview);
        TextView textView = (TextView) view.findViewById(R.id.tab_textview);

        imageView.setBackgroundResource(imageResources.get(i));
        textView.setText(stringResources.get(i));
        return view;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        TabWidget tabWidget = fragmentTabHost.getTabWidget();

        tabWidget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);

        fragmentTabHost.setCurrentTab(position);
        tabWidget.setDescendantFocusability(tabWidget.getDescendantFocusability());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onTabChanged(String tabId) {

        int position = fragmentTabHost.getCurrentTab();
        viewPager.setCurrentItem(position);

    }
}

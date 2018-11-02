package com.swein.framework.template.slidingtabs.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.swein.framework.template.slidingtabs.fragment.view.SlidingTabLayout;
import com.swein.framework.template.slidingtabs.fragment.viewpageradapter.SHSlidingTabViewPagerAdapter;
import com.swein.framework.template.slidingtabs.fragment.viewpageradapter.fragment.ViewPagerSampleFragment;
import com.swein.shandroidtoolutils.R;

import java.util.ArrayList;

public class SHSlidingTabViewPagerContainerFragment extends Fragment {

    private ArrayList<Fragment> list;

    private SlidingTabLayout slidingTabLayout;
    private ViewPager slidingViewPager;
    private SHSlidingTabViewPagerAdapter shSlidingTabViewPagerAdapter;
    private View rootView;

    public SHSlidingTabViewPagerContainerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_shsliding_tab_view_pager_container, container, false);

        initData();
        findView();

        return rootView;
    }

    private void findView() {

        slidingViewPager = rootView.findViewById(R.id.slidingViewPager);
        slidingTabLayout = rootView.findViewById(R.id.slidingTabLayout);

        shSlidingTabViewPagerAdapter = new SHSlidingTabViewPagerAdapter(getActivity().getSupportFragmentManager(), list);
        slidingViewPager.setAdapter(shSlidingTabViewPagerAdapter);

        slidingTabLayout.setViewPager(slidingViewPager);
    }

    private void initData() {
        list = new ArrayList<>();
        list.add(new ViewPagerSampleFragment());
        list.add(new ViewPagerSampleFragment());
        list.add(new ViewPagerSampleFragment());
        list.add(new ViewPagerSampleFragment());
        list.add(new ViewPagerSampleFragment());
    }

}

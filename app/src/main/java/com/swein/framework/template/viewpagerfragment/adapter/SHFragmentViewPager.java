package com.swein.framework.template.viewpagerfragment.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by seokho on 14/02/2018.
 */

public class SHFragmentViewPager extends FragmentPagerAdapter {

    private List<Fragment> fragmentList = new ArrayList<>();

    public SHFragmentViewPager(FragmentManager fm) {
        super(fm);
    }

    public void setFragmentList(List<Fragment> fragments) {
        fragmentList.clear();
        fragmentList.addAll(fragments);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}

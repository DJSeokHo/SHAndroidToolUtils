package com.swein.framework.template.tabslidinghost.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by seokho on 12/02/2018.
 */

public class SHFragmentTabAdapter extends FragmentPagerAdapter {

    private List<Fragment> list;

    public SHFragmentTabAdapter(FragmentManager fragmentManager, List<Fragment> list) {
        super(fragmentManager);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}

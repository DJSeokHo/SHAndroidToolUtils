package com.swein.framework.template.bottomnavigationbar.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class BottomNavigateViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> fragmentArrayList = new ArrayList<>();

    public BottomNavigateViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }

    public void addFragment(Fragment fragment) {
        fragmentArrayList.add(fragment);
    }

    public void clearFragment() {
        fragmentArrayList.clear();
    }

}

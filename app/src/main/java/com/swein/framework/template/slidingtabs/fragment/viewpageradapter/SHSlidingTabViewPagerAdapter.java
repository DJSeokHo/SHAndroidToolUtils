package com.swein.framework.template.slidingtabs.fragment.viewpageradapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by seokho on 05/01/2018.
 */

public class SHSlidingTabViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> list;

    public SHSlidingTabViewPagerAdapter(FragmentManager fm, ArrayList<Fragment> list) {
        super(fm);

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

    @Override
    public CharSequence getPageTitle(int position) {
        return "Tab Title ["+position+"]";
    }

}

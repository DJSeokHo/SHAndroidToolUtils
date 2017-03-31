package com.swein.tabhost.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by seokho on 31/03/2017.
 */

public class TabPagerAdapter extends FragmentPagerAdapter{

    List<Fragment> list;

    public TabPagerAdapter( FragmentManager fm, List<Fragment> list ) {
        super( fm );

        this.list = list;
    }

    @Override
    public Fragment getItem( int position ) {
        return list.get( position );
    }

    @Override
    public int getCount() {
        return list.size();
    }
}

package com.swein.tabhostandtablayout.viewpage.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by seokho on 31/03/2017.
 */

public class TabPagerAdapter extends FragmentPagerAdapter{

    List<Fragment> fragmentList;

    public TabPagerAdapter( FragmentManager fragmentManager, List<Fragment> fragmentList ) {
        super( fragmentManager );

        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem( int position ) {
        return fragmentList.get( position );
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}

package com.swein.tabhost.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.TabHost;

import com.swein.shandroidtoolutils.R;
import com.swein.tabhost.interfaces.TabHostInterface;


public class TabHostActivity extends FragmentActivity implements TabHostInterface {


    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_tab_host );



    }




    @Override
    public ViewPager.OnPageChangeListener onPageChangeListener() {
        return null;
    }

    @Override
    public TabHost.OnTabChangeListener onTabChangeListener() {
        return null;
    }
}

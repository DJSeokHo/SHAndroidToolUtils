package com.swein.tabhostandtablayout.interfaces;

import android.support.v4.view.ViewPager;
import android.widget.TabHost;

/**
 * Created by seokho on 31/03/2017.
 */

public interface TabHostInterface {

    ViewPager.OnPageChangeListener onPageChangeListener();

    TabHost.OnTabChangeListener onTabChangeListener();

}

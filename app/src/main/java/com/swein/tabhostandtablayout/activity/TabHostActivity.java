package com.swein.tabhostandtablayout.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;

import com.swein.shandroidtoolutils.R;
import com.swein.tabhostandtablayout.fragment.classes.EventFragment;
import com.swein.tabhostandtablayout.fragment.classes.FriendsFragment;
import com.swein.tabhostandtablayout.fragment.classes.HomeFragment;
import com.swein.tabhostandtablayout.fragment.classes.ProfileFragment;
import com.swein.tabhostandtablayout.interfaces.TabHostInterface;
import com.swein.tabhostandtablayout.viewholder.TabHostActivityViewHolder;
import com.swein.tabhostandtablayout.viewpage.adapter.TabPagerAdapter;

import java.util.ArrayList;
import java.util.List;


public class TabHostActivity extends FragmentActivity implements TabHostInterface {

    private FragmentTabHost tabHost;
    private String          currentTabId;
    private View            currentView;
    private LayoutInflater  inflate;

    private HomeFragment    homeFragment;
    private FriendsFragment friendsFragment;
    private EventFragment   eventFragment;
    private ProfileFragment profileFragment;

    private ViewPager        viewPager;
    private List< Fragment > fragmentList;

    private TabHostActivityViewHolder tabHostActivityViewHolder;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_tab_host );

        initFragment();
        initTabHost();
        initViewPager();

    }

    private void initFragment() {
        homeFragment = new HomeFragment();
        friendsFragment = new FriendsFragment();
        eventFragment = new EventFragment();
        profileFragment = new ProfileFragment();
    }

    private void initTabHost() {

        tabHostActivityViewHolder = new TabHostActivityViewHolder( this );

        fragmentList = new ArrayList<>();

        tabHost = (FragmentTabHost)findViewById( android.R.id.tabhost );
        tabHost.setup( this, getSupportFragmentManager(), android.R.id.tabcontent );

        // home fragment
        View homeView = tabHostActivityViewHolder.getTabView( getString( R.string.title_fragment_home ), R.drawable.home_fragment_icon_select, 0 );
        tabHost.addTab( tabHost.newTabSpec( getString( R.string.title_fragment_home ) ).setIndicator( homeView ), HomeFragment.class, null );
        tabHost.getTabWidget().getChildAt( 0 ).setBackgroundColor( Color.WHITE );
        fragmentList.add( homeFragment );

        // friends fragment
        View friendsView = tabHostActivityViewHolder.getTabView( getString( R.string.title_fragment_friends ), R.drawable.friends_fragment_icon_select, 1 );
        tabHost.addTab( tabHost.newTabSpec( getString( R.string.title_fragment_friends ) ).setIndicator( friendsView ), FriendsFragment.class, null );
        tabHost.getTabWidget().getChildAt( 1 ).setBackgroundColor( Color.WHITE );
        fragmentList.add( friendsFragment );

        // event fragment
        View eventView = tabHostActivityViewHolder.getTabView( getString( R.string.title_fragment_event ), R.drawable.event_fragment_icon_select, 2 );
        tabHost.addTab( tabHost.newTabSpec( getString( R.string.title_fragment_event ) ).setIndicator( eventView ), EventFragment.class, null );
        tabHost.getTabWidget().getChildAt( 2 ).setBackgroundColor( Color.WHITE );
        fragmentList.add( eventFragment );

        // profile fragment
        View profileView = tabHostActivityViewHolder.getTabView( getString( R.string.title_fragment_profile ), R.drawable.profile_fragment_icon_select, 3 );
        tabHost.addTab( tabHost.newTabSpec( getString( R.string.title_fragment_profile ) ).setIndicator( profileView ), ProfileFragment.class, null );
        tabHost.getTabWidget().getChildAt( 3 ).setBackgroundColor( Color.WHITE );
        fragmentList.add( profileFragment );

        // set home choose
        tabHostActivityViewHolder.setDefaultView( homeView, 0 );

        currentTabId = getString( R.string.title_fragment_home );
        currentView = homeView;

        tabHost.getTabWidget().setDividerDrawable( null );

        tabHost.setOnTabChangedListener( onTabChangeListener() );
    }

    private void initViewPager() {
        viewPager = (ViewPager)findViewById( R.id.itemViewPager );
        viewPager.setOnPageChangeListener( onPageChangeListener() );

        viewPager.setAdapter( new TabPagerAdapter(
                getSupportFragmentManager(), fragmentList ) );

        //set viewPager cache number
        viewPager.setOffscreenPageLimit( 3 );
    }


    @Override
    public ViewPager.OnPageChangeListener onPageChangeListener() {
        return new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged( int arg0 ) {
            }

            @Override
            public void onPageScrolled( int arg0, float arg1, int arg2 ) {
            }

            @Override
            public void onPageSelected( int arg0 ) {
                tabHost.setCurrentTab( arg0 );
            }
        };
    }

    @Override
    public TabHost.OnTabChangeListener onTabChangeListener() {
        return new TabHost.OnTabChangeListener() {

            @Override
            public void onTabChanged( String tabId ) {
                if ( !currentTabId.equals( tabId ) ) {

                    //set last page un-select icon
                    tabHostActivityViewHolder.setCurrentView( currentView, false, tabId );

                    currentView = tabHost.getCurrentTabView();

                    //set current page select icon
                    tabHostActivityViewHolder.setCurrentView( currentView, true, tabId );

                    currentTabId = tabId;

                }
                viewPager.setCurrentItem( tabHost.getCurrentTab() );
            }
        };
    }
}

package com.swein.framework.template.tabhost.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TabHost;

import com.swein.framework.template.tabhost.subfragment.SHTabHostSubFragmentFour;
import com.swein.framework.template.tabhost.subfragment.SHTabHostSubFragmentOne;
import com.swein.framework.template.tabhost.subfragment.SHTabHostSubFragmentThree;
import com.swein.framework.template.tabhost.subfragment.SHTabHostSubFragmentTwo;
import com.swein.shandroidtoolutils.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SHTabHostFragment extends Fragment implements TabHost.OnTabChangeListener{


    private Fragment[] fragments;
    private FragmentTabHost fragmentTabHost;
    private View rootView;

    private String          currentTapTag;

    private final static String HOME_TAB = "HOME_TAB";
    private final static String FRIEND_TAB = "FRIEND_TAB";
    private final static String EVENT_TAB = "EVENT_TAB";
    private final static String PROFILE_TAB = "PROFILE_TAB";

    public SHTabHostFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_shtab_host, container, false);
        findViews();
        return rootView;
    }

    void findViews() {
        fragmentTabHost = (FragmentTabHost) rootView.findViewById(android.R.id.tabhost);
        fragmentTabHost.setup(getActivity(), getActivity().getSupportFragmentManager(), R.id.shTabFragmentContainer);
        fragmentTabHost.setOnTabChangedListener(this);

        // add fragment tab
        SHTabHostSubFragmentOne shTabHostSubFragmentOne = new SHTabHostSubFragmentOne();
        SHTabHostSubFragmentTwo shTabHostSubFragmentTwo = new SHTabHostSubFragmentTwo();
        SHTabHostSubFragmentThree shTabHostSubFragmentThree = new SHTabHostSubFragmentThree();
        SHTabHostSubFragmentFour shTabHostSubFragmentFour = new SHTabHostSubFragmentFour();


        fragmentTabHost.addTab(fragmentTabHost.newTabSpec(HOME_TAB).setIndicator(tabButtonResource( 0, R.drawable.tab_home_icon_state )),
                shTabHostSubFragmentOne.getClass(), null);

        fragmentTabHost.addTab(fragmentTabHost.newTabSpec(FRIEND_TAB).setIndicator(tabButtonResource( 0, R.drawable.tab_friend_icon_state )),
                shTabHostSubFragmentTwo.getClass(), null);

        fragmentTabHost.addTab(fragmentTabHost.newTabSpec(EVENT_TAB).setIndicator(tabButtonResource( 0, R.drawable.tab_event_icon_state )),
                shTabHostSubFragmentThree.getClass(), null);

        fragmentTabHost.addTab(fragmentTabHost.newTabSpec(PROFILE_TAB).setIndicator(tabButtonResource( 0, R.drawable.tab_profile_icon_state )),
                shTabHostSubFragmentFour.getClass(), null);

        fragmentTabHost.getTabWidget().setDividerDrawable(null);

        fragmentTabHost.setCurrentTab(0);


    }

    View tabButtonResource(int titleId, int drawableId ) {
        ImageButton btn = new ImageButton( getActivity() );

        btn.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        btn.setBackgroundColor( Color.TRANSPARENT );
        btn.setId( android.R.id.content );
        btn.setImageResource( drawableId );

        return btn;
    }

    @Override
    public void onTabChanged(String tabId) {
        changeTab(tabId);
    }

    void changeTab( String tabId ) {

        if ( getActivity() == null || getFragmentManager() == null ) {
            return;
        }

        if ( HOME_TAB.compareTo( tabId ) == 0 ) {
            currentTapTag = tabId;

        }
        else if ( FRIEND_TAB.compareTo( tabId ) == 0 ) {
            currentTapTag = tabId;

        }
        else if ( EVENT_TAB.compareTo( tabId ) == 0 ) {
            currentTapTag = tabId;

        }
        else if ( PROFILE_TAB.compareTo( tabId ) == 0 ) {
            currentTapTag = tabId;
        }

        fragmentTabHost.setCurrentTabByTag( tabId );
    }

}

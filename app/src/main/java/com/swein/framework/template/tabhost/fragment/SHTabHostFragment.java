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
import com.swein.framework.tools.util.activity.ActivityUtil;
import com.swein.shandroidtoolutils.R;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class SHTabHostFragment extends Fragment {


    private View rootView;

    private final static String HOME_TAB = "HOME_TAB";
    private final static String FRIEND_TAB = "FRIEND_TAB";
    private final static String EVENT_TAB = "EVENT_TAB";
    private final static String PROFILE_TAB = "PROFILE_TAB";

    private ImageButton imageButtonHome;
    private ImageButton imageButtonFriend;
    private ImageButton imageButtonEvent;
    private ImageButton imageButtonProfile;

    private String currentTapTag = HOME_TAB;
    private String lastTapTag = "";

    public SHTabHostFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_shtab_host, container, false);
        findViews();
        changeTab(currentTapTag);
        return rootView;
    }

    void findViews() {

        imageButtonHome = rootView.findViewById(R.id.imageButtonHome);
        imageButtonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTab(HOME_TAB);
            }
        });

        imageButtonFriend = rootView.findViewById(R.id.imageButtonFriend);
        imageButtonFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTab(FRIEND_TAB);
            }
        });

        imageButtonEvent = rootView.findViewById(R.id.imageButtonEvent);
        imageButtonEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTab(EVENT_TAB);
            }
        });

        imageButtonProfile = rootView.findViewById(R.id.imageButtonProfile);
        imageButtonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTab(PROFILE_TAB);
            }
        });

    }

    void changeTab( String tabId ) {

        if (getFragmentManager() == null) {
            return;
        }

        resetTabState();

        Fragment currentFragment;
        Fragment lastFragment;

        if (HOME_TAB.compareTo(tabId) == 0) {
            currentTapTag = tabId;

            currentFragment = getFragmentManager().findFragmentByTag(currentTapTag);
            lastFragment = getFragmentManager().findFragmentByTag(lastTapTag);

            if(lastFragment != null) {
                ActivityUtil.hideFragment(getActivity(), lastFragment);
            }

            if(currentFragment == null) {
                currentFragment = new SHTabHostSubFragmentOne();
                ActivityUtil.addFragmentWithTAG(getActivity(), R.id.shTabFragmentContainer, currentFragment, currentTapTag);
            }
            else {
                ActivityUtil.showFragment(getActivity(), currentFragment);
            }

            lastTapTag = currentTapTag;
            imageButtonHome.setSelected(true);
        }
        else if(FRIEND_TAB.compareTo(tabId) == 0) {
            currentTapTag = tabId;

            currentFragment = getFragmentManager().findFragmentByTag(currentTapTag);
            lastFragment = getFragmentManager().findFragmentByTag(lastTapTag);

            if(lastFragment != null) {
                ActivityUtil.hideFragment(getActivity(), lastFragment);
            }

            if(currentFragment == null) {
                currentFragment = new SHTabHostSubFragmentTwo();
                ActivityUtil.addFragmentWithTAG(getActivity(), R.id.shTabFragmentContainer, currentFragment, currentTapTag);
            }
            else {
                ActivityUtil.showFragment(getActivity(), currentFragment);
            }

            lastTapTag = currentTapTag;

            imageButtonFriend.setSelected(true);
        }
        else if(EVENT_TAB.compareTo(tabId) == 0) {
            currentTapTag = tabId;

            currentFragment = getFragmentManager().findFragmentByTag(currentTapTag);
            lastFragment = getFragmentManager().findFragmentByTag(lastTapTag);

            if(lastFragment != null) {
                ActivityUtil.hideFragment(getActivity(), lastFragment);
            }

            if(currentFragment == null) {
                currentFragment = new SHTabHostSubFragmentThree();
                ActivityUtil.addFragmentWithTAG(getActivity(), R.id.shTabFragmentContainer, currentFragment, currentTapTag);
            }
            else {
                ActivityUtil.showFragment(getActivity(), currentFragment);
            }

            lastTapTag = currentTapTag;

            imageButtonEvent.setSelected(true);
        }
        else if(PROFILE_TAB.compareTo(tabId) == 0) {
            currentTapTag = tabId;

            currentFragment = getFragmentManager().findFragmentByTag(currentTapTag);
            lastFragment = getFragmentManager().findFragmentByTag(lastTapTag);

            if(lastFragment != null) {
                ActivityUtil.hideFragment(getActivity(), lastFragment);
            }

            if(currentFragment == null) {
                currentFragment = new SHTabHostSubFragmentFour();
                ActivityUtil.addFragmentWithTAG(getActivity(), R.id.shTabFragmentContainer, currentFragment, currentTapTag);
            }
            else {
                ActivityUtil.showFragment(getActivity(), currentFragment);
            }

            lastTapTag = currentTapTag;

            imageButtonProfile.setSelected(true);
        }
    }

    private void resetTabState() {
        imageButtonHome.setSelected(false);
        imageButtonFriend.setSelected(false);
        imageButtonEvent.setSelected(false);
        imageButtonProfile.setSelected(false);
    }

}

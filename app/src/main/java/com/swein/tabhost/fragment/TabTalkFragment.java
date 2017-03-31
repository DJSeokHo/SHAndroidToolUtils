package com.swein.tabhost.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.swein.shandroidtoolutils.R;

public class TabTalkFragment extends Fragment {

    public TabTalkFragment() {
    }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState ) {

        View rootView = inflater.inflate( R.layout.fragment_talk, container, false );
        return rootView;
    }

}

package com.swein.tabhost.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.swein.shandroidtoolutils.R;

public class TabHomeFragment extends Fragment {

    public TabHomeFragment() {
    }


    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState ) {
        View rootView = inflater.inflate( R.layout.fragment_home, container, false );
        return rootView;
    }

}

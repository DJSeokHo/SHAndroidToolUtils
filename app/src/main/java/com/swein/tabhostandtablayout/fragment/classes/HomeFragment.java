package com.swein.tabhostandtablayout.fragment.classes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.swein.shandroidtoolutils.R;
import com.swein.tabhostandtablayout.fragment.viewholder.HomeFragmentViewHolder;

public class HomeFragment extends Fragment {

    HomeFragmentViewHolder homeFragmentViewHolder;

    public HomeFragment() {
    }


    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        if ( getArguments() != null ) {
        }
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState ) {
        View rootView = inflater.inflate( R.layout.fragment_home, container, false );

        homeFragmentViewHolder = new HomeFragmentViewHolder( rootView );
        homeFragmentViewHolder.textViewSetText( getString(R.string.title_fragment_home) );

        return rootView;
    }

}

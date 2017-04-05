package com.swein.tabhostandtablayout.fragment.classes;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.swein.shandroidtoolutils.R;
import com.swein.tabhostandtablayout.fragment.viewholder.FriendsFragmentViewHolder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendsFragment extends Fragment {

    FriendsFragmentViewHolder friendsFragmentViewHolder;

    public FriendsFragment() {
        // Required empty public constructor
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
        View rootView = inflater.inflate( R.layout.fragment_friends, container, false );

        friendsFragmentViewHolder = new FriendsFragmentViewHolder( rootView );
        friendsFragmentViewHolder.textViewSetText( getString(R.string.title_fragment_friends) );

        return rootView;
    }

}

package com.swein.tabhostandtablayout.fragment.classes;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.swein.shandroidtoolutils.R;
import com.swein.tabhostandtablayout.fragment.viewholder.EventFragmentViewHolder;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventFragment extends Fragment {

    EventFragmentViewHolder eventFragmentViewHolder;

    public EventFragment() {
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

        View rootView = inflater.inflate( R.layout.fragment_event, container, false );

        eventFragmentViewHolder = new EventFragmentViewHolder( rootView );
        eventFragmentViewHolder.textViewSetText( getString(R.string.title_fragment_event) );

        return rootView;
    }

}

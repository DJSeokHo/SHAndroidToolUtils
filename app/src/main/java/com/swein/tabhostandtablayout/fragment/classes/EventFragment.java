package com.swein.tabhostandtablayout.fragment.classes;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.shandroidtoolutils.R;
import com.swein.tabhostandtablayout.fragment.viewholder.EventFragmentViewHolder;

import java.util.List;

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

        try {
            String[] strings = new String[] {"1", "2"};
            ILog.iLogDebug( getContext(), strings[5] );
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }

        try {
            List list = null;
            list.get( 5 );
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }

        try {
            int one = 1;
            int zero = 0;
            int result = one / zero;
            ILog.iLogDebug( getContext(), result );
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }

        return rootView;
    }

}

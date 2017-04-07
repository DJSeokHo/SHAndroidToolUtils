package com.swein.tabhostandtablayout.fragment.viewholder;

import android.view.View;
import android.widget.TextView;

import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.shandroidtoolutils.R;
import com.swein.tabhostandtablayout.fragment.delegate.ProfileFragmentDelegate;

import java.util.List;

/**
 * Created by seokho on 05/04/2017.
 */

public class ProfileFragmentViewHolder {

    private TextView textView;

    private ProfileFragmentDelegate profileFragmentDelegate;

    public ProfileFragmentViewHolder( View itemView ) {

        findView(itemView);

    }

    private void findView(View view) {
        textView = (TextView)view.findViewById( R.id.profileTextView );
    }


    public void textViewSetText( String string ) {

        textView.setText(string);

        try {
            String[] strings = new String[] {"1", "2"};
            ILog.iLogDebug( "ProfileFragmentViewHolder", strings[5] );
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
            ILog.iLogDebug( "ProfileFragmentViewHolder", result );
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }

    }

    public void doSomething() {
        profileFragmentDelegate.doSomething();
    }
}

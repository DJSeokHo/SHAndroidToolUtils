package com.swein.tabhostandtablayout.fragment.viewholder;

import android.view.View;
import android.widget.TextView;

import com.swein.shandroidtoolutils.R;
import com.swein.tabhostandtablayout.fragment.delegate.ProfileFragmentDelegate;

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

    }

    public void doSomething() {
        profileFragmentDelegate.doSomething();
    }
}

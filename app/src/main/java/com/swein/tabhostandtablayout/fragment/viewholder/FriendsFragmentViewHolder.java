package com.swein.tabhostandtablayout.fragment.viewholder;

import android.view.View;
import android.widget.TextView;

import com.swein.shandroidtoolutils.R;
import com.swein.tabhostandtablayout.fragment.delegate.FriendsFragmentDelegate;

/**
 * Created by seokho on 05/04/2017.
 */

public class FriendsFragmentViewHolder {

    private TextView textView;

    private FriendsFragmentDelegate friendsFragmentDelegate;

    public FriendsFragmentViewHolder( View itemView ) {

        findView(itemView);

    }

    private void findView(View view) {
        textView = (TextView)view.findViewById( R.id.friendsTextView );
    }


    public void textViewSetText( String string ) {

        textView.setText(string);

    }

    public void doSomething() {
        friendsFragmentDelegate.doSomething();
    }
}

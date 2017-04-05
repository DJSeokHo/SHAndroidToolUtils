package com.swein.tabhostandtablayout.fragment.viewholder;

import android.view.View;
import android.widget.TextView;

import com.swein.shandroidtoolutils.R;
import com.swein.tabhostandtablayout.fragment.delegate.HomeFragmentDelegate;

/**
 * Created by seokho on 05/04/2017.
 */

public class HomeFragmentViewHolder {

    private TextView textView;

    private HomeFragmentDelegate homeFragmentDelegate;

    public HomeFragmentViewHolder( View itemView ) {

        findView(itemView);

    }

    private void findView(View view) {
        textView = (TextView)view.findViewById( R.id.homeTextView );
    }


    public void textViewSetText( String string ) {

        textView.setText(string);

    }

    public void doSomething() {
        homeFragmentDelegate.doSomething();
    }
}

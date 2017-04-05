package com.swein.tabhostandtablayout.fragment.viewholder;

import android.view.View;
import android.widget.TextView;

import com.swein.shandroidtoolutils.R;
import com.swein.tabhostandtablayout.fragment.delegate.EventFragmentDelegate;

/**
 * Created by seokho on 05/04/2017.
 */

public class EventFragmentViewHolder {

    private TextView         textView;

    private EventFragmentDelegate eventFragmentDelegate;

    public EventFragmentViewHolder( View itemView ) {

        findView(itemView);

    }

    private void findView(View view) {
        textView = (TextView)view.findViewById( R.id.eventTextView );
    }


    public void textViewSetText( String string ) {

        textView.setText(string);

    }

    public void doSomething() {
        eventFragmentDelegate.doSomething();
    }

}

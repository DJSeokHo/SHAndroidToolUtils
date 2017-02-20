package com.swein.listview.pattern.delegate.viewholder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.swein.shandroidtoolutils.R;

/**
 * Created by seokho on 20/02/2017.
 */

public class ListViewItemViewHolder {

    public TextView textView;
    public Button buttonSet;
    public Button buttonDelete;

    public void findView(View rootView) {
        textView = (TextView) rootView.findViewById(R.id.textView);
        buttonSet = (Button) rootView.findViewById(R.id.buttonSet);
        buttonDelete = (Button) rootView.findViewById(R.id.buttonDelete);
    }

}

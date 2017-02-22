package com.swein.listview.pattern.delegate.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.swein.shandroidtoolutils.R;

/**
 * Created by seokho on 20/02/2017.
 */

public class ListViewItemViewHolder {

    private TextView textView;
    private Button buttonSet;
    private Button buttonDelete;

    private View rootView;

    private LayoutInflater layoutInflater;

    public ListViewItemViewHolder(Context context) {
        layoutInflater = LayoutInflater.from(context);
        rootView = layoutInflater.inflate(R.layout.list_view_item, null);
    }

    public void findView() {
        textView = (TextView) rootView.findViewById(R.id.textView);
        buttonSet = (Button) rootView.findViewById(R.id.buttonSet);
        buttonDelete = (Button) rootView.findViewById(R.id.buttonDelete);
    }

    public void textViewSetText(String string) {
        textView.setText( string );
    }

    public void buttonSetOnClick(View.OnClickListener onClickListener) {
        buttonSet.setOnClickListener( onClickListener );
    }

    public void buttonDeleteOnClick(View.OnClickListener onClickListener) {
        buttonDelete.setOnClickListener( onClickListener );
    }

    public View returnRootview() {
        if(null != rootView) {
            return rootView;
        }

        return null;
    }

}

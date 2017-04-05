package com.swein.tabhostandtablayout.viewholder;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.swein.shandroidtoolutils.R;

import static com.swein.tabhostandtablayout.content.ContentResource.setSelectImageResource;
import static com.swein.tabhostandtablayout.content.ContentResource.setUnSelectImageResource;

/**
 * Created by seokho on 05/04/2017.
 */

public class TabHostActivityViewHolder {


    private LayoutInflater  inflate;
    private Context         context;

    public TabHostActivityViewHolder(Context context) {

        inflate = LayoutInflater.from(context);
        this.context = context;

    }

    public View getTabView(String title, int resourceID, int position) {
        // home fragment
        View view = getTabItemView(title, resourceID);
        itemChooseStatus(view, false, position);

        return view;
    }

    public void setDefaultView(View view, int position) {
        itemChooseStatus(view, true, position);
    }

    public void setCurrentView(View view, boolean isChoose, String tabID) {
        itemChooseStatus(view, isChoose, getViewPosition(tabID));
    }

    private void itemChooseStatus(View view, boolean isChoose, int position) {
        if (isChoose) {
            ((TextView) view.findViewById( R.id.textview)).setTextColor( Color.BLACK );
            setSelectImageResource(view, R.id.imageview, position);
        } else {
            ((TextView) view.findViewById(R.id.textview)).setTextColor( Color.GRAY );
            setUnSelectImageResource(view, R.id.imageview, position);
        }
        view.findViewById(R.id.imageview).setEnabled(!isChoose);
    }

    /**
     * set button icon and text
     */
    private View getTabItemView(String title, int iconResource) {
        View view = inflate.inflate(R.layout.tab_item_view, null);

        ImageView imageView = (ImageView) view.findViewById( R.id.imageview);
        imageView.setImageResource(iconResource);

        TextView textView = (TextView) view.findViewById(R.id.textview);
        textView.setText(title);

        return view;
    }

    private int getViewPosition(String tabId) {

        if(tabId.equals( this.context.getString( R.string.title_fragment_home ) )) {
            return 0;
        }
        else if(tabId.equals( this.context.getString( R.string.title_fragment_friends ) )) {
            return 1;
        }
        else if(tabId.equals( this.context.getString( R.string.title_fragment_event ) )) {
            return 2;
        }
        else if(tabId.equals( this.context.getString( R.string.title_fragment_profile ) )) {
            return 3;
        }

        return 0;
    }

}


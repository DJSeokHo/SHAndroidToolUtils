package com.swein.listview.pattern.delegate.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.swein.listview.pattern.delegate.interfaces.ListViewDelegator;
import com.swein.listview.pattern.delegate.viewholder.ListViewItemViewHolder;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by seokho on 15/02/2017.
 */

public class ListViewAdapter extends BaseAdapter {

    private Context context;

    private ArrayList<HashMap<String, String>> data;

    private ListViewDelegator listViewDelegator;

    public void setListViewDelegator(ListViewDelegator listViewDelegator){
        this.listViewDelegator = listViewDelegator;
    }

    public ListViewAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<HashMap<String, String>> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ListViewItemViewHolder listViewItemViewHolder;

        if(null == convertView) {

            listViewItemViewHolder = new ListViewItemViewHolder(context);

            listViewItemViewHolder.findView();

            convertView = listViewItemViewHolder.returnRootview();

            convertView.setTag(listViewItemViewHolder);

        }
        else {
            listViewItemViewHolder = (ListViewItemViewHolder)convertView.getTag();
        }

        listViewItemViewHolder.textViewSetText( data.get(position).get("itemTitle").toString() );

        listViewItemViewHolder.buttonSetOnClick( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                listViewDelegator.setViewText(data.get(position).get("itemTitle").toString());
            }
        } );

        listViewItemViewHolder.buttonDeleteOnClick( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                listViewDelegator.deleteListItem(position);
                notifyDataSetChanged();
            }
        } );

        return convertView;
    }
}

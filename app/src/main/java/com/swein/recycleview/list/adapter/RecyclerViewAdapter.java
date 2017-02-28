package com.swein.recycleview.list.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.recycleview.list.data.RecyclerViewListData;
import com.swein.recycleview.list.delegator.RecyclerViewListDelegator;
import com.swein.recycleview.list.viewholder.RecyclerViewListItemViewHolder;
import com.swein.shandroidtoolutils.R;

/**
 * Created by seokho on 28/02/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter< RecyclerViewListItemViewHolder > {

    private Context context;

    private RecyclerViewListDelegator recyclerViewListDelegator;

    public RecyclerViewAdapter( Context context, RecyclerViewListDelegator recyclerViewListDelegator ) {
        this.context = context;
        this.recyclerViewListDelegator = recyclerViewListDelegator;
    }

    @Override
    public RecyclerViewListItemViewHolder onCreateViewHolder( ViewGroup parent, int viewType ) {

        RecyclerViewListItemViewHolder recyclerViewListItemViewHolder = new RecyclerViewListItemViewHolder(
            LayoutInflater.from( context ).inflate( R.layout.activity_recycler_view_list_item, parent, false )
        );

        return recyclerViewListItemViewHolder;
    }

    @Override
    public void onBindViewHolder( RecyclerViewListItemViewHolder recyclerViewListItemViewHolder, final int position ) {


        recyclerViewListItemViewHolder.textViewSetText( RecyclerViewListData.getInstance().getList().get( position ) );
        recyclerViewListItemViewHolder.buttonViewSetOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                ILog.iLogDebug( RecyclerViewAdapter.class.getSimpleName(), position );
                recyclerViewListDelegator.deleteListItem( position );
            }
        } );

        recyclerViewListItemViewHolder.textViewSetOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                ILog.iLogDebug( RecyclerViewAdapter.class.getSimpleName(), RecyclerViewListData.getInstance().getList().get( position ) );
            }
        } );


    }

    @Override
    public int getItemCount() {
        return RecyclerViewListData.getInstance().getList().size();
    }


}

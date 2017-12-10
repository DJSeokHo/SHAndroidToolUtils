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

public class RecyclerViewAdapter extends RecyclerView.Adapter {

    private Context context;

    private RecyclerViewListDelegator recyclerViewListDelegator;

    private static final int TYPE_EMPTY_HEADER = 0;
    private static final int TYPE_HEADER       = 1;
    private static final int TYPE_ITEM         = 2;

    public boolean shouldShowHeader  = false;
    public boolean useEmptyHeader    = false;
    public int     emptyHeaderHeight = 0;


    public RecyclerViewAdapter( Context context, RecyclerViewListDelegator recyclerViewListDelegator ) {
        this.context = context;
        this.recyclerViewListDelegator = recyclerViewListDelegator;
    }

    @Override
    public RecyclerViewListItemViewHolder onCreateViewHolder( ViewGroup parent, int viewType ) {

        RecyclerViewListItemViewHolder recyclerViewListItemViewHolder = new RecyclerViewListItemViewHolder(
            LayoutInflater.from( parent.getContext() ).inflate( R.layout.activity_recycler_view_list_item, parent, false )
        );

        return recyclerViewListItemViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if ( holder instanceof RecyclerViewListItemViewHolder ) {
            if ( useEmptyHeader ) {
                position -= 1;
            }

            RecyclerViewListItemViewHolder recyclerViewListItemViewHolder = (RecyclerViewListItemViewHolder) holder;

            recyclerViewListItemViewHolder.textViewSetText( RecyclerViewListData.getInstance().getList().get( position ) );

            ILog.iLogDebug( RecyclerViewAdapter.class.getSimpleName(), position );

            final int finalPosition = position;

            recyclerViewListItemViewHolder.buttonViewSetOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick( View view ) {

                    recyclerViewListDelegator.deleteListItem(finalPosition);
                }
            } );
        }
    }


    @Override
    public int getItemCount() {
        return RecyclerViewListData.getInstance().getList().size();
    }


}

package com.swein.recycleview.random.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.swein.recycleview.random.data.RecyclerViewRandomData;
import com.swein.recycleview.random.delegator.RecyclerViewRandomDelegator;
import com.swein.recycleview.random.viewholder.RecyclerViewRandomItemViewHolder;
import com.swein.shandroidtoolutils.R;

/**
 * Created by seokho on 02/03/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewRandomItemViewHolder > {

    private Context context;
    private RecyclerViewRandomDelegator recyclerViewRandomDelegator;

    public RecyclerViewAdapter(Context context, RecyclerViewRandomDelegator recyclerViewRandomDelegator) {
        this.context = context;
        this.recyclerViewRandomDelegator = recyclerViewRandomDelegator;
    }

    @Override
    public RecyclerViewRandomItemViewHolder onCreateViewHolder( ViewGroup parent, int viewType ) {

        RecyclerViewRandomItemViewHolder recyclerViewRandomItemViewHolder = new RecyclerViewRandomItemViewHolder(
                LayoutInflater.from( context ).inflate( R.layout.activity_recycler_view_random_item, parent, false )
        );

        return recyclerViewRandomItemViewHolder;

    }

    @Override
    public void onBindViewHolder( RecyclerViewRandomItemViewHolder recyclerViewRandomItemViewHolder, final int position ) {

        recyclerViewRandomItemViewHolder.textViewSetText( RecyclerViewRandomData.getInstance().getList().get( position ) );
        recyclerViewRandomItemViewHolder.imageViewSetImageResource( R.drawable.image_button_unchecked );
        recyclerViewRandomItemViewHolder.tagCloudItemSetOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                recyclerViewRandomDelegator.onItemClicked(position);
            }
        } );
    }

    @Override
    public int getItemCount() {
        return RecyclerViewRandomData.getInstance().getList().size();
    }
}

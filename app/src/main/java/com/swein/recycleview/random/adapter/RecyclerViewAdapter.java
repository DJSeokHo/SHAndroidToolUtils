package com.swein.recycleview.random.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.swein.framework.module.googleanalytics.manager.TrackerManager;
import com.swein.recycleview.random.data.RecyclerViewRandomData;
import com.swein.recycleview.random.delegate.RecyclerViewRandomDelegate;
import com.swein.recycleview.random.viewholder.RecyclerViewRandomItemViewHolder;
import com.swein.shandroidtoolutils.R;

import static com.swein.recycleview.random.activity.RecyclerViewRandomActivity.ALL;
import static com.swein.recycleview.random.activity.RecyclerViewRandomActivity.NORMAL;
import static com.swein.recycleview.random.activity.RecyclerViewRandomActivity.SELECT;
import static com.swein.recycleview.random.activity.RecyclerViewRandomActivity.checkState;

/**
 * Created by seokho on 02/03/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewRandomItemViewHolder > {

    private Context                    context;    //Context
    private RecyclerViewRandomDelegate recyclerViewRandomDelegate;    //Delegator for Controller

    public RecyclerViewAdapter(Context context, RecyclerViewRandomDelegate recyclerViewRandomDelegate ) {
        this.context = context;
        this.recyclerViewRandomDelegate = recyclerViewRandomDelegate;
    }

    @Override
    public RecyclerViewRandomItemViewHolder onCreateViewHolder( ViewGroup parent, int viewType ) {

        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT );

        parent.setLayoutParams( layoutParams );

        RecyclerViewRandomItemViewHolder recyclerViewRandomItemViewHolder = new RecyclerViewRandomItemViewHolder(
                LayoutInflater.from( context ).inflate( R.layout.activity_recycler_view_random_item, parent, false )
        );

        return recyclerViewRandomItemViewHolder;

    }

    /**
     * refresh list and check item state (be selected or not) in selection mode
     * @param recyclerViewRandomItemViewHolder
     * @param position
     */
    private void tagCloudCheckStateListener(RecyclerViewRandomItemViewHolder recyclerViewRandomItemViewHolder, int position) {
        switch ( checkState ) {
            case NORMAL:
                recyclerViewRandomItemViewHolder.hideImageView();
                break;

            case SELECT:
                recyclerViewRandomItemViewHolder.showImageView();
                if(RecyclerViewRandomData.getInstance().getList().get( position ).tagCheckState) {
                    recyclerViewRandomItemViewHolder.setImageViewChecked();
                }
                else {
                    recyclerViewRandomItemViewHolder.setImageViewUnChecked();
                }
                break;

            case ALL:
                recyclerViewRandomItemViewHolder.showImageView();
                if(RecyclerViewRandomData.getInstance().getList().get( position ).tagCheckState) {
                    recyclerViewRandomItemViewHolder.setImageViewChecked();
                }
                else {
                    recyclerViewRandomItemViewHolder.setImageViewUnChecked();
                }
                break;
        }
    }

    @Override
    public void onBindViewHolder( final RecyclerViewRandomItemViewHolder recyclerViewRandomItemViewHolder, final int position ) {

        tagCloudCheckStateListener(recyclerViewRandomItemViewHolder, position);

        recyclerViewRandomItemViewHolder.tagCloudItemLayoutSetBackGround();
        recyclerViewRandomItemViewHolder.textViewSetText( RecyclerViewRandomData.getInstance().getList().get( position ).tagName );

        recyclerViewRandomItemViewHolder.backgroundImageViewSetImage(position);

        recyclerViewRandomItemViewHolder.tagCloudItemLayoutSetOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick( View view ) {

                TrackerManager.sendEventReport( context, "Operate", "click", RecyclerViewRandomData.getInstance().getList().get( position ).tagName, position, true );

                if(!checkState.equals( NORMAL )) {  //select mode
                    recyclerViewRandomDelegate.setItemCheckState( RecyclerViewRandomData.getInstance().getList().get( position ) );
                    recyclerViewRandomItemViewHolder.tagCloudItemSetCheckState( RecyclerViewRandomData.getInstance().getList().get( position ).tagCheckState );
                }
                else {  //single click mode
                    recyclerViewRandomDelegate.singleTagSearch( RecyclerViewRandomData.getInstance().getList().get( position ));
                    recyclerViewRandomItemViewHolder.hideImageView();
                }

            }
        } );
    }

    @Override
    public int getItemCount() {
        return RecyclerViewRandomData.getInstance().getList().size();
    }
}



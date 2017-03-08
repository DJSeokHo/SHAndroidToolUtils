package com.swein.recycleview.random.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.swein.shandroidtoolutils.R;

/**
 * Created by seokho on 02/03/2017.
 */

public class RecyclerViewRandomItemViewHolder extends RecyclerView.ViewHolder {

    private TextView        recyclerViewRandomItemTextView;
    private ImageView       recyclerViewRandomItemCheckImageView;
    private RelativeLayout  tagCloudItem;

    public RecyclerViewRandomItemViewHolder( View itemView ) {
        super( itemView );

        recyclerViewRandomItemTextView = (TextView)itemView.findViewById( R.id.recyclerViewRandomItemTextView );
        recyclerViewRandomItemCheckImageView = (ImageView)itemView.findViewById( R.id.recyclerViewRandomItemCheckImageView );
        tagCloudItem = (RelativeLayout)itemView.findViewById( R.id.tagCloudItem );
    }

    public void textViewSetText( String string ) {
        recyclerViewRandomItemTextView.setText( string );
        recyclerViewRandomItemTextView.setSingleLine();
    }
    public void showImageView() {
        recyclerViewRandomItemCheckImageView.setVisibility( View.VISIBLE );
    }

    public void hideImageView() {
        recyclerViewRandomItemCheckImageView.setVisibility( View.GONE );
    }

    public void setImageViewChecked() {
        recyclerViewRandomItemCheckImageView.setImageResource(R.drawable.recyclerview_random_item_checked );
    }

    public void setImageViewUnChecked() {
        recyclerViewRandomItemCheckImageView.setImageResource(R.drawable.recyclerview_random_item_unchecked );
    }

    public void tagCloudItemLayoutSetBackGround() {
        tagCloudItem.setBackgroundResource( R.drawable.recycler_view_random_item_custom_textview_background );
    }

    public void tagCloudItemLayoutSetOnClickListener(View.OnClickListener onClickListener) {
        tagCloudItem.setOnClickListener( onClickListener );
    }

    public void tagCloudItemSetCheckState(boolean checkState) {

        if(checkState) {
            showImageView();
            setImageViewChecked();
        }
        else {
            showImageView();
            setImageViewUnChecked();
        }

    }

    public void hideViewHolder() {
        itemView.setVisibility( View.GONE );
    }


}
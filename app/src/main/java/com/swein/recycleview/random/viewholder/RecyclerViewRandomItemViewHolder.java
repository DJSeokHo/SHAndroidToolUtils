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

        findView();

    }

    private void findView() {
        recyclerViewRandomItemTextView = (TextView)itemView.findViewById( R.id.recyclerViewRandomItemTextView );
        recyclerViewRandomItemCheckImageView = (ImageView)itemView.findViewById( R.id.recyclerViewRandomItemCheckImageView );
        tagCloudItem = (RelativeLayout)itemView.findViewById( R.id.tagCloudItem );
    }


    public void textViewSetText( String string ) {
        recyclerViewRandomItemTextView.setText( string );
        recyclerViewRandomItemTextView.setSingleLine();
    }

    //show check box
    public void showImageView() {
        recyclerViewRandomItemCheckImageView.setVisibility( View.VISIBLE );
    }

    //hide check box
    public void hideImageView() {
        recyclerViewRandomItemCheckImageView.setVisibility( View.GONE );
    }

    //set checked
    public void setImageViewChecked() {
        recyclerViewRandomItemCheckImageView.setImageResource(R.drawable.recyclerview_random_item_checked );
    }

    //set unchecked
    public void setImageViewUnChecked() {
        recyclerViewRandomItemCheckImageView.setImageResource(R.drawable.recyclerview_random_item_unchecked );
    }

    //set item background
    public void tagCloudItemLayoutSetBackGround() {
        tagCloudItem.setBackgroundResource( R.drawable.recycler_view_random_item_custom_textview_background );
    }

    //set item onclick
    public void tagCloudItemLayoutSetOnClickListener(View.OnClickListener onClickListener) {
        tagCloudItem.setOnClickListener( onClickListener );
    }

    //set item check or not
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

    //hide item
    public void hideViewHolder() {
        itemView.setVisibility( View.GONE );
    }


}
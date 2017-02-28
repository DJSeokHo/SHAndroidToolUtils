package com.swein.recycleview.list.viewholder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.swein.shandroidtoolutils.R;

import static android.support.v7.widget.RecyclerView.ViewHolder;

/**
 * Created by seokho on 28/02/2017.
 */

public class RecyclerViewListItemViewHolder extends ViewHolder {

    private TextView recyclerViewListItemTextView;
    private Button recyclerViewListItemButton;

    public RecyclerViewListItemViewHolder( View itemView ) {
        super( itemView );

        recyclerViewListItemTextView = (TextView)itemView.findViewById( R.id.recyclerViewListItemTextView );
        recyclerViewListItemButton = (Button)itemView.findViewById( R.id.recyclerViewListItemButton );

    }

    public void textViewSetText(String string) {
        recyclerViewListItemTextView.setText( string );
    }

    public void textViewSetOnClickListener(View.OnClickListener onClickListener) {
        recyclerViewListItemTextView.setOnClickListener( onClickListener );
    }

    public void buttonViewSetOnClickListener(View.OnClickListener onClickListener) {
        recyclerViewListItemButton.setOnClickListener( onClickListener );
    }




}

package com.swein.recycleview.list.viewholder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.recycleview.list.data.RecyclerViewListData;
import com.swein.shandroidtoolutils.R;

import static android.support.v7.widget.RecyclerView.ViewHolder;

/**
 * Created by seokho on 28/02/2017.
 */

public class RecyclerViewListItemViewHolder extends ViewHolder implements View.OnClickListener {

    private TextView recyclerViewListItemTextView;
    private Button recyclerViewListItemButton;

    public RecyclerViewListItemViewHolder( final View itemView ) {
        super( itemView );

        recyclerViewListItemTextView = (TextView)itemView.findViewById( R.id.recyclerViewListItemTextView );
        recyclerViewListItemButton = (Button)itemView.findViewById( R.id.recyclerViewListItemButton );

        itemView.setOnClickListener( this );
    }

    public void textViewSetText(String string) {
        recyclerViewListItemTextView.setText( string );
    }



    public void buttonViewSetOnClickListener(View.OnClickListener onClickListener) {
        recyclerViewListItemButton.setOnClickListener( onClickListener );
    }


    @Override
    public void onClick( View view ) {
        ILog.iLogDebug( RecyclerViewListItemViewHolder.class.getSimpleName(),
                        RecyclerViewListData.getInstance().getList().get( getAdapterPosition() ) );
    }
}

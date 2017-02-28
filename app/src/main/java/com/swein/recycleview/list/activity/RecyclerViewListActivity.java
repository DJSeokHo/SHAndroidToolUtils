package com.swein.recycleview.list.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.swein.recycleview.list.adapter.RecyclerViewAdapter;
import com.swein.recycleview.list.data.RecyclerViewListData;
import com.swein.recycleview.list.decoration.RecyclerViewListDecoration;
import com.swein.recycleview.list.delegator.RecyclerViewListDelegator;
import com.swein.shandroidtoolutils.R;

public class RecyclerViewListActivity extends AppCompatActivity implements RecyclerViewListDelegator{

    private RecyclerView recyclerViewList;

    private RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_recycler_view_list );

        RecyclerViewListData.getInstance().initList();

        recyclerViewAdapter = new RecyclerViewAdapter( this, this );

        recyclerViewList = (RecyclerView)findViewById( R.id.recycleViewList );
        recyclerViewList.addItemDecoration(new RecyclerViewListDecoration( this, RecyclerViewListDecoration.VERTICAL_LIST));
        recyclerViewList.setLayoutManager(new LinearLayoutManager( this));
        recyclerViewList.setAdapter(recyclerViewAdapter);
        recyclerViewList.setItemAnimator(new DefaultItemAnimator());

    }

    @Override
    public void deleteListItem( int position ) {
        RecyclerViewListData.getInstance().removeListItem( position );
        recyclerViewAdapter.notifyItemRemoved( position );
        //rebind view position after remove item
        recyclerViewAdapter.notifyItemRangeChanged( position, RecyclerViewListData.getInstance().getList().size() );
    }
}

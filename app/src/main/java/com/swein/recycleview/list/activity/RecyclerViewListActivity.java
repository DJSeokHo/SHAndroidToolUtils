package com.swein.recycleview.list.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.Thing;
import com.swein.framework.module.googleanalytics.manager.TrackerManager;
import com.swein.framework.tools.util.thread.ThreadUtils;
import com.swein.recycleview.list.adapter.RecyclerViewAdapter;
import com.swein.recycleview.list.data.RecyclerViewListData;
import com.swein.recycleview.list.decoration.RecyclerViewListDecoration;
import com.swein.recycleview.list.delegator.RecyclerViewListDelegator;
import com.swein.shandroidtoolutils.R;

public class RecyclerViewListActivity extends AppCompatActivity implements RecyclerViewListDelegator, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerViewList;

    private RecyclerViewAdapter recyclerViewAdapter;
    private SwipeRefreshLayout  swipeRefreshLayout;

    private LinearLayoutManager linearLayoutManager;
    private int lastVisibleItem;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_recycler_view_list );
        TrackerManager.sendScreenViewReport( this );

        linearLayoutManager = new LinearLayoutManager( this );

        RecyclerViewListData.getInstance().initList();

        recyclerViewAdapter = new RecyclerViewAdapter( this, this );

        swipeRefreshLayout = (SwipeRefreshLayout)findViewById( R.id.swipeRefreshLayoutList );


        swipeRefreshLayout.setOnRefreshListener( this );

        //first enter page to show progress bar
        swipeRefreshLayout.setProgressViewOffset( false, 0, (int)TypedValue
                .applyDimension( TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics() ) );


        recyclerViewList = (RecyclerView)findViewById( R.id.recyclerViewList );
        recyclerViewList.addItemDecoration( new RecyclerViewListDecoration( this, RecyclerViewListDecoration.VERTICAL_LIST ) );
        recyclerViewList.setLayoutManager( linearLayoutManager );
        recyclerViewList.setAdapter( recyclerViewAdapter );
        recyclerViewList.setItemAnimator( new DefaultItemAnimator() );

        recyclerViewList.addOnScrollListener( new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged( RecyclerView recyclerView, int newState ) {
                super.onScrollStateChanged( recyclerView, newState );

                TrackerManager.sendEventReport( RecyclerViewListActivity.this, "Operate", "onScrollStateChanged", false );

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                int totalItemCount = recyclerView.getAdapter().getItemCount();
                int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                int visibleItemCount = recyclerView.getChildCount();

                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItemPosition == totalItemCount - 1
                        && visibleItemCount > 0) {

                    // load more here
                    RecyclerViewListData.getInstance().loadList();
                    ThreadUtils.createThreadWithUI( 0, new Runnable() {
                        @Override
                        public void run() {
                            RecyclerViewListData.getInstance().loadList();
                            recyclerViewAdapter.notifyDataSetChanged();
                        }
                    } );
                }

//                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1
//                        == RecyclerViewListData.getInstance().getList().size()) {
//
//                    //load more data
//                    ThreadUtils.createThreadWithUI( 0, new Runnable() {
//                        @Override
//                        public void run() {
//                            RecyclerViewListData.getInstance().loadList();
//                            recyclerViewAdapter.notifyDataSetChanged();
//                        }
//                    } );
//
//                }
            }

            @Override
            public void onScrolled( RecyclerView recyclerView, int dx, int dy ) {
                super.onScrolled( recyclerView, dx, dy );

                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

            }
        } );
    }

    @Override
    public void deleteListItem( int position ) {

        TrackerManager.sendEventReport( this, "Operate", "deleteListItem " + RecyclerViewListData.getInstance().getList().get( position ), false );

        RecyclerViewListData.getInstance().removeListItem( position );

        recyclerViewAdapter.notifyItemRemoved( position );
        //rebind view position after remove item
        recyclerViewAdapter.notifyItemRangeChanged( position, RecyclerViewListData.getInstance().getList().size() );
    }

    @Override
    public void onRefresh() {

        TrackerManager.sendEventReport( this, "Operate", "onRefresh", false );


        RecyclerViewListData.getInstance().initList();
        recyclerViewAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing( false );
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName( "RecyclerViewList Page" ) // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl( Uri.parse( "http://[ENTER-YOUR-URL-HERE]" ) )
                .build();
        return new Action.Builder( Action.TYPE_VIEW )
                .setObject( object )
                .setActionStatus( Action.STATUS_TYPE_COMPLETED )
                .build();
    }

}

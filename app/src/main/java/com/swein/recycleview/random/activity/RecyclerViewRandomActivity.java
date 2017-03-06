package com.swein.recycleview.random.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;

import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.framework.tools.util.thread.ThreadUtils;
import com.swein.framework.tools.util.toast.ToastUtils;
import com.swein.recycleview.random.adapter.RecyclerViewAdapter;
import com.swein.recycleview.random.data.RecyclerViewRandomData;
import com.swein.recycleview.random.delegator.RecyclerViewRandomDelegator;
import com.swein.shandroidtoolutils.R;

public class RecyclerViewRandomActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, RecyclerViewRandomDelegator {

    private RecyclerView recyclerViewRandom;

    private RecyclerViewAdapter recyclerViewAdapter;
    private SwipeRefreshLayout  swipeRefreshLayoutRandom;

    private GridLayoutManager gridLayoutManager;
    private int               lastVisibleItem;

    final static private int MAX_SPAN_SIZE = 14;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_recycler_view_random );

        gridLayoutManager = new GridLayoutManager( this, MAX_SPAN_SIZE );

        RecyclerViewRandomData.getInstance().initList();

        gridLayoutManager.setSpanSizeLookup( new GridLayoutManager.SpanSizeLookup() {


            @Override
            public int getSpanSize( int position ) {

                ILog.iLogDebug( RecyclerViewRandomActivity.class.getSimpleName(),
                                position + " " + RecyclerViewRandomData.getInstance().getList().get( position ) + " " +
                                RecyclerViewRandomData.getInstance().getList().get( position ).length());

                int length = RecyclerViewRandomData.getInstance().getList().get( position ).length();
                if(length <= 2) {
                    return 3;
                }
                return length;

            }
        } );

        recyclerViewAdapter = new RecyclerViewAdapter( this, this );

        swipeRefreshLayoutRandom = (SwipeRefreshLayout)findViewById( R.id.swipeRefreshLayoutRandom );

        recyclerViewRandom = (RecyclerView)findViewById( R.id.recyclerViewRandom );

        swipeRefreshLayoutRandom.setOnRefreshListener( this );

        //first enter page to show progress bar
        swipeRefreshLayoutRandom.setProgressViewOffset( false, 0, (int)TypedValue
                .applyDimension( TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics() ) );

        recyclerViewRandom = (RecyclerView)findViewById( R.id.recyclerViewRandom );
        recyclerViewRandom.setLayoutManager( gridLayoutManager );
        recyclerViewRandom.setAdapter( recyclerViewAdapter );
        recyclerViewRandom.setItemAnimator( new DefaultItemAnimator() );

        recyclerViewRandom.addOnScrollListener( new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged( RecyclerView recyclerView, int newState ) {
                super.onScrollStateChanged( recyclerView, newState );

                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1
                        == RecyclerViewRandomData.getInstance().getList().size()) {

                    //load more data
                    ThreadUtils.createThreadWithUI( 0, new Runnable() {
                        @Override
                        public void run() {
                            RecyclerViewRandomData.getInstance().loadList();
                            recyclerViewAdapter.notifyDataSetChanged();
                        }
                    } );

                }
            }

            @Override
            public void onScrolled( RecyclerView recyclerView, int dx, int dy ) {
                super.onScrolled( recyclerView, dx, dy );

                lastVisibleItem = gridLayoutManager.findLastVisibleItemPosition();

            }
        } );
    }

    @Override
    public void onRefresh() {

        RecyclerViewRandomData.getInstance().initList();
        recyclerViewAdapter.notifyDataSetChanged();
        swipeRefreshLayoutRandom.setRefreshing( false );

    }

    @Override
    public void onItemClicked( int position ) {
        ToastUtils.showCustomLongToastNormal( this, "position: " + position + " [" + RecyclerViewRandomData.getInstance().getList().get( position ) + "]\n"
        + "length: " + RecyclerViewRandomData.getInstance().getList().get( position ).length());
    }
}

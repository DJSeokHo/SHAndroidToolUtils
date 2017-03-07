package com.swein.recycleview.random.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;

import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.framework.tools.util.thread.ThreadUtils;
import com.swein.recycleview.random.adapter.RecyclerViewAdapter;
import com.swein.recycleview.random.data.ListItemData;
import com.swein.recycleview.random.data.RecyclerViewRandomData;
import com.swein.recycleview.random.delegator.RecyclerViewRandomDelegator;
import com.swein.shandroidtoolutils.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewRandomActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, RecyclerViewRandomDelegator {

    private RecyclerView recyclerViewRandom;

    private RecyclerViewAdapter recyclerViewAdapter;
    private SwipeRefreshLayout  swipeRefreshLayoutRandom;

    private GridLayoutManager gridLayoutManager;
    private int               lastVisibleItem;

    private Button checkButton;
    private Button searchButton;

    private final static int MAX_SPAN_SIZE = 14;

    public static String checkState;   //0: normal. 1: select. 2: all select

    public final static String NORMAL = "NORMAL";
    public final static String SELECT = "SELECT";
    public final static String ALL = "ALL";


    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_recycler_view_random );

        initData();
        initView();
        initPara();
        initControl();
    }

    private void initView() {
        checkButton = (Button)findViewById( R.id.checkButton );
        searchButton = (Button)findViewById( R.id.searchButton );
        swipeRefreshLayoutRandom = (SwipeRefreshLayout)findViewById( R.id.swipeRefreshLayoutRandom );
        recyclerViewRandom = (RecyclerView)findViewById( R.id.recyclerViewRandom );
    }

    private void initPara() {
        checkState = NORMAL;
        recyclerViewAdapter = new RecyclerViewAdapter( this, this );
        gridLayoutManager = new GridLayoutManager( this, MAX_SPAN_SIZE );
        recyclerViewRandom.setLayoutManager( gridLayoutManager );
        recyclerViewRandom.setAdapter( recyclerViewAdapter );
        recyclerViewRandom.setItemAnimator( new DefaultItemAnimator() );
    }

    private void initControl() {

        gridLayoutManager.setSpanSizeLookup( new GridLayoutManager.SpanSizeLookup() {

            @Override
            public int getSpanSize( int position ) {

                //                ILog.iLogDebug( RecyclerViewRandomActivity.class.getSimpleName(),
                //                                position + " " + RecyclerViewRandomData.getInstance().getList().get( position ) + " " +
                //                                RecyclerViewRandomData.getInstance().getList().get( position ).tagName.length());

                int length = RecyclerViewRandomData.getInstance().getList().get( position ).tagName.length();
                if(length <= 2) {
                    return 3;
                }
                else if(length > MAX_SPAN_SIZE) {
                    return MAX_SPAN_SIZE;
                }
                return length;

            }
        } );

        swipeRefreshLayoutRandom.setOnRefreshListener( this );

        //first enter page to show progress bar
        swipeRefreshLayoutRandom.setProgressViewOffset( false, 0, (int)TypedValue
                .applyDimension( TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics() ) );

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

        checkButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                setCheckButtonAfterClicked();
            }
        } );

        searchButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                final List<ListItemData> listItemDataList = new ArrayList<ListItemData>();

                ThreadUtils.createThreadWithoutUI(new Runnable() {
                    @Override
                    public void run() {
                        for(ListItemData listItemData : RecyclerViewRandomData.getInstance().getList()) {
                            if(listItemData.tagCheckState) {
                                listItemDataList.add(listItemData);
                            }
                        }

                        for(ListItemData listItemData : listItemDataList) {
                            ILog.iLogDebug( RecyclerViewRandomActivity.class.getSimpleName(), listItemData.tagName + " " + listItemData.tagCheckState );
                        }
                    }
                } );

            }
        } );
    }

    private void initData() {
        RecyclerViewRandomData.getInstance().initList();
    }

    public void setCheckButtonAfterClicked() {

        switch ( checkState ) {
            case NORMAL:
                searchButton.setVisibility( View.VISIBLE );
                checkButton.setText( "select" );
                checkState = SELECT;
                recyclerViewAdapter.notifyDataSetChanged();
                break;

            case SELECT:
                searchButton.setVisibility( View.VISIBLE );
                checkButton.setText( "all" );
                checkState = ALL;
                setAllItemSelected();
                break;

            case ALL:
                searchButton.setVisibility( View.GONE );
                checkButton.setText( "normal" );
                checkState = NORMAL;
                setAllItemUnSelected();
                break;
        }

    }

    @Override
    public void onRefresh() {

        RecyclerViewRandomData.getInstance().initList();
        recyclerViewAdapter.notifyDataSetChanged();
        swipeRefreshLayoutRandom.setRefreshing( false );

        checkButton.setText( "normal" );
        checkState = NORMAL;
        searchButton.setVisibility( View.GONE );
        setAllItemUnSelected();

    }

    @Override
    public void setItemCheckState( Object object ) {

        if(((ListItemData)object).tagCheckState) {
            ( (ListItemData)object ).tagCheckState = false;
        }
        else {
            ( (ListItemData)object ).tagCheckState = true;
        }
    }

    @Override
    public void setAllItemSelected() {
        ThreadUtils.createThreadWithUI(0, new Runnable() {
            @Override
            public void run() {
                for(ListItemData listItemData : RecyclerViewRandomData.getInstance().getList()) {
                    listItemData.tagCheckState = true;
                }
                recyclerViewAdapter.notifyDataSetChanged();
            }
        } );
    }

    @Override
    public void setAllItemUnSelected() {
        ThreadUtils.createThreadWithUI( 0, new Runnable() {
            @Override
            public void run() {
                for(ListItemData listItemData : RecyclerViewRandomData.getInstance().getList()) {
                    listItemData.tagCheckState = false;
                }
                recyclerViewAdapter.notifyDataSetChanged();
            }
        } );
    }

    @Override
    public void singleTagSearch( Object object ) {

        ILog.iLogDebug( RecyclerViewRandomActivity.class.getSimpleName(), ((ListItemData)object).tagName );
    }


}

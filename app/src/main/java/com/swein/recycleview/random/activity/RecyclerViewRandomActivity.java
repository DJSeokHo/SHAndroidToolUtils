package com.swein.recycleview.random.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.framework.tools.util.thread.ThreadUtils;
import com.swein.recycleview.random.adapter.RecyclerViewAdapter;
import com.swein.recycleview.random.data.ListItemData;
import com.swein.recycleview.random.data.RecyclerViewRandomData;
import com.swein.recycleview.random.delegator.RecyclerViewRandomDelegator;
import com.swein.shandroidtoolutils.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewRandomActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, RecyclerViewRandomDelegator, ListenerInterface {

    private RecyclerView recyclerViewRandom;

    private RecyclerViewAdapter recyclerViewAdapter;
    private SwipeRefreshLayout  swipeRefreshLayoutRandom;

    private GridLayoutManager gridLayoutManager;
    private int               lastVisibleItem;

    private ImageButton checkImageButton;
    private ImageButton searchImageButton;
    private ImageButton clearImageButton;
    private ImageButton searchTagImageButton;

    private EditText tagEditText;

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
        checkImageButton = (ImageButton)findViewById( R.id.checkImageButton );
        searchImageButton = (ImageButton)findViewById( R.id.searchImageButton );
        clearImageButton = (ImageButton)findViewById( R.id.clearImageButton );
        searchTagImageButton = (ImageButton)findViewById( R.id.searchTagImageButton );
        swipeRefreshLayoutRandom = (SwipeRefreshLayout)findViewById( R.id.swipeRefreshLayoutRandom );
        recyclerViewRandom = (RecyclerView)findViewById( R.id.recyclerViewRandom );
        tagEditText = (EditText)findViewById( R.id.tagEditText );
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

        checkImageButton.setOnClickListener( onClickListener() );

        searchImageButton.setOnClickListener( onClickListener() );

        clearImageButton.setOnClickListener( onClickListener() );

        searchTagImageButton.setOnClickListener( onClickListener() );

        tagEditText.addTextChangedListener( textWatcher() );
    }

    private void initData() {
        RecyclerViewRandomData.getInstance().initList();
    }

    public void setCheckButtonAfterClicked() {

        switch ( checkState ) {
            case NORMAL:
                searchImageButton.setVisibility( View.VISIBLE );
                checkImageButton.setImageResource( R.drawable.recyclerview_random_select );
                checkState = SELECT;
                recyclerViewAdapter.notifyDataSetChanged();
                break;

            case SELECT:
                searchImageButton.setVisibility( View.VISIBLE );
                checkImageButton.setImageResource( R.drawable.recyclerview_random_select_all );
                checkState = ALL;
                setAllItemSelected();
                break;

            case ALL:
                searchImageButton.setVisibility( View.GONE );
                checkImageButton.setImageResource( R.drawable.recyclerview_random_select_normal );
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

        checkImageButton.setImageResource( R.drawable.recyclerview_random_select_normal );
        checkState = NORMAL;
        searchImageButton.setVisibility( View.GONE );
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

        checkSelectedItem();
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

    @Override
    public void checkSelectedItem() {
        ThreadUtils.createThreadWithUI( 0, new Runnable() {
            @Override
            public void run() {

                for(ListItemData listItemData: RecyclerViewRandomData.getInstance().getList()) {

                    // if has any one unselected item, so it's not all selected state. change state and break
                    if (!listItemData.tagCheckState) {
                        ILog.iLogDebug( RecyclerViewRandomActivity.class.getSimpleName(), "false" );
                        searchImageButton.setVisibility( View.VISIBLE );
                        checkImageButton.setImageResource( R.drawable.recyclerview_random_select );
                        checkState = SELECT;
                        recyclerViewAdapter.notifyDataSetChanged();
                        break;
                    }
                    else {// if has no one unselected item until last item, now it's all selected state, do not break
                        ILog.iLogDebug( RecyclerViewRandomActivity.class.getSimpleName(), "true" );
                        searchImageButton.setVisibility( View.VISIBLE );
                        checkImageButton.setImageResource( R.drawable.recyclerview_random_select_all );
                        checkState = ALL;
                    }
                }

            }
        } );
    }


    @Override
    public View.OnClickListener onClickListener() {

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick( View view ) {

                switch ( view.getId() ) {

                    case R.id.checkImageButton:

                        setCheckButtonAfterClicked();

                        break;

                    case R.id.searchImageButton:
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

                        break;

                    case R.id.clearImageButton:

                        tagEditText.setText( "" );

                        break;

                    case R.id.searchTagImageButton:

                        break;

                }
            }
        };

        return onClickListener;
    }

    @Override
    public TextWatcher textWatcher() {

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged( CharSequence charSequence, int i, int i1, int i2 ) {

            }

            @Override
            public void onTextChanged( CharSequence charSequence, int i, int i1, int i2 ) {

            }

            @Override
            public void afterTextChanged( Editable editable ) {
                if(tagEditText.getText().toString().trim().length() != 0) {
                    clearImageButton.setVisibility( View.VISIBLE );
                    searchTagImageButton.setVisibility( View.VISIBLE );
                }
                else {
                    clearImageButton.setVisibility( View.GONE );
                    searchTagImageButton.setVisibility( View.INVISIBLE );
                }
            }
        };

        return textWatcher;
    }

}

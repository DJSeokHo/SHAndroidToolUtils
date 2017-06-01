package com.swein.recycleview.random.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.swein.framework.module.googleanalytics.handler.CrashExceptionHandler;
import com.swein.framework.module.googleanalytics.manager.TrackerManager;
import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.framework.tools.util.input.keyboard.KeyBoardUtils;
import com.swein.framework.tools.util.thread.ThreadUtils;
import com.swein.framework.tools.util.views.edittext.EditTextViewUtils;
import com.swein.recycleview.random.adapter.RecyclerViewAdapter;
import com.swein.recycleview.random.customview.AutofitRecyclerView;
import com.swein.recycleview.random.customview.RecyclerViewItemSetting;
import com.swein.recycleview.random.data.ListItemData;
import com.swein.recycleview.random.data.RecyclerViewRandomData;
import com.swein.recycleview.random.delegate.RecyclerViewRandomDelegate;
import com.swein.shandroidtoolutils.R;

import java.util.ArrayList;
import java.util.List;

import static com.swein.recycleview.random.customview.AutofitRecyclerView.returnLastVisibleItemPosition;

public class RecyclerViewRandomActivity extends AppCompatActivity implements RecyclerViewRandomDelegate, ListenerInterface {

    private AutofitRecyclerView recyclerViewRandom;

    private RecyclerViewAdapter recyclerViewAdapter;
    private SwipeRefreshLayout swipeRefreshLayoutRandom;

    private RecyclerViewItemSetting recyclerViewItemSetting;

    private int lastVisibleItem;

    private ImageButton checkImageButton;
    private ImageButton searchImageButton;
    private ImageButton clearImageButton;
    private ImageButton searchTagImageButton;
    private ImageButton btnBackToTop;

    private EditText tagEditText;

    private LinearLayout headerLinearLayout;

    private boolean isheaderLinearLayoutShow;

    public static String checkState;   //0: normal. 1: select. 2: all select

    public final static String NORMAL = "NORMAL";
    public final static String SELECT = "SELECT";
    public final static String ALL = "ALL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_random);

        //auto send crash report when this activity crashed
        CrashExceptionHandler.getInstance().init(this);

        TrackerManager.sendScreenViewReport(this);

        initData();
        initView();
        initPara();
        initControl();
    }

    private void initView() {
//        TrackerManager.sendEventReport(this, "Auto", "initView", false);
        checkImageButton = (ImageButton) findViewById(R.id.checkImageButton);
        searchImageButton = (ImageButton) findViewById(R.id.searchImageButton);
        clearImageButton = (ImageButton) findViewById(R.id.clearImageButton);
        searchTagImageButton = (ImageButton) findViewById(R.id.searchTagImageButton);
        btnBackToTop = (ImageButton) findViewById(R.id.btnBackToTop);
        swipeRefreshLayoutRandom = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayoutRandom);
        recyclerViewRandom = (AutofitRecyclerView) findViewById(R.id.recyclerViewRandom);
        tagEditText = (EditText) findViewById(R.id.tagEditText);
        headerLinearLayout = (LinearLayout) findViewById(R.id.headerLinearLayout);
    }

    private void initPara() {
//        TrackerManager.sendEventReport(this, "Auto", "initPara", false);
        isheaderLinearLayoutShow = false;
        checkState = NORMAL;
        recyclerViewAdapter = new RecyclerViewAdapter(this, this);
        recyclerViewRandom.setAdapter(recyclerViewAdapter);
        recyclerViewRandom.setItemAnimator(new DefaultItemAnimator());
        recyclerViewItemSetting = new RecyclerViewItemSetting(this);
    }

    private void initControl() {
//        TrackerManager.sendEventReport(this, "Auto", "initControl", false);
        recyclerViewRandom.setColumnWidth(200);

        swipeRefreshLayoutRandom.setOnRefreshListener(onRefreshListener());

        //first enter page to show progress bar
        swipeRefreshLayoutRandom.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));

        recyclerViewRandom.addOnScrollListener(onScrollListener());

        recyclerViewRandom.addItemDecoration(recyclerViewItemSetting);

        checkImageButton.setOnClickListener(onClickListener());

        searchImageButton.setOnClickListener(onClickListener());

        clearImageButton.setOnClickListener(onClickListener());

        searchTagImageButton.setOnClickListener(onClickListener());

        btnBackToTop.setOnClickListener(onClickListener());

        EditTextViewUtils.editTextViewAddTextWatcher(tagEditText, textWatcher());
        EditTextViewUtils.editTextViewSetFocusChangeListener(tagEditText, onFocusChangeListener());
    }

    private void initData() {
//        TrackerManager.sendEventReport(this, "Auto", "initListFromDB", false);
        RecyclerViewRandomData.getInstance().initListFromDB();
    }

    public void setCheckButtonAfterClicked() {

        switch (checkState) {
            case NORMAL:
                searchImageButton.setVisibility(View.VISIBLE);
                checkImageButton.setImageResource(R.drawable.recyclerview_random_item_unchecked);
                checkState = SELECT;
                recyclerViewAdapter.notifyDataSetChanged();
                break;

            case SELECT:
                searchImageButton.setVisibility(View.VISIBLE);
                checkImageButton.setImageResource(R.drawable.recyclerview_random_item_checked);
                checkState = ALL;
                setAllItemSelected();
                break;

            case ALL:
                searchImageButton.setVisibility(View.GONE);
                checkImageButton.setImageResource(R.drawable.recyclerview_random_select_normal);
                checkState = NORMAL;
                setAllItemUnSelected();
                break;
        }

    }

    @Override
    public void setItemCheckState(Object object) {

        if (((ListItemData) object).tagCheckState) {
            ((ListItemData) object).tagCheckState = false;
        } else {
            ((ListItemData) object).tagCheckState = true;
        }

        checkSelectedItem();
    }

    @Override
    public void setAllItemSelected() {
        ThreadUtils.createThreadWithUI(0, new Runnable() {
            @Override
            public void run() {
                for (ListItemData listItemData : RecyclerViewRandomData.getInstance().getList()) {
                    listItemData.tagCheckState = true;
                }
                recyclerViewAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void setAllItemUnSelected() {
        ThreadUtils.createThreadWithUI(0, new Runnable() {
            @Override
            public void run() {
                for (ListItemData listItemData : RecyclerViewRandomData.getInstance().getList()) {
                    listItemData.tagCheckState = false;
                }
                recyclerViewAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void singleTagSearch(Object object) {

        ILog.iLogDebug(RecyclerViewRandomActivity.class.getSimpleName(), ((ListItemData) object).tagName + ((ListItemData) object).itemPosition);
    }

    @Override
    public void checkSelectedItem() {
        ThreadUtils.createThreadWithUI(0, new Runnable() {
            @Override
            public void run() {

                for (ListItemData listItemData : RecyclerViewRandomData.getInstance().getList()) {

                    // if has any one unselected item, so it's not all selected state. change state and break
                    if (!listItemData.tagCheckState) {
                        ILog.iLogDebug(RecyclerViewRandomActivity.class.getSimpleName(), "false");
                        searchImageButton.setVisibility(View.VISIBLE);
                        checkImageButton.setImageResource(R.drawable.recyclerview_random_item_unchecked);
                        checkState = SELECT;
                        recyclerViewAdapter.notifyDataSetChanged();
                        break;
                    } else {// if has no one unselected item until last item, now it's all selected state, do not break
                        ILog.iLogDebug(RecyclerViewRandomActivity.class.getSimpleName(), "true");
                        searchImageButton.setVisibility(View.VISIBLE);
                        checkImageButton.setImageResource(R.drawable.recyclerview_random_item_checked);
                        checkState = ALL;
                    }
                }
            }
        });
    }

    @Override
    public View.OnClickListener onClickListener() {

        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (view.getId()) {

                    case R.id.checkImageButton:

//                        TrackerManager.sendEventReport(RecyclerViewRandomActivity.this, "Operate", "click", "checkImageButton", R.id.checkImageButton, false);

                        setCheckButtonAfterClicked();

                        break;

                    case R.id.searchImageButton:

//                        TrackerManager.sendEventReport(RecyclerViewRandomActivity.this, "Operate", "click", "searchImageButton", R.id.searchImageButton, false);

                        final List<ListItemData> listItemDataList = new ArrayList<ListItemData>();

                        ThreadUtils.createThreadWithoutUI(new Runnable() {
                            @Override
                            public void run() {
                                for (ListItemData listItemData : RecyclerViewRandomData.getInstance().getList()) {
                                    if (listItemData.tagCheckState) {
                                        listItemDataList.add(listItemData);
                                    }
                                }

                                for (ListItemData listItemData : listItemDataList) {
                                    ILog.iLogDebug(RecyclerViewRandomActivity.class.getSimpleName(), listItemData.tagName + " " + listItemData.tagCheckState);
                                }
                            }
                        });

                        break;

                    case R.id.clearImageButton:

//                        TrackerManager.sendEventReport(RecyclerViewRandomActivity.this, "Operate", "click", "clearImageButton", R.id.clearImageButton, false);

                        tagEditText.setText("");

                        break;

                    case R.id.searchTagImageButton:

//                        String[] test = new String[] {"1", "2"};
//                        ILog.iLogDebug( RecyclerViewRandomActivity.this, test[3] );
                        break;

                    case R.id.btnBackToTop:

                        recyclerViewRandom.scrollToPosition(0);
                        hideHeader();
                        break;
                }
            }
        };

    }

    @Override
    public TextWatcher textWatcher() {

        return new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (tagEditText.getText().toString().trim().length() != 0) {
                    clearImageButton.setVisibility(View.VISIBLE);
                    searchTagImageButton.setVisibility(View.VISIBLE);
                } else {
                    clearImageButton.setVisibility(View.GONE);
                    searchTagImageButton.setVisibility(View.INVISIBLE);
                }
            }
        };
    }

    @Override
    public RecyclerView.OnScrollListener onScrollListener() {

        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

//                TrackerManager.sendEventReport(RecyclerViewRandomActivity.this, "Operate", "onScrollStateChanged", false);

                tagEditText.setText("");

                //check edit text view focus
                if (EditTextViewUtils.isEditTextViewHasFocus(tagEditText)) {
                    KeyBoardUtils.clearFocusOnView(tagEditText);
                    KeyBoardUtils.dismissKeyboard(RecyclerViewRandomActivity.this);
                }

                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1
                        == RecyclerViewRandomData.getInstance().getList().size()) {

                    //load more data
                    ThreadUtils.createThreadWithUI(0, new Runnable() {
                        @Override
                        public void run() {
                            RecyclerViewRandomData.getInstance().loadMoreListFromDB();
                            recyclerViewAdapter.notifyDataSetChanged();
                            if (!checkState.equals(NORMAL)) {
                                checkSelectedItem();
                            }
                        }
                    });
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                ILog.iLogDebug(RecyclerViewRandomActivity.class.getSimpleName(), dy);

                if(dy < -100) {
                    if(!isheaderLinearLayoutShow) {
                        showHeader();
                    }
                }
                else if(dy > 100) {
                    if(isheaderLinearLayoutShow) {
                        hideHeader();
                    }
                }

                lastVisibleItem = returnLastVisibleItemPosition();
            }
        };
    }

    private void showHeader() {
        headerLinearLayout.setVisibility(View.VISIBLE);
        isheaderLinearLayoutShow = true;
    }

    private void hideHeader() {
        headerLinearLayout.setVisibility(View.GONE);
        isheaderLinearLayoutShow = false;
    }

    @Override
    public SwipeRefreshLayout.OnRefreshListener onRefreshListener() {

        return new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

//                TrackerManager.sendEventReport(RecyclerViewRandomActivity.this, "Operate", "onRefresh", false);

                RecyclerViewRandomData.getInstance().initListFromDB();
                recyclerViewAdapter.notifyDataSetChanged();
                swipeRefreshLayoutRandom.setRefreshing(false);

                checkImageButton.setImageResource(R.drawable.recyclerview_random_select_normal);
                checkState = NORMAL;
                searchImageButton.setVisibility(View.GONE);
                setAllItemUnSelected();

                headerLinearLayout.setVisibility(View.GONE);
            }
        };
    }

    @Override
    public View.OnFocusChangeListener onFocusChangeListener() {
        return new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

            }
        };
    }
}

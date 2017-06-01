package com.swein.recycleview.random.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextWatcher;
import android.view.View;

/**
 *
 * Created by seokho on 08/03/2017.
 */

public interface ListenerInterface {

    View.OnClickListener onClickListener();
    TextWatcher textWatcher();
    RecyclerView.OnScrollListener onScrollListener();
    SwipeRefreshLayout.OnRefreshListener onRefreshListener();
    View.OnFocusChangeListener onFocusChangeListener();

}

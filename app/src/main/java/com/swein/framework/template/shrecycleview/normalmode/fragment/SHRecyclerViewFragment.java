package com.swein.framework.template.shrecycleview.normalmode.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.swein.framework.template.shrecycleview.normalmode.fragment.adapter.SHRecyclerViewAdapter;
import com.swein.framework.template.shrecycleview.normalmode.fragment.adapter.viewholder.model.SHRecyclerViewItemDataModel;
import com.swein.framework.template.shrecycleview.normalmode.shrecycleviewconstants.Constants;
import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.framework.tools.util.views.ViewUtil;
import com.swein.shandroidtoolutils.R;

import java.util.ArrayList;
import java.util.List;


public class SHRecyclerViewFragment extends Fragment {

    private final static String TAG = "SHRecyclerViewFragment";

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private SHRecyclerViewAdapter adapter;


    private View rootView;

    public int spanCount = 3;
    public Constants.RecyclerViewLayoutMode recyclerViewLayoutMode = Constants.RecyclerViewLayoutMode.LIST;

    public SHRecyclerViewFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = ViewUtil.viewLayoutInflater(R.layout.fragment_shrecycler_view, container, false);

        findView();

        return rootView;
    }

    private void findView(){

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.shSwipeRefreshLayout);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.shRecyclerView);

        switch (recyclerViewLayoutMode) {
            case GRID:
                layoutManager = new GridLayoutManager(rootView.getContext(), spanCount);
                break;

            case LIST:
                layoutManager = new LinearLayoutManager(rootView.getContext());
                break;
        }

        adapter = new SHRecyclerViewAdapter();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                // reload here
                adapter.reloadList(createTempData());

                swipeRefreshLayout.setRefreshing(false);
            }
        });

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                int totalItemCount = recyclerView.getAdapter().getItemCount();
                int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                int visibleItemCount = recyclerView.getChildCount();

                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItemPosition == totalItemCount - 1
                        && visibleItemCount > 0) {

                    // load more here
                    adapter.loadMoreList(createTempData());
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        adapter.loadMoreList(createTempData());
    }


    private List<SHRecyclerViewItemDataModel> createTempData() {

        List<SHRecyclerViewItemDataModel> list = new ArrayList<>();

        SHRecyclerViewItemDataModel shRecyclerViewItemDataModel;

        for(int i = 0; i < 100; i++) {
            shRecyclerViewItemDataModel = new SHRecyclerViewItemDataModel(i + " item");
            list.add(shRecyclerViewItemDataModel);
        }

        return list;
    }

    @Override
    public void onDestroyView() {

        ILog.iLogDebug(TAG, "onDestroyView");
        adapter = null;
        super.onDestroyView();
    }


    @Override
    public void onDestroy() {

        ILog.iLogDebug(TAG, "onDestroy");

        super.onDestroy();
    }

    @Override
    protected void finalize() throws Throwable {

        ILog.iLogDebug(TAG, "finalize");

        super.finalize();
    }
}

package com.swein.framework.template.shrecycleview.normalmode.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.swein.framework.template.shrecycleview.normalmode.fragment.adapter.SHRecyclerViewAdapter;
import com.swein.framework.template.shrecycleview.normalmode.fragment.adapter.viewholder.bean.SHRecyclerViewItemDataBean;
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

    private void findView() {

        swipeRefreshLayout = rootView.findViewById(R.id.shSwipeRefreshLayout);
        recyclerView = rootView.findViewById(R.id.shRecyclerView);

        switch (recyclerViewLayoutMode) {
            case GRID:
                layoutManager = new GridLayoutManager(rootView.getContext(), spanCount);
                break;

            case LIST:
                layoutManager = new LinearLayoutManager(rootView.getContext());
                break;
        }

        adapter = new SHRecyclerViewAdapter();

        swipeRefreshLayout.setOnRefreshListener(() -> {

            // reload here
            adapter.reloadList(createTempData(0, 15));

            swipeRefreshLayout.setRefreshing(false);
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
                    adapter.loadMoreList(createTempData(adapter.getItemCount(), 15));
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        adapter.loadMoreList(createTempData(0, 15));
    }


    private List<SHRecyclerViewItemDataBean> createTempData(int offset, int limit) {

        List<SHRecyclerViewItemDataBean> list = new ArrayList<>();

        SHRecyclerViewItemDataBean shRecyclerViewItemDataBean;

        String url;
        for (int i = offset; i < offset + limit; i++) {
            if (i % 5 == 0) {
                url = "https://onestep4ward.com/wp-content/uploads/2019/06/Travel.jpg";
            }
            else if (i % 7 == 0) {
                url = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQQPy7raArvIo1T8Haw7Sq-KOg2-CfSx_ObFaX7ORD-OOjnFCHgrw&s";
            }
            else if (i % 8 == 0) {
                url = "https://independenttravelcats.com/wp-content/uploads/2018/03/Destinations.jpg";
            }
            else if (i % 9 == 0) {
                url = "https://cms.hostelworld.com/hwblog/wp-content/uploads/sites/2/2017/08/girlgoneabroad.jpg";
            }
            else {
                url = "https://specials-images.forbesimg.com/imageserve/5de28d24ea103f000653be7c/960x0.jpg?cropX1=0&cropX2=3936&cropY1=320&cropY2=2166";
            }

            shRecyclerViewItemDataBean = new SHRecyclerViewItemDataBean(i + " item", url);
            list.add(shRecyclerViewItemDataBean);
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

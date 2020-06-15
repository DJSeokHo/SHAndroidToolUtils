package com.swein.framework.module.imageselector.selector;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.swein.framework.module.imageselector.ImageFolderItemBean;
import com.swein.framework.module.imageselector.imageselectorwrapper.ImageSelectorWrapper;
import com.swein.framework.module.imageselector.selector.adapter.ImageSelectorAdapter;
import com.swein.framework.module.imageselector.selector.adapter.item.ImageSelectorItemBean;
import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.framework.tools.util.eventsplitshot.eventcenter.EventCenter;
import com.swein.framework.tools.util.eventsplitshot.subject.ESSArrows;
import com.swein.framework.tools.util.thread.ThreadUtil;
import com.swein.framework.tools.util.views.ViewUtil;
import com.swein.shandroidtoolutils.R;

import java.util.ArrayList;
import java.util.List;

public class ImageSelectorViewHolder {

    private final static String TAG = "ImageSelectorViewHolder";

    public interface ImageSelectorViewHolderDelegate {
        void onInitFinish();
    }

    private View view;

    private ImageSelectorAdapter imageSelectorAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private List<ImageFolderItemBean> imageFolderItemBeans = new ArrayList<>();
    private List<ImageSelectorItemBean> imageSelectorItemBeanList = new ArrayList<>();
    public List<ImageSelectorItemBean> selectedList = new ArrayList<>();

    public ImageSelectorViewHolderDelegate imageSelectorViewHolderDelegate;
    public int maxSelect;

    public ImageSelectorViewHolder(Context context, int maxSelect) {
        this.maxSelect = maxSelect;
        view = ViewUtil.inflateView(context, R.layout.view_holder_image_selector, null);

        initData();
        findView();
        setListener();
        initList();
    }

    private void findView() {
        recyclerView = view.findViewById(R.id.recyclerView);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
    }

    private void setListener() {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(false);
            reload();
        });
    }

    private void initData() {
        ImageSelectorWrapper.getInstance().scanImageFile(view.getContext(), new ImageSelectorWrapper.ImageSelectorWrapperDelegate() {
            @Override
            public void onSuccess(List<ImageFolderItemBean> imageFolderItemBeans, List<ImageSelectorItemBean> imageSelectorItemBeanList) {
                ImageSelectorViewHolder.this.imageFolderItemBeans.clear();
                ImageSelectorViewHolder.this.imageFolderItemBeans.addAll(imageFolderItemBeans);
                ImageSelectorViewHolder.this.imageSelectorItemBeanList.clear();
                ImageSelectorViewHolder.this.imageSelectorItemBeanList.addAll(imageSelectorItemBeanList);


                initList();

                imageSelectorViewHolderDelegate.onInitFinish();
            }

            @Override
            public void onError() {
                ILog.iLogDebug(TAG, "error");
            }
        });
    }

    private void initList() {
        layoutManager = new GridLayoutManager(view.getContext(), 3);

        imageSelectorAdapter = new ImageSelectorAdapter();
        imageSelectorAdapter.imageSelectorAdapterDelegate = new ImageSelectorAdapter.ImageSelectorAdapterDelegate() {
            @Override
            public void onLoadMore() {
                loadMore();
            }

            @Override
            public void onSelected() {
                selectedList.clear();
                for(int i = 0; i < imageSelectorItemBeanList.size(); i++) {
                    if(imageSelectorItemBeanList.get(i).isSelected) {
                        selectedList.add(imageSelectorItemBeanList.get(i));
                    }
                }

                for(int i = 0; i < selectedList.size(); i++) {
                    ILog.iLogDebug(TAG, selectedList.get(i).filePath + " " + selectedList.get(i).isSelected);
                }

                if(selectedList.size() >= maxSelect) {
                    EventCenter.getInstance().sendEvent(ESSArrows.DISABLE_LIST_ITEM_CLICK, this, null);
                }
                else {
                    EventCenter.getInstance().sendEvent(ESSArrows.ENABLE_LIST_ITEM_CLICK, this, null);
                }

            }
        };

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(imageSelectorAdapter);

    }

    public void reload() {

        ThreadUtil.startThread(() -> {

            ThreadUtil.startUIThread(0, () -> {
                imageSelectorAdapter.reload(getImageSelectorItemBeanList(0, 30));
            });
        });

    }


    private void loadMore() {

        ThreadUtil.startThread(() -> {

            ThreadUtil.startUIThread(0, () -> {
                imageSelectorAdapter.loadMore(getImageSelectorItemBeanList(imageSelectorAdapter.getItemCount(), 30));
            });
        });
    }

    public View getView() {
        return view;
    }

    private List<ImageSelectorItemBean> getImageSelectorItemBeanList(int offset, int limit) {

        List<ImageSelectorItemBean> imageSelectorItemBeanList = new ArrayList<>();
        for(int i = offset; i < offset + limit; i++) {

            if(i > ImageSelectorViewHolder.this.imageSelectorItemBeanList.size() - 1) {
                return imageSelectorItemBeanList;
            }

            imageSelectorItemBeanList.add(ImageSelectorViewHolder.this.imageSelectorItemBeanList.get(i));
        }

        return imageSelectorItemBeanList;
    }
}

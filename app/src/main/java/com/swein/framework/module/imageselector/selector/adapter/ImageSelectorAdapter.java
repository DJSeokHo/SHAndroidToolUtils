package com.swein.framework.module.imageselector.selector.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.swein.framework.module.imageselector.selector.adapter.item.ImageSelectorItemBean;
import com.swein.framework.module.imageselector.selector.adapter.item.ImageSelectorItemViewHolder;
import com.swein.shandroidtoolutils.R;

import java.util.ArrayList;
import java.util.List;

public class ImageSelectorAdapter extends RecyclerView.Adapter {

    public interface ImageSelectorAdapterDelegate {
        void onLoadMore();
        void onSelected();
    }

    private List<ImageSelectorItemBean> imageSelectorItemBeanList = new ArrayList<>();

    public ImageSelectorAdapterDelegate imageSelectorAdapterDelegate;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_image_selector_item, parent, false);
        return new ImageSelectorItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ImageSelectorItemViewHolder imageSelectorItemViewHolder = (ImageSelectorItemViewHolder) holder;
        imageSelectorItemViewHolder.imageSelectorItemBean = imageSelectorItemBeanList.get(position);
        imageSelectorItemViewHolder.imageSelectorItemViewHolderDelegate = () -> imageSelectorAdapterDelegate.onSelected();

        imageSelectorItemViewHolder.updateView();

        if(position == imageSelectorItemBeanList.size() - 1) {
            imageSelectorAdapterDelegate.onLoadMore();
        }
    }

    public void reload(List<ImageSelectorItemBean> imageSelectorItemBeanList) {
        this.imageSelectorItemBeanList.clear();
        this.imageSelectorItemBeanList.addAll(imageSelectorItemBeanList);
        notifyDataSetChanged();
    }

    public void loadMore(List<ImageSelectorItemBean> imageSelectorItemBeanList) {
        this.imageSelectorItemBeanList.addAll(imageSelectorItemBeanList);
        notifyItemRangeChanged(this.imageSelectorItemBeanList.size() - imageSelectorItemBeanList.size() + 1, imageSelectorItemBeanList.size());
    }

    @Override
    public int getItemCount() {
        return imageSelectorItemBeanList.size();
    }
}

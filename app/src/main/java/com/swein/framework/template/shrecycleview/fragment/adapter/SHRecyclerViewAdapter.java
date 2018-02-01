package com.swein.framework.template.shrecycleview.fragment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.swein.framework.template.shrecycleview.fragment.adapter.viewholder.SHRecyclerViewHolder;
import com.swein.framework.template.shrecycleview.fragment.adapter.viewholder.delegate.SHRecyclerViewHolderDelegate;
import com.swein.framework.template.shrecycleview.fragment.adapter.viewholder.model.SHRecyclerViewItemDataModel;
import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.framework.tools.util.toast.ToastUtil;
import com.swein.shandroidtoolutils.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by seokho on 02/01/2018.
 */

public class SHRecyclerViewAdapter extends RecyclerView.Adapter implements SHRecyclerViewHolderDelegate {

    private final static String TAG = "SHRecyclerViewAdapter";

    private List<SHRecyclerViewItemDataModel> shRecyclerViewItemDataModelList = new ArrayList<>();

    private Context context;

    private static final int TYPE_ITEM_LIST = 0;
    private static final int TYPE_ITEM_GRID = 1;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sh_recycler_view_holder_item, parent, false);
        return new SHRecyclerViewHolder(view, this);
    }

    public void loadMoreList(List<SHRecyclerViewItemDataModel> shRecyclerViewItemDataModelList) {
        this.shRecyclerViewItemDataModelList.addAll(shRecyclerViewItemDataModelList);

        notifyDataSetChanged();
    }

    public void reloadList(List<SHRecyclerViewItemDataModel> shRecyclerViewItemDataModelList) {
        this.shRecyclerViewItemDataModelList.clear();
        this.shRecyclerViewItemDataModelList.addAll(shRecyclerViewItemDataModelList);

        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SHRecyclerViewHolder) {

            SHRecyclerViewHolder shRecyclerViewHolder = (SHRecyclerViewHolder) holder;

            shRecyclerViewHolder.updateView(shRecyclerViewItemDataModelList.get(position));

        }

    }

    @Override
    public int getItemViewType(int position) {
        // change item view holder ui here
        return TYPE_ITEM_LIST;
//        return TYPE_ITEM_GRID;
    }

    @Override
    public int getItemCount() {
        return shRecyclerViewItemDataModelList.size();
    }

    @Override
    public void onSHRecyclerViewHolderClicked(SHRecyclerViewItemDataModel shRecyclerViewItemDataModel) {

        ToastUtil.showShortToastNormal(context, shRecyclerViewItemDataModel.string);

    }

    @Override
    protected void finalize() throws Throwable {

        ILog.iLogDebug(TAG, "finalize");

        super.finalize();
    }
}

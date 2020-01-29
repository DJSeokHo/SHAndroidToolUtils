package com.swein.framework.template.shrecycleview.normalmode.fragment.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.swein.framework.template.shrecycleview.normalmode.fragment.adapter.viewholder.SHRecyclerViewHolder;
import com.swein.framework.template.shrecycleview.normalmode.fragment.adapter.viewholder.delegate.SHRecyclerViewHolderDelegate;
import com.swein.framework.template.shrecycleview.normalmode.fragment.adapter.viewholder.bean.SHRecyclerViewItemDataBean;
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

    private List<SHRecyclerViewItemDataBean> shRecyclerViewItemDataBeanList = new ArrayList<>();

    private Context context;

    private static final int TYPE_ITEM_LIST = 0;
    private static final int TYPE_ITEM_GRID = 1;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sh_recycler_view_holder_item, parent, false);
        return new SHRecyclerViewHolder(view, this);
    }

    public void loadMoreList(List<SHRecyclerViewItemDataBean> shRecyclerViewItemDataBeanList) {
        this.shRecyclerViewItemDataBeanList.addAll(shRecyclerViewItemDataBeanList);
        notifyItemRangeChanged(this.shRecyclerViewItemDataBeanList.size() - shRecyclerViewItemDataBeanList.size() + 1, shRecyclerViewItemDataBeanList.size());
    }

    public void reloadList(List<SHRecyclerViewItemDataBean> shRecyclerViewItemDataBeanList) {
        this.shRecyclerViewItemDataBeanList.clear();
        this.shRecyclerViewItemDataBeanList.addAll(shRecyclerViewItemDataBeanList);

        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SHRecyclerViewHolder) {

            SHRecyclerViewHolder shRecyclerViewHolder = (SHRecyclerViewHolder) holder;

            shRecyclerViewHolder.updateView(shRecyclerViewItemDataBeanList.get(position));

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
        return shRecyclerViewItemDataBeanList.size();
    }

    @Override
    public void onSHRecyclerViewHolderClicked(SHRecyclerViewItemDataBean shRecyclerViewItemDataBean) {

        ToastUtil.showShortToastNormal(context, shRecyclerViewItemDataBean.string);

    }

    @Override
    protected void finalize() throws Throwable {

        ILog.iLogDebug(TAG, "finalize");

        super.finalize();
    }
}

package com.swein.framework.template.shrecycleview.fragment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.swein.framework.template.shrecycleview.fragment.adapter.viewholder.SHRecyclerViewHolder;
import com.swein.framework.template.shrecycleview.fragment.adapter.viewholder.delegate.SHRecyclerViewHolderDelegate;
import com.swein.framework.template.shrecycleview.fragment.adapter.viewholder.model.SHRecyclerViewItemDataModel;
import com.swein.framework.tools.util.toast.ToastUtils;
import com.swein.shandroidtoolutils.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by seokho on 02/01/2018.
 */

public class SHRecyclerViewAdapter extends RecyclerView.Adapter implements SHRecyclerViewHolderDelegate {

    private List<SHRecyclerViewItemDataModel> shRecyclerViewItemDataModelList = new ArrayList<>();

    private Context context;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context = parent.getContext();
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.sh_recycler_view_holder_item, parent, false );

        SHRecyclerViewHolder shRecyclerViewHolder = new SHRecyclerViewHolder(view, this);

        return shRecyclerViewHolder;
    }

    public void loadMoreList(List<SHRecyclerViewItemDataModel> shRecyclerViewItemDataModelList) {
        this.shRecyclerViewItemDataModelList.addAll(shRecyclerViewItemDataModelList);

        notifyDataSetChanged();
    }

    public void reloadList(List<SHRecyclerViewItemDataModel> shRecyclerViewItemDataModelList){
        this.shRecyclerViewItemDataModelList.clear();
        this.shRecyclerViewItemDataModelList.addAll(shRecyclerViewItemDataModelList);

        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof SHRecyclerViewHolder) {

            SHRecyclerViewHolder shRecyclerViewHolder = (SHRecyclerViewHolder)holder;

            shRecyclerViewHolder.updateView(shRecyclerViewItemDataModelList.get(position));

        }

    }

    @Override
    public int getItemCount() {
        return shRecyclerViewItemDataModelList.size();
    }

    @Override
    public void onSHRecyclerViewHolderClicked(SHRecyclerViewItemDataModel shRecyclerViewItemDataModel) {

        ToastUtils.showShortToastNormal(context, shRecyclerViewItemDataModel.string);

    }
}

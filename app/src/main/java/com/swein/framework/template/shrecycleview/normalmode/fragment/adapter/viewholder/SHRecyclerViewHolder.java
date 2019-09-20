package com.swein.framework.template.shrecycleview.normalmode.fragment.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.swein.framework.template.shrecycleview.normalmode.fragment.adapter.viewholder.delegate.SHRecyclerViewHolderDelegate;
import com.swein.framework.template.shrecycleview.normalmode.fragment.adapter.viewholder.model.SHRecyclerViewItemDataModel;
import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.shandroidtoolutils.R;

/**
 * Created by seokho on 02/01/2018.
 */

public class SHRecyclerViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = "SHRecyclerViewHolder";

    private View itemView;
    private TextView textView;

    private FrameLayout shRecyclerViewItem;

    private SHRecyclerViewItemDataModel dataModel;

    private SHRecyclerViewHolderDelegate shRecyclerViewHolderDelegate;

    public SHRecyclerViewHolder(View itemView, SHRecyclerViewHolderDelegate shRecyclerViewHolderDelegate) {
        super(itemView);
        this.itemView = itemView;
        this.shRecyclerViewHolderDelegate = shRecyclerViewHolderDelegate;
        findView();
    }

    private void findView(){
        shRecyclerViewItem = (FrameLayout) this.itemView.findViewById(R.id.shRecyclerViewItem);
        textView = (TextView) this.itemView.findViewById(R.id.textView);

        shRecyclerViewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shRecyclerViewHolderDelegate.onSHRecyclerViewHolderClicked(dataModel);
            }
        });

    }

    public void updateView(SHRecyclerViewItemDataModel dataModel){
        this.dataModel = dataModel;
        textView.setText(dataModel.string);
    }

    @Override
    protected void finalize() throws Throwable {

        ILog.iLogDebug(TAG, "finalize");
        super.finalize();
    }
}

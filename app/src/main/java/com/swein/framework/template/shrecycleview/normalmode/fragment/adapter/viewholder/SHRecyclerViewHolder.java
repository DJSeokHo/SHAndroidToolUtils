package com.swein.framework.template.shrecycleview.normalmode.fragment.adapter.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.swein.framework.template.shrecycleview.normalmode.fragment.adapter.viewholder.bean.SHRecyclerViewItemDataBean;
import com.swein.framework.template.shrecycleview.normalmode.fragment.adapter.viewholder.delegate.SHRecyclerViewHolderDelegate;
import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.framework.tools.util.glide.SHGlide;
import com.swein.shandroidtoolutils.R;

import java.lang.ref.WeakReference;

/**
 * Created by seokho on 02/01/2018.
 */

public class SHRecyclerViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = "SHRecyclerViewHolder";

    private WeakReference<View> view;
    private TextView textView;
    private ImageView imageView;

    private SHRecyclerViewItemDataBean dataModel;

    private SHRecyclerViewHolderDelegate shRecyclerViewHolderDelegate;

    public SHRecyclerViewHolder(View itemView, SHRecyclerViewHolderDelegate shRecyclerViewHolderDelegate) {
        super(itemView);
        this.view = new WeakReference<>(itemView);
        this.shRecyclerViewHolderDelegate = shRecyclerViewHolderDelegate;
        findView();
    }

    private void findView() {
        textView = view.get().findViewById(R.id.textView);
        imageView = view.get().findViewById(R.id.imageView);

        view.get().setOnClickListener(v -> shRecyclerViewHolderDelegate.onSHRecyclerViewHolderClicked(dataModel));

    }

    public void updateView(SHRecyclerViewItemDataBean dataModel) {
        this.dataModel = dataModel;
        textView.setText(dataModel.string);
        imageView.post(() -> {
            ILog.iLogDebug(TAG, imageView.getWidth() + " " + imageView.getHeight());
            SHGlide.getInstance().setImageBitmap(view.get().getContext(), dataModel.imageUrl, imageView, null, imageView.getWidth(), imageView.getHeight(), 0, 0);
        });
    }

    @Override
    protected void finalize() throws Throwable {

        ILog.iLogDebug(TAG, "finalize");
        super.finalize();
    }
}

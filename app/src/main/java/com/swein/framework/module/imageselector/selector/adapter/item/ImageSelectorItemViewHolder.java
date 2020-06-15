package com.swein.framework.module.imageselector.selector.adapter.item;

import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.swein.framework.tools.util.bitmaps.BitmapUtil;
import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.framework.tools.util.eventsplitshot.eventcenter.EventCenter;
import com.swein.framework.tools.util.eventsplitshot.subject.ESSArrows;
import com.swein.framework.tools.util.glide.SHGlide;
import com.swein.framework.tools.util.thread.ThreadUtil;
import com.swein.shandroidtoolutils.R;

import java.lang.ref.WeakReference;

public class ImageSelectorItemViewHolder extends RecyclerView.ViewHolder {

    private final static String TAG = "ImageSelectorItemViewHolder";

    public interface ImageSelectorItemViewHolderDelegate {
        void onSelected();
    }

    public ImageSelectorItemBean imageSelectorItemBean;

    private WeakReference<View> view;

    private ImageView imageView;
    private ImageView imageViewCheck;

    private Bitmap bitmap;

    public ImageSelectorItemViewHolderDelegate imageSelectorItemViewHolderDelegate;

    private boolean click = true;

    public ImageSelectorItemViewHolder(@NonNull View itemView) {
        super(itemView);

        view = new WeakReference<>(itemView);

        findView();
        setListener();
        initView();
    }

    private void initESS() {
        EventCenter.getInstance().addEventObserver(ESSArrows.ENABLE_LIST_ITEM_CLICK, this, (arrow, poster, data) -> click = true);

        EventCenter.getInstance().addEventObserver(ESSArrows.DISABLE_LIST_ITEM_CLICK, this, (arrow, poster, data) -> click = false);
    }

    private void findView() {
        imageView = view.get().findViewById(R.id.imageView);
        imageViewCheck = view.get().findViewById(R.id.imageViewCheck);
    }

    private void setListener() {

        imageViewCheck.setOnClickListener(view -> {

            if(!imageSelectorItemBean.isSelected && !click) {
                return;
            }

            imageSelectorItemBean.isSelected = !imageSelectorItemBean.isSelected;
            toggleCheck();
        });

        imageView.setOnClickListener(view -> {

            if(!imageSelectorItemBean.isSelected && !click) {
                return;
            }

            imageSelectorItemBean.isSelected = !imageSelectorItemBean.isSelected;
            toggleCheck();
        });
    }

    private void initView() {
        imageView.post(() -> {

            ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
            layoutParams.height = imageView.getWidth();
        });
    }

    public void updateView() {
        bitmap = null;
        imageView.setImageBitmap(null);

        initESS();
        toggleCheck();

        ThreadUtil.startThread(() -> {

            int degree = BitmapUtil.readPictureDegree(imageSelectorItemBean.filePath);
            bitmap = BitmapUtil.rotateImageWithoutStore(imageSelectorItemBean.filePath, degree, 0.5f);

            ThreadUtil.startUIThread(0, () -> SHGlide.getInstance().setImageBitmap(view.get().getContext(), bitmap, imageView, null,
                    imageView.getWidth(), imageView.getHeight(), 0.7f, 0));

        });

    }

    private void toggleCheck() {

        imageSelectorItemViewHolderDelegate.onSelected();

        if(imageSelectorItemBean.isSelected) {
            imageViewCheck.setImageResource(R.drawable.ti_check);
        }
        else {
            imageViewCheck.setImageResource(R.drawable.ti_un_check);
        }
    }

    private void removeESS() {
        ILog.iLogDebug(TAG, "removeESS");
        EventCenter.getInstance().removeAllObserver(this);
    }

    @Override
    protected void finalize() throws Throwable {
        removeESS();
        ILog.iLogDebug(TAG, "finalize");
        super.finalize();
    }
}

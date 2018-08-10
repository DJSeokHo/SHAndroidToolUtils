package com.swein.framework.template.doublescroll.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

public class SHHorizontalScrollView extends HorizontalScrollView {

    public SHHorizontalScrollView(Context context) {
        super(context);
    }

    public SHHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SHHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SHHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public interface SHHorizontalScrollViewDelegate {
        void onScrollChanged(int l, int t, int oldl, int oldt);
    }

    private SHHorizontalScrollViewDelegate shHorizontalScrollViewDelegate;

    public void setOnScrollDelegate(SHHorizontalScrollViewDelegate shHorizontalScrollViewDelegate) {
        this.shHorizontalScrollViewDelegate = shHorizontalScrollViewDelegate;
    }



    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        if (shHorizontalScrollViewDelegate != null) {
            shHorizontalScrollViewDelegate.onScrollChanged(l, t, oldl, oldt);
        }
    }

}

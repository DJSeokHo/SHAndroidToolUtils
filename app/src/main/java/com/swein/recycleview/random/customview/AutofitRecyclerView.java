package com.swein.recycleview.random.customview;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.swein.recycleview.random.data.RecyclerViewRandomData;

import static com.swein.recycleview.random.data.manager.ItemPositionManager.MAX_LENGTH;

/**
 * Created by seokho on 08/03/2017.
 */

public class AutofitRecyclerView extends RecyclerView {

    private static GridLayoutManager gridLayoutManager;

    private int mColumnWidth = -1;

    public AutofitRecyclerView(Context context) {
        super(context);
        init(context);
    }

    public AutofitRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AutofitRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        gridLayoutManager = new GridLayoutManager(context, MAX_LENGTH, VERTICAL, false);
        setLayoutManager(gridLayoutManager);
    }

    public void setColumnWidth(int columnWidth) {
        mColumnWidth = columnWidth;
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);

        if (mColumnWidth > 0) {

            gridLayoutManager.setSpanSizeLookup( new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize( int position ) {

                    return RecyclerViewRandomData.getInstance().getColList().get( position );
                }
            } );
        }
    }

    public static int returnLastVisibleItemPosition() {
        return gridLayoutManager.findLastVisibleItemPosition();
    }
}

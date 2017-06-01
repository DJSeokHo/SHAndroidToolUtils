package com.swein.recycleview.random.customview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;

import com.swein.recycleview.random.data.ListItemData;
import com.swein.recycleview.random.data.RecyclerViewRandomData;
import com.swein.recycleview.random.manager.content.ItemPosition;

import static com.swein.recycleview.random.manager.ItemPositionManager.LEVEL_FIVE_SPACE_RATIO;
import static com.swein.recycleview.random.manager.ItemPositionManager.LEVEL_FOUR_SPACE_RATIO;
import static com.swein.recycleview.random.manager.ItemPositionManager.LEVEL_ONE_SPACE_RATIO;
import static com.swein.recycleview.random.manager.ItemPositionManager.LEVEL_SIX_SPACE_RATIO;
import static com.swein.recycleview.random.manager.ItemPositionManager.LEVEL_THREE_SPACE_RATIO;
import static com.swein.recycleview.random.manager.ItemPositionManager.LEVEL_TWO_SPACE_RATIO;
import static com.swein.recycleview.random.manager.ItemPositionManager.STRING_LENGTH_NORMAL;
import static com.swein.recycleview.random.manager.ItemPositionManager.STRING_LENGTH_SHORT;
import static com.swein.recycleview.random.manager.ItemPositionManager.STRING_LENGTH_ZERO;

/**
 *
 * Created by seokho on 13/03/2017.
 */

public class RecyclerViewItemSetting extends RecyclerView.ItemDecoration {

    private int screenWidth;

    public RecyclerViewItemSetting(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();

        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            ((Activity)context).getWindowManager().getDefaultDisplay().getRealMetrics( displayMetrics);
            screenWidth = displayMetrics.widthPixels;
        }
    }

    @Override
    public void getItemOffsets( Rect outRect, View view, RecyclerView parent, RecyclerView.State state ) {
        super.getItemOffsets( outRect, view, parent, state );


        int position = parent.getChildLayoutPosition(view);

//        ILog.iLogDebug( RecyclerViewRandomData.class.getSimpleName(),
//                        view.getId() + " " + RecyclerViewRandomData.getInstance().getList().get( position ).tagName + " [" +
//                        RecyclerViewRandomData.getInstance().getList().get( position ).tagName.length() + "] " +
//                        RecyclerViewRandomData.getInstance().getColList().get( position ) + " " +
//                        RecyclerViewRandomData.getInstance().getList().get( position ).itemPosition);

        ListItemData listItemData;

        listItemData = RecyclerViewRandomData.getInstance().getList().get( position );

        if(listItemData.itemPosition.equals( ItemPosition.LEFT )) {
            if(STRING_LENGTH_ZERO < listItemData.tagName.length() && STRING_LENGTH_SHORT >= listItemData.tagName.length()) {

                //item dp margin
                outRect.left = (int)(screenWidth * LEVEL_ONE_SPACE_RATIO);
                outRect.right = -(int)(screenWidth * LEVEL_TWO_SPACE_RATIO);
            }
            else if(STRING_LENGTH_SHORT < listItemData.tagName.length() && STRING_LENGTH_NORMAL >= listItemData.tagName.length()) {

                //item dp margin
                outRect.left = (int)(screenWidth * LEVEL_THREE_SPACE_RATIO);
                outRect.right = -(int)(screenWidth * LEVEL_FOUR_SPACE_RATIO);
            }
        }
        else if(listItemData.itemPosition.equals( ItemPosition.RIGHT)) {
            if(STRING_LENGTH_ZERO < listItemData.tagName.length() && STRING_LENGTH_SHORT >= listItemData.tagName.length()) {

                //item dp margin
                outRect.right = (int)(screenWidth * LEVEL_ONE_SPACE_RATIO);
                outRect.left = -(int)(screenWidth * LEVEL_TWO_SPACE_RATIO);
            }
            else if(STRING_LENGTH_SHORT < listItemData.tagName.length() && STRING_LENGTH_NORMAL >= listItemData.tagName.length()) {

                //item dp margin
                outRect.right = (int)(screenWidth * LEVEL_THREE_SPACE_RATIO);
                outRect.left = -(int)(screenWidth * LEVEL_FOUR_SPACE_RATIO);
            }
        }
        else if(listItemData.itemPosition.equals( ItemPosition.MID)) {
            if(STRING_LENGTH_ZERO < listItemData.tagName.length() && STRING_LENGTH_SHORT >= listItemData.tagName.length()) {

                //item dp margin
                outRect.right = -(int)(screenWidth * LEVEL_FIVE_SPACE_RATIO);
                outRect.left = -(int)(screenWidth * LEVEL_FIVE_SPACE_RATIO);
            }
            else if(STRING_LENGTH_SHORT < listItemData.tagName.length() && STRING_LENGTH_NORMAL >= listItemData.tagName.length()) {

                //item dp margin
                outRect.right = (int)(screenWidth * LEVEL_SIX_SPACE_RATIO);
                outRect.left = (int)(screenWidth * LEVEL_SIX_SPACE_RATIO);
            }
            else {

                //item dp margin
                outRect.right = (int)(screenWidth * LEVEL_FIVE_SPACE_RATIO);
                outRect.left = (int)(screenWidth * LEVEL_FIVE_SPACE_RATIO);
            }
        }
    }
}

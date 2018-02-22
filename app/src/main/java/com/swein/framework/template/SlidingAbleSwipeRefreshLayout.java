package com.swein.framework.template;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 *
 * Created by seokho on 22/02/2018.
 */
public class SlidingAbleSwipeRefreshLayout extends SwipeRefreshLayout {

    private float startY;
    private float startX;

    private boolean isViewDragged;
    private final int touchSlop;

    public SlidingAbleSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();

        switch (action) {

            case MotionEvent.ACTION_DOWN:

                startY = ev.getY();
                startX = ev.getX();

                isViewDragged = false;

                break;

            case MotionEvent.ACTION_MOVE:

                if(isViewDragged) {
                    return false;
                }

                float endY = ev.getY();
                float endX = ev.getX();
                float distanceX = Math.abs(endX - startX);
                float distanceY = Math.abs(endY - startY);

                if(distanceX > touchSlop && distanceX > distanceY) {
                    isViewDragged = true;
                    return false;
                }
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:

                isViewDragged = false;

                break;
        }

        return super.onInterceptTouchEvent(ev);
    }

}

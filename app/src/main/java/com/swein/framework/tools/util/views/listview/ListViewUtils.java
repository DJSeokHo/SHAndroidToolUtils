package com.swein.framework.tools.util.views.listview;

import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

/**
 * Created by seokho on 13/09/2017.
 */

public class ListViewUtils {

    /**
     * this method can get a touch listener with dispatch touch event to parent
     * @return
     */
    public static View.OnTouchListener getListViewDispathOnTouchListenerToParent() {

        return new ListView.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        view.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        view.getParent().requestDisallowInterceptTouchEvent(false);
                        break;

                    case MotionEvent.ACTION_MOVE:
                        view.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                view.onTouchEvent(motionEvent);

                return true;
            }
        };
    }


}

package com.swein.framework.template.doublescroll;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;

import com.swein.framework.template.doublescroll.custom.SHHorizontalScrollView;
import com.swein.framework.tools.util.animation.AnimationUtil;
import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.framework.tools.util.display.DisplayUtils;
import com.swein.shandroidtoolutils.R;

public class SHDoubleScrollActivity extends Activity {

    private final static String TAG = "SHDoubleScrollActivity";

    private final static int CARD_WIDTH = 330;
    private final static int SPACE_WIDTH = 15;

    private HorizontalScrollView horizontalScrollView;
    private SHHorizontalScrollView horizontalScrollViewSmall;

    private FrameLayout viewR;
    private FrameLayout viewB;

    private View viewSpace;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shdouble_scroll);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        final int width = size.x;

        horizontalScrollView = findViewById(R.id.horizontalScrollView);
        horizontalScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        viewR = findViewById(R.id.viewR);
        ViewGroup.LayoutParams viewRLayoutParams = viewR.getLayoutParams();
        viewRLayoutParams.width = width;
        viewR.setLayoutParams(viewRLayoutParams);

        viewB = findViewById(R.id.viewB);
        ViewGroup.LayoutParams viewGLayoutParams = viewB.getLayoutParams();
        viewGLayoutParams.width = width;
        viewB.setLayoutParams(viewGLayoutParams);


        horizontalScrollViewSmall = findViewById(R.id.horizontalScrollViewSmall);

        horizontalScrollViewSmall.setOnScrollDelegate(new SHHorizontalScrollView.SHHorizontalScrollViewDelegate() {

            @Override
            public void onScrollChanged(int l, final int t, int oldl, int oldt) {
                ILog.iLogDebug(TAG, l + " " + t + " " + oldl + " " + oldt + " -- " + DisplayUtils.pxToDip(SHDoubleScrollActivity.this, l));
                horizontalScrollView.scrollTo(l, t);

                if(DisplayUtils.pxToDip(SHDoubleScrollActivity.this, l) == (CARD_WIDTH - SPACE_WIDTH)) {
                    /*
                        right end
                     */
                    AnimationUtil.scrollViewSmoothScrollToX(horizontalScrollView, width, 100, null);

                }

                if(DisplayUtils.pxToDip(SHDoubleScrollActivity.this, l) == 0) {
                    /*
                        left end
                     */

                    horizontalScrollView.smoothScrollTo(0, t);
                }
            }
        });


        viewSpace = findViewById(R.id.viewSpace);
        horizontalScrollViewSmall.post(new Runnable() {

            @Override
            public void run() {

                ILog.iLogDebug(TAG, horizontalScrollViewSmall.getWidth() + " " + horizontalScrollViewSmall.getHeight());

                ViewGroup.LayoutParams viewGLayoutParams = viewSpace.getLayoutParams();
                viewGLayoutParams.width = width - DisplayUtils.dipToPx(SHDoubleScrollActivity.this, CARD_WIDTH) - DisplayUtils.dipToPx(SHDoubleScrollActivity.this, SPACE_WIDTH);
                viewSpace.setLayoutParams(viewGLayoutParams);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}

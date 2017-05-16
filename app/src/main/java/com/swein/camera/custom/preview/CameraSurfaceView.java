package com.swein.camera.custom.preview;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;

import com.swein.camera.custom.camera.CameraOperate;
import com.swein.camera.custom.draw.FocusAreaView;
import com.swein.framework.tools.util.debug.log.ILog;

import static com.swein.camera.custom.data.CameraContent.TOUCH_FOCUS_AREA_RECT_SIZE_EDGE_LENGTH;

/**
 * Created by seokho on 16/05/2017.
 */

public class CameraSurfaceView extends SurfaceView {

    final private static String TAG = "CameraSurfaceView";

    private CameraOperate cameraOperate;
    private FocusAreaView focusAreaView;

    private boolean focusAreaViewSet = false;
    private boolean listenerSet = false;

    public CameraSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
    }

    private Rect createTouchRect(float x, float y, int sizeLength) {
        return new Rect((int)(x - sizeLength), (int)(y - sizeLength), (int)(x + sizeLength), (int)(y + sizeLength));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!listenerSet) {
            return false;
        }

        if(event.getAction() == MotionEvent.ACTION_DOWN || 1 == event.getPointerCount()) {

            float x = event.getX();
            float y = event.getY();

            ILog.iLogDebug("\n\nonTouchEvent ", x + " " + y);

            Rect touchRect = createTouchRect(x, y, TOUCH_FOCUS_AREA_RECT_SIZE_EDGE_LENGTH);

            ILog.iLogDebug("touch rect is ", touchRect.left + " " + touchRect.right + " " + touchRect.top + " " + touchRect.bottom);

            final Rect targetFocusRect = new Rect(
                    touchRect.left * 2000/this.getWidth() - 1000,
                    touchRect.top * 2000/this.getHeight() - 1000,
                    touchRect.right * 2000/this.getWidth() - 1000,
                    touchRect.bottom * 2000/this.getHeight() - 1000);

            ILog.iLogDebug("left", "touchRect.left " + touchRect.left + " width " + this.getWidth() + " " + (touchRect.left * 2000/this.getWidth() - 1000));
            ILog.iLogDebug("top", "touchRect.left " + touchRect.top + " height " + this.getHeight() + " " + (touchRect.top * 2000/this.getHeight() - 1000));
            ILog.iLogDebug("right", "touchRect.left " + touchRect.right + " width " + this.getWidth() + " " + (touchRect.right * 2000/this.getWidth() - 1000));
            ILog.iLogDebug("bottom", "touchRect.left " + touchRect.bottom + " height " + this.getHeight() + " " + (touchRect.bottom * 2000/this.getHeight() - 1000));

            ILog.iLogDebug("target Focus Rect is ", targetFocusRect.left + " " + targetFocusRect.right + " " + targetFocusRect.top + " " + targetFocusRect.bottom);


            cameraOperate.doTouchFocus(targetFocusRect);
            if (focusAreaViewSet) {
                focusAreaView.setHaveTouch(true, touchRect);
                focusAreaView.invalidate();

                // Remove the square after some time
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        focusAreaView.setHaveTouch(false, new Rect(0, 0, 0, 0));
                        focusAreaView.invalidate();
                    }
                }, 1000);
            }
        }
        return false;
    }

    /**
     * set CameraOperate instance for touch focus.
     * @param cameraOperate - CameraOperate
     */
    public void setListener(CameraOperate cameraOperate) {
        this.cameraOperate = cameraOperate;
        listenerSet = true;
    }

    /**
     * set FocusAreaView instance for touch focus indication.
     * @param focusAreaView - FocusAreaView
     */
    public void setDrawingView(FocusAreaView focusAreaView) {
        this.focusAreaView = focusAreaView;
        this.focusAreaViewSet = true;
    }
}

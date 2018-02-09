package com.swein.framework.tools.util.views.motionevent;

import android.view.MotionEvent;
import android.view.VelocityTracker;

/**
 * Created by seokho on 09/02/2018.
 */

public class ViewMotionEventUtil {

    public interface ViewMotionEventUtilDelegate {

        void onVelocityResult(int velocity);

    }

    public static void getVelocityX(MotionEvent motionEvent, int second, ViewMotionEventUtilDelegate viewMotionEventUtilDelegate) {
        VelocityTracker velocityTracker = VelocityTracker.obtain();
        velocityTracker.addMovement(motionEvent);
        velocityTracker.computeCurrentVelocity(second * 1000);

        viewMotionEventUtilDelegate.onVelocityResult((int) velocityTracker.getXVelocity());

        velocityTracker.clear();
        velocityTracker.recycle();
    }

    public static void getVelocityY(MotionEvent motionEvent, int second, ViewMotionEventUtilDelegate viewMotionEventUtilDelegate) {
        VelocityTracker velocityTracker = VelocityTracker.obtain();
        velocityTracker.addMovement(motionEvent);
        velocityTracker.computeCurrentVelocity(second * 1000);

        viewMotionEventUtilDelegate.onVelocityResult((int) velocityTracker.getXVelocity());

        velocityTracker.clear();
        velocityTracker.recycle();
    }

}

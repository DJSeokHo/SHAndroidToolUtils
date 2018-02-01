package com.swein.framework.module.dragmenu.presenter;

import android.content.Context;
import android.support.v4.view.VelocityTrackerCompat;
import android.view.MotionEvent;
import android.view.VelocityTracker;

import com.swein.framework.module.dragmenu.presenter.method.DMPresenterMethod;
import com.swein.framework.module.dragmenu.view.activity.method.DMMainActivityMethod;
import com.swein.framework.module.dragmenu.view.holder.actionbar.method.ActionBarViewHolderMethod;
import com.swein.framework.module.dragmenu.view.holder.dragmenu.method.DragMenuViewHolderMethod;
import com.swein.framework.tools.util.device.DeviceInfoUtil;

/**
 * Created by seokho on 07/09/2017.
 */

public class DMPresenter implements DMPresenterMethod {

    private long flickTime;

    private VelocityTracker velocityTracker;

    private boolean isMoveUp;

    private float touchDown;

    private float screenCentY;

    private ActionBarViewHolderMethod actionBarViewHolderMethod;
    private DragMenuViewHolderMethod dragMenuViewHolderMethod;
    private DMMainActivityMethod dmMainActivityMethod;

    public DMPresenter(ActionBarViewHolderMethod actionBarViewHolderMethod,
                       DragMenuViewHolderMethod dragMenuViewHolderMethod,
                       DMMainActivityMethod dmMainActivityMethod,
                       Context context) {

        this.actionBarViewHolderMethod = actionBarViewHolderMethod;
        this.dragMenuViewHolderMethod = dragMenuViewHolderMethod;
        this.dmMainActivityMethod = dmMainActivityMethod;

        screenCentY = DeviceInfoUtil.getDeviceScreenHeight(context) * 0.5f;
    }

    @Override
    public boolean onActionBarDragArrowTouch(MotionEvent event) {

        int index = event.getActionIndex();
        int pointerId = event.getPointerId(index);

        if(event.getAction() == MotionEvent.ACTION_DOWN) {

            flickTime = System.currentTimeMillis();
            touchDown = event.getY();

            if(velocityTracker == null) {
                // Retrieve a new VelocityTracker object to watch the velocity of a motion.
                velocityTracker = VelocityTracker.obtain();
            }
            else {
                // Reset the velocity tracker back to its initial state.
                velocityTracker.clear();
            }
        }

        if(event.getAction() == MotionEvent.ACTION_MOVE) {

            dmMainActivityMethod.addDragMenu();

            velocityTracker.addMovement(event);

            velocityTracker.computeCurrentVelocity(1000);

            /*
             * get movement direction
             */
            if(VelocityTrackerCompat.getYVelocity(velocityTracker, pointerId) < 0) {
                isMoveUp = true;
            }
            else {
                isMoveUp = false;
            }

            /*
             * setY
             *
             * fragment will moveUp&Down with your touch point and can not over fragment view size
             *
             * centerY means the center point of fragment view
             *
             */
            if(event.getY() >= screenCentY * 2 ) {
                dragMenuViewHolderMethod.getDragMenuView().setY(screenCentY * 2.0f);
//                dragMenuViewHolder.getDragMenuView().setY(screenCentY * 2f);
            }
            else {
//                dragMenuViewHolder.getDragMenuView().setY(event.getY() - screenCentY * 2f);
                dragMenuViewHolderMethod.getDragMenuView().setY(event.getY() - screenCentY * 2.0f);
            }
        }

        if(event.getAction() == MotionEvent.ACTION_UP) {

            float touchUp = event.getY();

            /*
                just clicked
             */
            if(0 == Math.abs(touchUp - touchDown)) {
                dmMainActivityMethod.dragMenuAutoDownToBottom();
                actionBarViewHolderMethod.triggerActionBarDragArrowDownVisible();
                dragMenuViewHolderMethod.triggerDragMenuDragArrowUpVisible();
                return true;
            }

            /*
             * movement small than centerY * 0.8f, fragment will gone
             */
            if (event.getY() < screenCentY * 0.8f) {

                flickTime = System.currentTimeMillis() - flickTime;

                /*
                 * if velocity( movement / flickTime) > 1 then is flick.
                 * so fragment will show
                 */
                if ((touchUp - touchDown) / flickTime > 1) {
                    dmMainActivityMethod.dragMenuAutoDownToBottom();
                    actionBarViewHolderMethod.triggerActionBarDragArrowDownVisible();
                    dragMenuViewHolderMethod.triggerDragMenuDragArrowUpVisible();
                    return true;
                }

            }
            /*
             * movement big than centerY * 0.8f
             */
            else {

                /*
                 * if movement out of this view size than just show fragment
                 */
                if (event.getY() >= screenCentY * 2f) {
                    return true;
                }

                /*
                 * if movement direction is up
                 * user maybe want to not show fragment
                 * else
                 * show fragment
                 */
                if (isMoveUp) {
//                    dragMenuViewHolder.autoHideDragMenu();
                }
                else {
//                    dragMenuAutoDownToBottom();
//                    iActionBarViewHolder.triggerActionBarDragArrowDownVisible();
                    dmMainActivityMethod.dragMenuAutoDownToBottom();
                    actionBarViewHolderMethod.triggerActionBarDragArrowDownVisible();
                    dragMenuViewHolderMethod.triggerDragMenuDragArrowUpVisible();
                }
            }
        }

        return false;
    }



}

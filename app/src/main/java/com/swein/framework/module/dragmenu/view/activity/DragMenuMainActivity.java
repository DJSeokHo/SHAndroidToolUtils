package com.swein.framework.module.dragmenu.view.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.VelocityTrackerCompat;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.widget.FrameLayout;

import com.swein.framework.module.dragmenu.view.fragment.DragMenuFragment;
import com.swein.framework.module.dragmenu.module.actionbar.viewholder.ActionBarViewHolder;
import com.swein.framework.module.dragmenu.module.actionbar.methods.ActionBarViewHolderMethods;
import com.swein.framework.module.dragmenu.module.dragmenu.viewholder.DragMenuViewHolder;
import com.swein.framework.module.dragmenu.module.dragmenu.methods.DragMenuViewHolderMethods;
import com.swein.framework.tools.util.activity.ActivityUtils;
import com.swein.framework.tools.util.device.DeviceInfoUtils;
import com.swein.shandroidtoolutils.R;

public class DragMenuMainActivity extends FragmentActivity {

    private DragMenuViewHolder dragMenuViewHolder;
    private ActionBarViewHolder actionBarViewHolder;

    private FrameLayout frameLayoutDragMenu;

    private long flickTime;

    private VelocityTracker velocityTracker;

    private boolean isMoveUp;

    private float touchDown;

    private float screenCentY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_drag_menu);

        ActivityUtils.fullScreen(this);

        ActivityUtils.addFragment(this, R.id.containerDragMenuFragment, DragMenuFragment.newInstance(), false);

        frameLayoutDragMenu = (FrameLayout) findViewById(R.id.frameLayoutDragMenu);
        FrameLayout frameLayoutActionBar = (FrameLayout) findViewById(R.id.frameLayoutActionBar);

        dragMenuViewHolder = new DragMenuViewHolder(this, new DragMenuViewHolderMethods() {
            @Override
            public boolean onHideDragMenuTouched(MotionEvent event) {
                return hideDragMenuByTouch(event);
            }
        });

        actionBarViewHolder = new ActionBarViewHolder(this, new ActionBarViewHolderMethods() {

            @Override
            public boolean onTriggerDragMenuTouched(MotionEvent event) {
                return triggerDragMenuByTouch(event);
            }
        });

        frameLayoutActionBar.addView(actionBarViewHolder.getActionBarView());

        // cent point y of screen
        screenCentY = DeviceInfoUtils.getDeviceScreenHeight(DragMenuMainActivity.this) * 0.5f;
    }

    private void addDragMenu() {
        if(1 <= frameLayoutDragMenu.getChildCount()) {
            return;
        }
        frameLayoutDragMenu.addView(dragMenuViewHolder.getDragMenuView());
    }

    private void autoHideDragMenu() {
        if(0 == frameLayoutDragMenu.getChildCount()) {
            return;
        }
        dragMenuViewHolder.setDragMenuViewHideAnimation(this);
        frameLayoutDragMenu.removeView(dragMenuViewHolder.getDragMenuView());
        actionBarViewHolder.showImageButtonDragDown();
        dragMenuViewHolder.hideImageButtonHideMenu();
    }

    private void actionBarHideImageButtonDrag() {
        actionBarViewHolder.hideImageButtonDragDown();
    }

    private void dragMenuAutoDownToBottom() {
        dragMenuViewHolder.getDragMenuView().animate().setDuration(350).translationY(0);
        dragMenuViewHolder.showImageButtonHideMenu();
    }

    private boolean hideDragMenuByTouch(MotionEvent event) {

        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            touchDown = event.getY();
        }

        if(event.getAction() == MotionEvent.ACTION_MOVE) {

        }

        if(event.getAction() == MotionEvent.ACTION_UP) {
            float touchUp = event.getY();

            if(0 == Math.abs(touchUp - touchDown)) {
                autoHideDragMenu();
            }
        }

        return false;
    }

    private boolean triggerDragMenuByTouch(MotionEvent event) {
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

            addDragMenu();

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
                dragMenuViewHolder.getDragMenuView().setY(screenCentY * 2f);
            }
            else {
                dragMenuViewHolder.getDragMenuView().setY(event.getY() - screenCentY * 2f);
            }
        }

        if(event.getAction() == MotionEvent.ACTION_UP) {

            float touchUp = event.getY();

            /*
                just clicked
             */
            if(0 == Math.abs(touchUp - touchDown)) {
                dragMenuAutoDownToBottom();
                actionBarHideImageButtonDrag();
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
                    dragMenuAutoDownToBottom();
                    actionBarHideImageButtonDrag();
                    return true;
                }

                autoHideDragMenu();
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
                    autoHideDragMenu();
                }
                else {
                    dragMenuAutoDownToBottom();
                    actionBarHideImageButtonDrag();
                }
            }
        }

        return false;
    }

}

package com.swein.framework.module.dragmenu.view.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import com.swein.framework.module.dragmenu.presenter.DMPresenter;
import com.swein.framework.module.dragmenu.presenter.method.DMPresenterMethod;
import com.swein.framework.module.dragmenu.view.activity.method.DMMainActivityMethod;
import com.swein.framework.module.dragmenu.view.fragment.DMFragment;
import com.swein.framework.module.dragmenu.view.holder.actionbar.ActionBarViewHolder;
import com.swein.framework.module.dragmenu.view.holder.actionbar.method.ActionBarViewHolderMethod;
import com.swein.framework.module.dragmenu.view.holder.dragmenu.DragMenuViewHolder;
import com.swein.framework.module.dragmenu.view.holder.dragmenu.method.DragMenuViewHolderMethod;
import com.swein.framework.tools.util.activity.ActivityUtil;
import com.swein.framework.tools.util.window.WindowUtil;
import com.swein.shandroidtoolutils.R;

public class DMMainActivity extends FragmentActivity implements DMMainActivityMethod {

    private FrameLayout frameLayoutDragMenu;

    private ActionBarViewHolderMethod actionBarViewHolderMethod;
    private DragMenuViewHolderMethod dragMenuViewHolderMethod;

    private DMPresenterMethod dmPresenterMethod;
//
//    private long flickTime;
//
//    private VelocityTracker velocityTracker;
//
//    private boolean isMoveUp;
//
//    private float touchDown;
//
//    private float screenCentY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_drag_menu);

        WindowUtil.fullScreen(this);

        // fragment
        ActivityUtil.addFragment(this, R.id.containerDMFragment, DMFragment.newInstance(), false);

        // actionbar view holder
        FrameLayout frameLayoutActionBar = (FrameLayout) findViewById(R.id.frameLayoutActionBar);

        actionBarViewHolderMethod = new ActionBarViewHolder(this, this);
        frameLayoutActionBar.addView(actionBarViewHolderMethod.getActionBarView());


        // drag menu view holder
        frameLayoutDragMenu = (FrameLayout) findViewById(R.id.frameLayoutDragMenu);
        dragMenuViewHolderMethod = new DragMenuViewHolder(this);


        // presenter init
        dmPresenterMethod = new DMPresenter(actionBarViewHolderMethod, dragMenuViewHolderMethod, this, this);





//        actionBarViewHolder = new SHMDMActionBarViewHolder(this, new ActionBarViewHolderMethods() {
//
//            @Override
//            public boolean onTriggerDragMenuTouched(MotionEvent event) {
//                return triggerDragMenuByTouch(event);
//            }
//        });



//        dragMenuViewHolder = new DragMenuViewHolder(this, new DragMenuViewHolderMethods() {
//            @Override
//            public void onDragMenuHideListener() {
//                actionBarViewHolder.showImageButtonDragDown();
//            }
//        }, frameLayoutDragMenu);
//
//        actionBarViewHolder = new SHMDMActionBarViewHolder(this, new ActionBarViewHolderMethods() {
//
//            @Override
//            public boolean onTriggerDragMenuTouched(MotionEvent event) {
//                return triggerDragMenuByTouch(event);
//            }
//        });
//

//        // cent point y of screen
//        screenCentY = DeviceInfoUtil.getDeviceScreenHeight(DMMainActivity.this) * 0.5f;
    }

    @Override
    public boolean onActionBarDragArrowTouch(MotionEvent event) {
        return dmPresenterMethod.onActionBarDragArrowTouch(event);
    }

    @Override
    public void addDragMenu() {
        if(1 <= frameLayoutDragMenu.getChildCount()) {
            return;
        }
        frameLayoutDragMenu.addView(dragMenuViewHolderMethod.getDragMenuView());
    }
//
//    private void actionBarHideImageButtonDrag() {
//        actionBarViewHolder.hideImageButtonDragDown();
//    }

    @Override
    public void dragMenuAutoDownToBottom() {
        dragMenuViewHolderMethod.getDragMenuView().animate().setDuration(350).translationY(0);
    }

//    private boolean triggerDragMenuByTouch(MotionEvent event) {
//        int index = event.getActionIndex();
//        int pointerId = event.getPointerId(index);
//
//        if(event.getAction() == MotionEvent.ACTION_DOWN) {
//
//            flickTime = System.currentTimeMillis();
//            touchDown = event.getY();
//
//            if(velocityTracker == null) {
//                // Retrieve a new VelocityTracker object to watch the velocity of a motion.
//                velocityTracker = VelocityTracker.obtain();
//            }
//            else {
//                // Reset the velocity tracker back to its initial state.
//                velocityTracker.clear();
//            }
//        }
//
//        if(event.getAction() == MotionEvent.ACTION_MOVE) {
//
//            addDragMenu();
//
//            velocityTracker.addMovement(event);
//
//            velocityTracker.computeCurrentVelocity(1000);
//
//            /*
//             * get movement direction
//             */
//            if(VelocityTrackerCompat.getYVelocity(velocityTracker, pointerId) < 0) {
//                isMoveUp = true;
//            }
//            else {
//                isMoveUp = false;
//            }
//
//            /*
//             * setY
//             *
//             * fragment will moveUp&Down with your touch point and can not over fragment view size
//             *
//             * centerY means the center point of fragment view
//             *
//             */
//            if(event.getY() >= screenCentY * 2 ) {
//                dragMenuViewHolder.getDragMenuView().setY(screenCentY * 2f);
//            }
//            else {
//                dragMenuViewHolder.getDragMenuView().setY(event.getY() - screenCentY * 2f);
//            }
//        }
//
//        if(event.getAction() == MotionEvent.ACTION_UP) {
//
//            float touchUp = event.getY();
//
//            /*
//                just clicked
//             */
//            if(0 == Math.abs(touchUp - touchDown)) {
//                dragMenuAutoDownToBottom();
//                actionBarHideImageButtonDrag();
//                return true;
//            }
//
//            /*
//             * movement small than centerY * 0.8f, fragment will gone
//             */
//            if (event.getY() < screenCentY * 0.8f) {
//
//                flickTime = System.currentTimeMillis() - flickTime;
//
//                /*
//                 * if velocity( movement / flickTime) > 1 then is flick.
//                 * so fragment will show
//                 */
//                if ((touchUp - touchDown) / flickTime > 1) {
//                    dragMenuAutoDownToBottom();
//                    actionBarHideImageButtonDrag();
//                    return true;
//                }
//
//                dragMenuViewHolder.autoHideDragMenu();
//            }
//            /*
//             * movement big than centerY * 0.8f
//             */
//            else {
//
//                /*
//                 * if movement out of this view size than just show fragment
//                 */
//                if (event.getY() >= screenCentY * 2f) {
//                    return true;
//                }
//
//                /*
//                 * if movement direction is up
//                 * user maybe want to not show fragment
//                 * else
//                 * show fragment
//                 */
//                if (isMoveUp) {
//                    dragMenuViewHolder.autoHideDragMenu();
//                }
//                else {
//                    dragMenuAutoDownToBottom();
//                    actionBarHideImageButtonDrag();
//                }
//            }
//        }
//
//        return false;
//    }

}

package com.swein.framework.module.dragmenu.view.holder.dragmenu;

import android.app.Activity;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;

import com.swein.framework.module.dragmenu.view.holder.dragmenu.method.DragMenuViewHolderMethod;
import com.swein.framework.tools.util.device.DeviceInfoUtils;
import com.swein.shandroidtoolutils.R;

/**
 *
 * Created by seokho on 28/08/2017.
 */

public class DragMenuViewHolder implements DragMenuViewHolderMethod {

    private View dragMenuView;
    private ImageButton imageButtonDragArrowUp;
    private Context context;

//    private float touchDown;

    public DragMenuViewHolder(Context context) {
        dragMenuView = ((Activity)context).getLayoutInflater().inflate(R.layout.viewholder_drag_menu, null);

        this.context = context;

        imageButtonDragArrowUp = (ImageButton) dragMenuView.findViewById(R.id.imageButtonDragArrowUp);
        imageButtonDragArrowUp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return false;
            }
        });

    }

    @Override
    public View getDragMenuView() {
        return dragMenuView;
    }

    private void setDragMenuViewHideAnimation(Context context) {
        TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, 0, DeviceInfoUtils.getDeviceScreenHeight(context) * -1);
        translateAnimation.setDuration(350);
        translateAnimation.setFillAfter(true);
        translateAnimation.start();
        dragMenuView.setAnimation(translateAnimation);
    }

    @Override
    public void triggerDragMenuDragArrowUpVisible() {
        if(View.VISIBLE == imageButtonDragArrowUp.getVisibility()) {
            AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
            alphaAnimation.setDuration(350);
            alphaAnimation.start();
            imageButtonDragArrowUp.setAnimation(alphaAnimation);
            imageButtonDragArrowUp.setVisibility(View.GONE);
        }
        else {
            AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
            alphaAnimation.setDuration(350);
            alphaAnimation.start();
            imageButtonDragArrowUp.setAnimation(alphaAnimation);
            imageButtonDragArrowUp.setVisibility(View.VISIBLE);
        }
    }

//    public void autoHideDragMenu() {
//        if(0 == rootParentFrameLayout.getChildCount()) {
//            return;
//        }
//        setDragMenuViewHideAnimation(context);
//        rootParentFrameLayout.removeView(dragMenuView);
//        dragMenuViewHolderMethods.onDragMenuHideListener();
//        hideImageButtonHideMenu();
//    }

//    @Override
//    public boolean onDragMenuHideListener(MotionEvent event) {
//
//
//        if(event.getAction() == MotionEvent.ACTION_DOWN) {
//            touchDown = event.getY();
//        }
//
//        if(event.getAction() == MotionEvent.ACTION_MOVE) {
//
//        }
//
//        if(event.getAction() == MotionEvent.ACTION_UP) {
//            float touchUp = event.getY();
//
//            if(0 == Math.abs(touchUp - touchDown)) {
////                autoHideDragMenu();
//            }
//        }
//
//        return false;
//    }

//    @Override
//    public void triggerDragMenuDragArrowUpVisible() {
//        if(View.VISIBLE == imageButtonHideMenu.getVisibility()) {
//            AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
//            alphaAnimation.setDuration(350);
//            alphaAnimation.start();
//            imageButtonHideMenu.setAnimation(alphaAnimation);
//            imageButtonHideMenu.setVisibility(View.GONE);
//        }
//        else {
//            AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
//            alphaAnimation.setDuration(350);
//            alphaAnimation.start();
//            imageButtonHideMenu.setAnimation(alphaAnimation);
//            imageButtonHideMenu.setVisibility(View.VISIBLE);
//        }
//    }
}

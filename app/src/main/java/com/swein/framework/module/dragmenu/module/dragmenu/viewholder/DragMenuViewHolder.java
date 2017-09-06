package com.swein.framework.module.dragmenu.module.dragmenu.viewholder;

import android.app.Activity;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;

import com.swein.framework.module.dragmenu.module.dragmenu.methods.DragMenuViewHolderMethods;
import com.swein.framework.tools.util.device.DeviceInfoUtils;
import com.swein.shandroidtoolutils.R;

/**
 *
 * Created by seokho on 28/08/2017.
 */

public class DragMenuViewHolder {

    private View dragMenuView;
    private ImageButton imageButtonHideMenu;

    private DragMenuViewHolderMethods dragMenuViewHolderMethods;

    public DragMenuViewHolder(Activity activity, DragMenuViewHolderMethods dragMenuViewHolderMethods) {
        dragMenuView = activity.getLayoutInflater().inflate(R.layout.viewholder_drag_menu, null);
        this.dragMenuViewHolderMethods = dragMenuViewHolderMethods;
        findView();
    }

    private void findView() {
        imageButtonHideMenu = (ImageButton) dragMenuView.findViewById(R.id.imageButtonHideMenu);

        imageButtonHideMenu.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return dragMenuViewHolderMethods.onHideDragMenuTouched(event);
            }
        });
    }

    public View getDragMenuView() {
        return dragMenuView;
    }

    public void setDragMenuViewHideAnimation(Context context) {
        TranslateAnimation translateAnimation = new TranslateAnimation (0, 0, 0, DeviceInfoUtils.getDeviceScreenHeight(context) * -1);
        translateAnimation.setDuration(350);
        translateAnimation.setFillAfter(true);
        translateAnimation.start();
        dragMenuView.setAnimation(translateAnimation);
    }

    public void showImageButtonHideMenu() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(350);
        alphaAnimation.start();
        imageButtonHideMenu.setAnimation(alphaAnimation);
        imageButtonHideMenu.setVisibility(View.VISIBLE);
    }

    public void hideImageButtonHideMenu() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(350);
        alphaAnimation.start();
        imageButtonHideMenu.setAnimation(alphaAnimation);
        imageButtonHideMenu.setVisibility(View.GONE);
    }

}

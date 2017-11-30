package com.swein.framework.module.dragmenu.view.holder.actionbar;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageButton;

import com.swein.framework.module.dragmenu.view.activity.method.DMMainActivityMethod;
import com.swein.framework.module.dragmenu.view.holder.actionbar.method.ActionBarViewHolderMethod;
import com.swein.shandroidtoolutils.R;

/**
 *
 * Created by seokho on 05/09/2017.
 */

public class ActionBarViewHolder implements ActionBarViewHolderMethod {

    private View actionBarView;
    private ImageButton imageButtonDragArrowDown;
    private DMMainActivityMethod dmMainActivityMethod;

    public ActionBarViewHolder(Activity activity, DMMainActivityMethod dmMainActivityMethod) {

        actionBarView = activity.getLayoutInflater().inflate(R.layout.viewholder_drag_menu_action_bar, null);
        this.dmMainActivityMethod = dmMainActivityMethod;
        findView();
    }

    @Override
    public View getActionBarView() {
        return actionBarView;
    }

    private void findView() {
        imageButtonDragArrowDown = (ImageButton) actionBarView.findViewById(R.id.imageButtonDragArrowDown);

        imageButtonDragArrowDown.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return dmMainActivityMethod.onActionBarDragArrowTouch(event);
            }
        });
    }

    @Override
    public void triggerActionBarDragArrowDownVisible() {
        if(View.VISIBLE == imageButtonDragArrowDown.getVisibility()) {
            AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
            alphaAnimation.setDuration(350);
            alphaAnimation.start();
            imageButtonDragArrowDown.setAnimation(alphaAnimation);
            imageButtonDragArrowDown.setVisibility(View.GONE);
        }
        else {
            AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
            alphaAnimation.setDuration(350);
            alphaAnimation.start();
            imageButtonDragArrowDown.setAnimation(alphaAnimation);
            imageButtonDragArrowDown.setVisibility(View.VISIBLE);
        }
    }

}

package com.swein.framework.module.dragmenu.module.actionbar.viewholder;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageButton;

import com.swein.framework.module.dragmenu.module.actionbar.methods.ActionBarViewHolderMethods;
import com.swein.shandroidtoolutils.R;

/**
 *
 * Created by seokho on 05/09/2017.
 */

public class ActionBarViewHolder {

    private View actionBarView;
    private ImageButton imageButtonDragMenu;
    private ActionBarViewHolderMethods actionBarViewHolderMethods;

    public ActionBarViewHolder(Activity activity, ActionBarViewHolderMethods actionBarViewHolderMethods) {

        this.actionBarViewHolderMethods = actionBarViewHolderMethods;

        actionBarView = activity.getLayoutInflater().inflate(R.layout.viewholder_drag_menu_action_bar, null);

        findView();
    }

    public View getActionBarView() {
        return actionBarView;
    }

    private void findView() {
        imageButtonDragMenu = (ImageButton) actionBarView.findViewById(R.id.imageButtonDragMenu);

        imageButtonDragMenu.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return actionBarViewHolderMethods.onTriggerDragMenuTouched(event);
            }
        });
    }

    public void showImageButtonDragDown() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(350);
        alphaAnimation.start();
        imageButtonDragMenu.setAnimation(alphaAnimation);

        imageButtonDragMenu.setVisibility(View.VISIBLE);
    }

    public void hideImageButtonDragDown() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(350);
        alphaAnimation.start();
        imageButtonDragMenu.setAnimation(alphaAnimation);

        imageButtonDragMenu.setVisibility(View.GONE);
    }


}

package com.swein.framework.module.mdmcustom.actionbar;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import com.swein.framework.module.mdmcustom.actionbar.method.SHMDMActionBarViewHolderMethod;
import com.swein.framework.tools.util.animation.AnimationUtil;
import com.swein.shandroidtoolutils.R;

/**
 *
 * Created by seokho on 05/09/2017.
 */

public class SHMDMActionBarViewHolder implements SHMDMActionBarViewHolderMethod {

    private View actionBarView;
    private ImageView imageViewUnderControl;

    private float angle;

    public SHMDMActionBarViewHolder(Activity activity) {

        actionBarView = activity.getLayoutInflater().inflate(R.layout.viewholder_sh_mdm_action_bar, null);
        findView();

        angle = 0.0f;
    }

    public void showUnderControl() {
        imageViewUnderControl.setVisibility(View.VISIBLE);
    }

    public void hideUnderControl() {
        imageViewUnderControl.setVisibility(View.GONE);
    }

    public void rotateUnderControlView() {
        angle += 90;

        AnimationUtil.setViewRotation(imageViewUnderControl, 1500, null, angle - 90, angle);

        if(angle >= 360) {
            angle = 0;
        }
    }

    @Override
    public View getActionBarView() {
        return actionBarView;
    }

    private void findView() {
        imageViewUnderControl = (ImageView)actionBarView.findViewById(R.id.imageViewUnderControl);
    }

}

package com.swein.framework.tools.util.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;

import com.swein.shandroidtoolutils.R;

/**
 * Created by seokho on 11/04/2017.
 */

public class AnimationUtil {

    public static void scrollViewSmoothScrollToY(ScrollView scrollView, int scrollToY, int duration, Animator.AnimatorListener animatorListener) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(scrollView, "scrollY", scrollToY);
        if (null != animatorListener) {
            objectAnimator.addListener(animatorListener);
        }
        objectAnimator.setDuration(duration).start();
    }

    public static void scrollViewSmoothScrollToX(HorizontalScrollView scrollView, int scrollToX, int duration, Animator.AnimatorListener animatorListener) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(scrollView, "scrollX", scrollToX);
        if (null != animatorListener) {
            objectAnimator.addListener(animatorListener);
        }
        objectAnimator.setDuration(duration).start();
    }

    public static void scrollViewSmoothScrollToX(ScrollView scrollView, int scrollToX, int duration, Animator.AnimatorListener animatorListener) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(scrollView, "scrollX", scrollToX);
        if (null != animatorListener) {
            objectAnimator.addListener(animatorListener);
        }
        objectAnimator.setDuration(duration).start();
    }

    public static void translateViewSmoothToX(View view, int x, int duration, Animator.AnimatorListener animatorListener) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "translationX", x);
        if (null != animatorListener) {
            objectAnimator.addListener(animatorListener);
        }
        objectAnimator.setDuration(duration).start();
    }

    public static void translateViewSmoothToY(View view, int y, int duration, Animator.AnimatorListener animatorListener) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "translationY", y);
        if (null != animatorListener) {
            objectAnimator.addListener(animatorListener);
        }
        objectAnimator.setDuration(duration).start();
    }

    public static void setViewToPositionX(View view, int x, int duration, Animator.AnimatorListener animatorListener) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "x", x);
        if (null != animatorListener) {
            objectAnimator.addListener(animatorListener);
        }
        objectAnimator.setDuration(duration).start();
    }

    public static void setViewToPositionY(View view, int y, int duration, Animator.AnimatorListener animatorListener) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "y", y);
        if (null != animatorListener) {
            objectAnimator.addListener(animatorListener);
        }
        objectAnimator.setDuration(duration).start();
    }

    public static void setViewRotation(View view, int duration, Animator.AnimatorListener animatorListener, float fromAngle, float toAngle) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "rotation", fromAngle, toAngle);
        if (null != animatorListener) {
            objectAnimator.addListener(animatorListener);
        }
        objectAnimator.setDuration(duration).start();
    }


    /*
        animation set element
     */
    public static PropertyValuesHolder createAnimationSetElement(String param, int value) {
        /*
            translationX
            translationY

            scrollX
            scrollY
         */
        return PropertyValuesHolder.ofFloat(param, value);
    }

    public static void viewAnimationSet(View view, int duration, PropertyValuesHolder... propertyValuesHolders) {

        ObjectAnimator.ofPropertyValuesHolder(view, propertyValuesHolders).setDuration(duration).start();

    }
    /*
        animation set element
     */

    public static void shakeView(Context context, View view) {
        Animation shake = AnimationUtils.loadAnimation(context, R.anim.shake);
        view.startAnimation(shake);
    }

}

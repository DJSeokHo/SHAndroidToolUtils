package com.swein.framework.tools.util.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.view.View;
import android.widget.ScrollView;

/**
 * Created by seokho on 11/04/2017.
 */

public class AnimationUtil {

    public static void scrollViewSmoothScrollToY(ScrollView scrollView, int scrollToY, int duration, Animator.AnimatorListener animatorListener){
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(scrollView, "scrollY",  scrollToY);
        objectAnimator.addListener(animatorListener);
        objectAnimator.setDuration(duration).start();
    }

    public static void scrollViewSmoothScrollToX(ScrollView scrollView, int scrollToX, int duration, Animator.AnimatorListener animatorListener){
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(scrollView, "scrollX",  scrollToX);
        objectAnimator.addListener(animatorListener);
        objectAnimator.setDuration(duration).start();
    }

    public static void translateViewSmoothToX(View view, int x, int duration, Animator.AnimatorListener animatorListener) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "translationX", x);
        objectAnimator.addListener(animatorListener);
        objectAnimator.setDuration(duration).start();
    }

    public static void translateViewSmoothToY(View view, int y, int duration, Animator.AnimatorListener animatorListener) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "translationY", y);
        objectAnimator.addListener(animatorListener);
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



}

package com.swein.framework.tools.util.animation;

import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.List;

/**
 * Created by seokho on 11/04/2017.
 */

public class AnimationUtils {

    public static void scrollViewSmoothScrollToY(ScrollView scrollView, int scrollToY, int duration){
        ObjectAnimator.ofInt(scrollView, "scrollY",  scrollToY).setDuration(duration).start();
    }

    public static void scrollViewSmoothScrollToX(ScrollView scrollView, int scrollToX, int duration){
        ObjectAnimator.ofInt(scrollView, "scrollX",  scrollToX).setDuration(duration).start();
    }

    public static void setViewAlphaAnimation(View view, AlphaAnimation alphaAnimation) {
        view.startAnimation( alphaAnimation );
    }

    public static void setViewRotateAnimation(View view, RotateAnimation rotateAnimation ) {
        view.startAnimation( rotateAnimation );
    }

    public static void setViewScaleAnimation(View view, ScaleAnimation scaleAnimation ) {
        view.startAnimation( scaleAnimation );
    }

    public static Animation getViewAlphaAnimation(long durationMMS, float startAlpha, float endAlpha) {

        if(0 == durationMMS) {
            return null;
        }

        AlphaAnimation alphaAnimation = new AlphaAnimation( startAlpha, endAlpha );
        alphaAnimation.setDuration( durationMMS );

        return alphaAnimation;
    }

    public static Animation getViewRotateAnimation(long durationMMS, float fromDegrees, float toDegrees, float pivotX, float pivotY ) {

        if(0 == durationMMS) {
            return null;
        }

        RotateAnimation rotateAnimation = new RotateAnimation( fromDegrees, toDegrees, pivotX, pivotY );
        rotateAnimation.setDuration( durationMMS );

        return rotateAnimation;
    }

    public static Animation getViewScaleAnimation(long durationMMS, float fromX, float toX, float fromY, float toY ) {

        if(0 == durationMMS) {
            return null;
        }

        ScaleAnimation scaleAnimation = new ScaleAnimation( fromX, toX, fromY, toY );
        scaleAnimation.setDuration( durationMMS );

        return scaleAnimation;
    }

    public static void setViewAnimations( View view,  List<Animation> list ) {

        if(list.isEmpty()) {
            return;
        }

        AnimationSet animationSet = new AnimationSet( true );

        for(Animation animation : list) {
            animationSet.addAnimation( animation );
        }

        view.startAnimation( animationSet );
    }

    public static void setLinearLayoutAnimationSubViewScale(LinearLayout linearLayout, int resourceID, long durationMMS) {

        if(0 == durationMMS) {
            return;
        }

        linearLayout = (LinearLayout)linearLayout.findViewById( resourceID );
        ScaleAnimation scaleAnimation = new ScaleAnimation( 0, 1, 0, 1 );
        scaleAnimation.setDuration( durationMMS );
        LayoutAnimationController layoutAnimationController = new LayoutAnimationController( scaleAnimation, 0.5f );
        layoutAnimationController.setOrder( LayoutAnimationController.ORDER_NORMAL );
        linearLayout.setLayoutAnimation( layoutAnimationController );
    }


}

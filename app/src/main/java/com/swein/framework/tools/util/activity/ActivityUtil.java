package com.swein.framework.tools.util.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Window;

import com.swein.framework.module.camera.custom.camera2.fragment.CameraTwoFragment;
import com.swein.shandroidtoolutils.R;

import java.util.List;

/**
 * Created by seokho on 29/12/2016.
 */

public class ActivityUtil {

    public static void startSystemIntent(Context packageContext, Intent intent) {
        packageContext.startActivity(intent);
    }

    public static void startSystemIntentWithResultByRequestCode(Activity activity, Intent intent, int requestCode) {
        activity.startActivityForResult(intent, requestCode);
    }

    public static void startNewActivityWithoutFinish(Context packageContext, Class<?> cls) {
        Intent intent = new Intent(packageContext, cls);
        packageContext.startActivity(intent);
    }

    public static void startNewActivityWithFinish(Context packageContext, Class<?> cls) {
        Intent intent = new Intent(packageContext, cls);
        packageContext.startActivity(intent);
        ((Activity) packageContext).finish();
    }

    public static Bundle getBundleDataFromPreActivity(Activity activity) {

        Bundle bundle = activity.getIntent().getExtras();

        return bundle;
    }

    public static Bundle getActivityResultBundleDataFromPreActivity(Intent intent) {

        Bundle bundle = intent.getExtras();

        return bundle;
    }

    public static void startNewActivityWithoutFinishForResult(Activity activity, Class<?> cls, int requestCode) {
        Intent intent = new Intent(activity, cls);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void startActivityWithTransitionAnimationWithoutFinish(Activity activity, Class<?> cls) {
        Intent intent = new Intent(activity, cls);
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN ) {
            if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ) {
                activity.startActivity( intent, ActivityOptions.makeSceneTransitionAnimation( activity ).toBundle());
            }
        }
    }

    public static void startActivityWithTransitionAnimationWithFinish(Activity activity, Class<?> cls) {
        Intent intent = new Intent(activity, cls);
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN ) {
            if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ) {
                activity.startActivity( intent, ActivityOptions.makeSceneTransitionAnimation( activity ).toBundle());
                activity.finish();
            }
        }
    }

    private static boolean targetActivityFiringTransitionAnimation(Activity activity) {
        try {
            activity.getWindow().requestFeature( Window.FEATURE_CONTENT_TRANSITIONS );
            return true;
        }
        catch ( Exception e ) {
            return false;
        }
    }

    @TargetApi( Build.VERSION_CODES.LOLLIPOP )
    public static void tagetActivitySetEnterTransitionAnimationExplode(Activity activity) {

        if(targetActivityFiringTransitionAnimation(activity)) {

            activity.getWindow().setEnterTransition( new Explode(  ) );

        }
    }

    @TargetApi( Build.VERSION_CODES.LOLLIPOP )
    public static void tagetActivitySetEnterTransitionAnimationSlide(Activity activity) {

        if(targetActivityFiringTransitionAnimation(activity)) {

            activity.getWindow().setEnterTransition( new Slide(  ) );

        }
    }

    @TargetApi( Build.VERSION_CODES.LOLLIPOP )
    public static void tagetActivitySetEnterTransitionAnimationFade(Activity activity) {

        if(targetActivityFiringTransitionAnimation(activity)) {

            activity.getWindow().setEnterTransition( new Fade(  ) );

        }
    }

    @TargetApi( Build.VERSION_CODES.LOLLIPOP )
    public static void tagetActivitySetExitTransitionAnimationExplode(Activity activity) {

        if(targetActivityFiringTransitionAnimation(activity)) {

            activity.getWindow().setExitTransition( new Explode(  ) );

        }
    }

    @TargetApi( Build.VERSION_CODES.LOLLIPOP )
    public static void tagetActivitySetExitTransitionAnimationSlide(Activity activity) {

        if(targetActivityFiringTransitionAnimation(activity)) {

            activity.getWindow().setExitTransition( new Slide(  ) );

        }
    }

    @TargetApi( Build.VERSION_CODES.LOLLIPOP )
    public static void tagetActivitySetExitTransitionAnimationFade(Activity activity) {

        if(targetActivityFiringTransitionAnimation(activity)) {

            activity.getWindow().setExitTransition( new Fade(  ) );

        }
    }

    public static void addFragment(FragmentActivity activity, int containerViewId, Fragment fragment, boolean withAnimation) {
        if(fragment.isAdded()) {
            return;
        }

        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        if(withAnimation) {
            transaction.setCustomAnimations(R.anim.quick_menu_in_up_to_down, R.anim.quick_menu_out_down_to_up);
        }
        transaction.add(containerViewId, fragment).commit();
    }

    public static void addFragmentWithTAG(FragmentActivity activity, int containerViewId, Fragment fragment, String tag) {
        if(fragment.isAdded()) {
            return;
        }

        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.add(containerViewId, fragment, tag).commit();
    }

    public static void replaceFragment(FragmentActivity activity, int containerViewId, Fragment fragment) {
        activity.getSupportFragmentManager().beginTransaction().replace(containerViewId, fragment).commit();
    }

    public static Fragment findFragmentByTAG(FragmentActivity activity, String fragmentTAGString) {

        return activity.getSupportFragmentManager().findFragmentByTag(fragmentTAGString);
    }

    public static void removeFragment(FragmentActivity activity, Fragment fragment, boolean withAnimation) {
        if(fragment == null || !fragment.isAdded()) {
            return;
        }
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        if(withAnimation) {
            transaction.setCustomAnimations(R.anim.quick_menu_in_up_to_down, R.anim.quick_menu_out_down_to_up);
        }
        transaction.remove(fragment).commit();
    }


    public static void replaceFragment(FragmentActivity activity, int containerViewId, Fragment fragment, boolean withAnimation) {

        if(fragment == null || !fragment.isAdded()) {
            return;
        }

        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        if(withAnimation) {
            transaction.setCustomAnimations(R.anim.quick_menu_in_left_to_right, R.anim.quick_menu_out_right_to_left);
            transaction.setCustomAnimations(R.anim.quick_menu_in_up_to_down, R.anim.quick_menu_out_down_to_up);
        }
        transaction.replace(containerViewId, fragment).commit();
    }

    public static void hideFragment(FragmentActivity activity, Fragment fragment) {
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.hide(fragment).commit();
    }

    public static void hideFragment(FragmentManager fragmentManager, Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
    }

    public static void showFragment(FragmentActivity activity, Fragment fragment) {
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.show(fragment).commit();
    }

    public static void showFragment(FragmentManager fragmentManager, Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.show(fragment).commit();
    }

    public static void detachFragment(FragmentActivity activity, Fragment fragment) {
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.detach(fragment).commit();
    }

    public static void attachFragment(FragmentActivity activity, Fragment fragment) {
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.attach(fragment).commit();
    }

    public static void addToBackStackFragment(FragmentActivity activity, int containerViewId, Fragment fragment) {
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.replace(containerViewId, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    /**
     *
     * is activity visible
     *
     */
    public static boolean isForeground(Activity activity) {
        return isForeground(activity, activity.getClass().getName());
    }


    private static boolean isForeground(Context context, String className) {
        if (context == null || TextUtils.isEmpty(className)) {
            return false;
        }

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            if (className.equals(cpn.getClassName()))
                return true;
        }
        return false;
    }
}

package com.swein.framework.tools.util.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Window;
import android.view.WindowManager;

import com.swein.data.global.activity.RequestData;
import com.swein.data.local.BundleData;

import static com.swein.data.global.key.BundleDataKey.ACTIVITY_RESULT_STRING_VALUE;

/**
 * Created by seokho on 29/12/2016.
 */

public class ActivityUtils {

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

    public static void startNewActivityWithoutFinishWithBundleData(Context packageContext, Class<?> cls, BundleData bundleData) {
        Intent intent = new Intent(packageContext, cls);
        intent.putExtras(bundleData.getBundle());
        packageContext.startActivity(intent);
    }

    public static void startNewActivityWithFinishWithBundleData(Context packageContext, Class<?> cls, BundleData bundleData) {
        Intent intent = new Intent(packageContext, cls);
        intent.putExtras(bundleData.getBundle());
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

    public static void setActivityResultStringWithFinish(Activity activity, String result, int resultCode) {
        Bundle bundle = new Bundle();
        bundle.putString(ACTIVITY_RESULT_STRING_VALUE, result);
        Intent intent = new Intent();
        intent.putExtras(bundle);
        activity.setResult(resultCode, intent);
        activity.finish();
    }

    public static String getActivityResultString(int requestCode, int resultCode, Intent intent) {

        if(RequestData.ACTIVITY_REQUEST_CODE == requestCode && RequestData.ACTIVITY_RESULT_CODE == resultCode) {
            Bundle data = ActivityUtils.getActivityResultBundleDataFromPreActivity(intent);

            String string = (String) data.get(ACTIVITY_RESULT_STRING_VALUE);

            return string;
        }

        return "";
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

    private static boolean tagetActivityFiringTransitionAnimation(Activity activity) {
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

        if(tagetActivityFiringTransitionAnimation(activity)) {

            activity.getWindow().setEnterTransition( new Explode(  ) );

        }
    }

    @TargetApi( Build.VERSION_CODES.LOLLIPOP )
    public static void tagetActivitySetEnterTransitionAnimationSlide(Activity activity) {

        if(tagetActivityFiringTransitionAnimation(activity)) {

            activity.getWindow().setEnterTransition( new Slide(  ) );

        }
    }

    @TargetApi( Build.VERSION_CODES.LOLLIPOP )
    public static void tagetActivitySetEnterTransitionAnimationFade(Activity activity) {

        if(tagetActivityFiringTransitionAnimation(activity)) {

            activity.getWindow().setEnterTransition( new Fade(  ) );

        }
    }

    @TargetApi( Build.VERSION_CODES.LOLLIPOP )
    public static void tagetActivitySetExitTransitionAnimationExplode(Activity activity) {

        if(tagetActivityFiringTransitionAnimation(activity)) {

            activity.getWindow().setExitTransition( new Explode(  ) );

        }
    }

    @TargetApi( Build.VERSION_CODES.LOLLIPOP )
    public static void tagetActivitySetExitTransitionAnimationSlide(Activity activity) {

        if(tagetActivityFiringTransitionAnimation(activity)) {

            activity.getWindow().setExitTransition( new Slide(  ) );

        }
    }

    @TargetApi( Build.VERSION_CODES.LOLLIPOP )
    public static void tagetActivitySetExitTransitionAnimationFade(Activity activity) {

        if(tagetActivityFiringTransitionAnimation(activity)) {

            activity.getWindow().setExitTransition( new Fade(  ) );

        }
    }

    public static void hideTitleBarWithFullScreen(Activity activity) {
        // hide title bar
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // hide state bar
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }





}

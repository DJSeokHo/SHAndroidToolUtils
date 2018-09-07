package com.swein.framework.module.easyscreenrecord.framework.util.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.swein.framework.module.easyscreenrecord.data.global.activity.RequestData;
import com.swein.framework.module.easyscreenrecord.data.local.BundleData;

import static com.swein.framework.module.easyscreenrecord.data.global.key.BundleDataKey.ACTIVITY_RESULT_STRING_VALUE;

/**
 * Created by seokho on 29/12/2016.
 */

public class ActivityUtils {

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
}

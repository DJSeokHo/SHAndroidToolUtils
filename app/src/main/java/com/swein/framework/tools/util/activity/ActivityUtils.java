package com.swein.framework.tools.util.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.swein.data.bundle.BundleData;
import com.swein.data.singleton.key.KeyData;
import com.swein.framework.tools.util.debug.log.ILog;

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
        ILog.iLogException(ActivityUtils.class.getName(), bundleData.getBundle().getString(KeyData.BUNDLE_STRING_KEY));
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

}

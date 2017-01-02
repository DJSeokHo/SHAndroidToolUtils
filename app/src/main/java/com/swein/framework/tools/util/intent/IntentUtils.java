package com.swein.framework.tools.util.intent;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;

/**
 * Created by seokho on 03/01/2017.
 */

public class IntentUtils {

    public void intentStartActionBackToHome(Activity activity) {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        activity.startActivity(intent);

    }

    public void intentStartActivityWithComponentNameWithoutFinish(Activity activity, Class<?> cls) {

        ComponentName componentName = new ComponentName(activity, cls);
        Intent intent = new Intent();
        intent.setComponent(componentName);
        activity.startActivity(intent);

    }

    public void intentStartActivityWithComponentNameWithFinish(Activity activity, Class<?> cls) {

        ComponentName componentName = new ComponentName(activity, cls);
        Intent intent = new Intent();
        intent.setComponent(componentName);
        activity.startActivity(intent);
        activity.finish();
    }

}

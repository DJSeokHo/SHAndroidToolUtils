package com.swein.framework.module.easyscreenrecord.framework.util.intent;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.TaskStackBuilder;

import static com.swein.framework.module.easyscreenrecord.data.global.key.NotificationKey.FROM_APP_UI_NOTIFICATION_CENTER;
import static com.swein.framework.module.easyscreenrecord.data.global.key.NotificationKey.TURN_OFF_SCREEN_RECORD_VIDEO_MODE;


/**
 * Created by seokho on 03/01/2017.
 */

public class IntentUtils {

    public static void intentStartActionBackToHome(Activity activity) {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        activity.startActivity(intent);

    }

    public static void intentStartActivityWithComponentNameWithoutFinish(Activity activity, Class<?> cls) {

        ComponentName componentName = new ComponentName(activity, cls);
        Intent intent = new Intent();
        intent.setComponent(componentName);
        activity.startActivity(intent);

    }

    public static void intentStartActivityWithComponentNameWithFinish(Activity activity, Class<?> cls) {

        ComponentName componentName = new ComponentName(activity, cls);
        Intent intent = new Intent();
        intent.setComponent(componentName);
        activity.startActivity(intent);
        activity.finish();
    }

    public static PendingIntent getPendingIntentWithClass(Context context, Class<?> cls) {
        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(context, cls);
        resultIntent.putExtra(FROM_APP_UI_NOTIFICATION_CENTER, TURN_OFF_SCREEN_RECORD_VIDEO_MODE);
        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(cls);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);

        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        1,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        return resultPendingIntent;
    }

}

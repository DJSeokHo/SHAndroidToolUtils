package com.swein.framework.helper.information.app.usage.manager;

import android.content.Context;

import com.swein.framework.helper.information.app.usage.tracker.type.AppCrashTracker;
import com.swein.framework.helper.information.app.usage.tracker.type.AppEventTracker;
import com.swein.framework.helper.information.app.usage.tracker.type.AppScreenAccessTracker;

/**
 * Created by seokho on 24/11/2016.
 */

public class AppTrackerManager {


    public final static String APP_NAME = "SHAndroidToolUtils";

    public final static String TRACKER_ID = "UA-87415315-2";

    public final static String TRACK_SCREEN = APP_NAME + " tracking screen : ";

    public final static String ACTION_BUTTON_CLICK = "button click";
    public final static String ACTION_FUNCTION_RUN = "function run";
    public final static String CATEGORY_USER_OPERATION_TRACKING = "user operation tracking";
    public final static String CATEGORY_SYSTEM_OPERATION_TRACKING = "system operation tracking";
    public final static String CRASH_REPORT = "[ " + APP_NAME + " Crash Report ]";


    private static AppTrackerManager instance = new AppTrackerManager();

    public static AppTrackerManager getInstance() {
        return instance;
    }

    public void createScreenAccessTracker(Context context) {

        AppScreenAccessTracker.getInstance().setTracker(context);

    }
    public void sendScreenAccessReport() {

        AppScreenAccessTracker.getInstance().doSendReport();

    }

    public void createEventTracker(Context context) {

        AppEventTracker.getInstance().setTracker(context);

    }
    public void sendEventReport() {

        AppEventTracker.getInstance().doSendReport();

    }

    public void createCrashTracker(Context applicationContext) {

        AppCrashTracker.getInstance().setTracker(applicationContext);

    }
    public void sendCrashReport() {

        AppCrashTracker.getInstance().doSendReport();

    }


}

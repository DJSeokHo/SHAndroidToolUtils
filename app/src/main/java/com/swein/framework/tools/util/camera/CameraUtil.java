package com.swein.framework.tools.util.camera;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by seokho on 26/01/2017.
 */

public class CameraUtil {

    public static void setCameraOpenWithDefaultSystemCamera(Intent intent, Context context) {

        PackageManager packageManager = context.getPackageManager();
        Intent finalIntent;

        //get package name and class name list from intent
        List<Intent> yourIntentsList = new ArrayList<Intent>();
        List<ResolveInfo> listCam = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo res : listCam) {

            finalIntent = new Intent(intent);
            finalIntent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            yourIntentsList.add(finalIntent);
        }

        //set intent component with default package name and class name
        intent.setComponent(new ComponentName(yourIntentsList.get(0).getComponent().getPackageName(), yourIntentsList.get(0).getComponent().getClassName()));
    }

}

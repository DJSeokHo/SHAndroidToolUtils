package com.swein.framework.module.easyscreenrecord.manager.screenrecord;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.DisplayMetrics;

import com.swein.framework.module.easyscreenrecord.EasyScreenRecordApplication;
import com.swein.framework.module.easyscreenrecord.data.local.IntentData;
import com.swein.framework.module.easyscreenrecord.data.singleton.DataCenter;
import com.swein.framework.module.easyscreenrecord.data.singleton.ScreenRecordData;
import com.swein.framework.module.easyscreenrecord.manager.handler.HandlerManager;
import com.swein.framework.module.easyscreenrecord.service.ScreenRecordModeService;
import com.swein.framework.module.easyscreenrecord.viewholder.ScreenRecordViewHolder;
import com.swein.framework.tools.util.intent.IntentUtil;

import static android.app.Activity.RESULT_OK;
import static com.swein.framework.module.easyscreenrecord.data.global.applicaiton.ESRContent.SCREEN_RECORD_FILE_FULL_PATH;
import static com.swein.framework.module.easyscreenrecord.manager.permission.PermissionManager.RECORD_REQUEST_CODE;

/**
 * Created by seokho on 04/02/2017.
 */

public class ScreenRecordManager {

    private static ScreenRecordViewHolder screenRecordViewHolder;

    public static void initScreenRecordView(final Context context) {
        screenRecordViewHolder = new ScreenRecordViewHolder(context);
        screenRecordViewHolder.initView();
        screenRecordViewHolder.setViewAction();

        //make UI change with message
        HandlerManager.createHandlerMethodWithMessage(new Runnable() {
            @Override
            public void run() {

                if(DataCenter.getInstance().getIsRecordModeOn()) {
                    screenRecordViewHolder.setScreenRecordEndView(context);
                }
                else {
                    screenRecordViewHolder.setScreenRecordStartView(context);

                    //reset file full path after send scan broadcast
                    context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + SCREEN_RECORD_FILE_FULL_PATH)));
                    SCREEN_RECORD_FILE_FULL_PATH = "";
                }

            }
        });
    }

    public static void prepareScreenRecord(Context context) {

        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        DataCenter.getInstance().setDisplayMetrics(metrics);

        ScreenRecordData.getInstance().setMediaProjection(null);

    }

    public static void toggleScreenRecordVideoModeState(Context context) {
        if (DataCenter.getInstance().getIsRecordModeOn()) {
            requestStopScreenRecord(context);
        } else {
            requestStartScreenRecord(context);
        }
    }


    public static void requestStartScreenRecord(Context context) {
        IntentData intentData = new IntentData();
        intentData.setScreenRecordIntent(ScreenRecordData.getInstance().getMediaProjectionManager().createScreenCaptureIntent());
        ((Activity)context).startActivityForResult(intentData.getScreenRecordIntent(), RECORD_REQUEST_CODE);
    }

    public static void requestStopScreenRecord(Context context) {
        if(ScreenRecordModeService.isRunning()) {
            context.stopService(EasyScreenRecordApplication.screenRecordServiceIntent);
        }

        //send change UI message
        HandlerManager.sendScreenRecordEndMessage();
    }

    public static void prepareScreenRecordService(Context context) {
        EasyScreenRecordApplication.screenRecordServiceIntent = new Intent(context, ScreenRecordModeService.class);
    }

    public static void startScreenRecordService(Context context) {
        context.startService(EasyScreenRecordApplication.screenRecordServiceIntent);
    }

    public static void onRequestScreenRecordResult(Context context, int requestCode, int resultCode, Intent data) {
        if (requestCode == RECORD_REQUEST_CODE && resultCode == RESULT_OK) {

            prepareScreenRecordService(context);
            startScreenRecordService(context);

            ScreenRecordData.getInstance().setMediaProjection(ScreenRecordData.getInstance().getMediaProjectionManager().getMediaProjection(resultCode, data));

            IntentUtil.intentStartActionBackToHome((Activity)context);

            //send change UI message
            HandlerManager.sendScreenRecordStartMessage();
        }
    }

}

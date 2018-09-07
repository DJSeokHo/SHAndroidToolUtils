package com.swein.framework.module.easyscreenrecord.viewholder;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.Button;

import com.swein.framework.module.easyscreenrecord.data.singleton.DataCenter;
import com.swein.framework.module.easyscreenrecord.manager.permission.PermissionManager;
import com.swein.framework.module.easyscreenrecord.manager.screenrecord.ScreenRecordManager;
import com.swein.shandroidtoolutils.R;


/**
 * Created by seokho on 04/02/2017.
 */

public class ScreenRecordViewHolder implements View.OnClickListener {

    private Button buttonRecord;

    private Context context;

    public ScreenRecordViewHolder(Context context) {
        this.context = context;
    }

    public void initView() {

        buttonRecord = (Button) ((Activity)context).findViewById(R.id.buttonRecord);
        buttonRecord.setText("Start Record");
    }

    public void setViewAction() {

        buttonRecord.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.buttonRecord:

                if (DataCenter.getInstance().getIsRecordModeOn()) {
                    //should turn off record mode
                    ScreenRecordManager.toggleScreenRecordVideoModeState(context);
                } else {
                    //should turn on record mode

                    //check version and permission
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                        PermissionManager.goAppsWithUsageAccessPermissionOperationPage(context, R.id.buttonRecord, new Runnable() {
                            @Override
                            public void run() {

                                //should turn off record mode
                                ScreenRecordManager.toggleScreenRecordVideoModeState(context);

                            }
                        });

                    }
                }

                break;
        }

    }

    public void setScreenRecordStartView(Context context) {

        buttonRecord.setText("Start Record");

    }

    public void setScreenRecordEndView(Context context) {

        buttonRecord.setText("Stop Record");

    }
}

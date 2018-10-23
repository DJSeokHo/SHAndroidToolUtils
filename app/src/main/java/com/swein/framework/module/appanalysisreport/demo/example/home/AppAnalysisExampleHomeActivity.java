package com.swein.framework.module.appanalysisreport.demo.example.home;

import android.app.Activity;
import android.os.Bundle;

import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.shandroidtoolutils.R;

import java.util.List;

public class AppAnalysisExampleHomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_analysis_example_home);

//        AppAnalysisData appAnalysisData = new OperationData.Builder()
//                .setUuid(UUIDUtil.getUUIDString())
//                .setClassFileName(this.getClass().getName())
//                .setViewUINameOrMethodName("onCreate")
//                .setDateTime(DateUtil.getCurrentDateTimeString())
//                .setOperationType(ReportProperty.OPERATION_TYPE.NONE)
//                .setEventGroup(ReportProperty.EVENT_GROUP_CHANGE_SCREEN)
//                .build();
//        ReportTracker.getInstance().saveAppAnalysisIntoDB(this, appAnalysisData);

//        RMLog.init(Context.this);
//        RMLog.setAdminEmail("email");
//
//        RMLog.log("EventGroup","Operation","OtherValue");
//        RMLog.logException("EventGroup","Operation","OtherValue");

        List list = null;
        ILog.iLogDebug(">>>", list.get(5));
    }
}

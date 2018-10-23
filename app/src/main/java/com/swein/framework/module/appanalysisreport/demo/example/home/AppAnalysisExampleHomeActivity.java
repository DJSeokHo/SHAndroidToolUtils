package com.swein.framework.module.appanalysisreport.demo.example.home;

import android.app.Activity;
import android.os.Bundle;

import com.swein.framework.module.appanalysisreport.data.parser.ReportParser;
import com.swein.framework.module.appanalysisreport.reportproperty.ReportProperty;
import com.swein.framework.module.appanalysisreport.reporttracker.ReportTracker;
import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.shandroidtoolutils.R;

import java.util.List;

public class AppAnalysisExampleHomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_analysis_example_home);

        ReportTracker.getInstance().trackOperation(
                ReportParser.getLocationFromThrowable(new Throwable()),
                ReportProperty.EVENT_GROUP_CHANGE_SCREEN,
                ReportProperty.OPERATION_TYPE.NONE,
                ""
        );


        try {
            Thread.sleep(3000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        List list = null;
        ILog.iLogDebug(">>>", list.get(5));
    }

}

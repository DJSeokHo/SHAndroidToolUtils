package com.swein.shandroidtoolutils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.swein.framework.helper.information.app.usage.manager.AppTrackerManager;
import com.swein.framework.helper.information.app.usage.tracker.report.TrackingReport;
import com.swein.framework.tools.util.ToastUtils;


public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ToastUtils.showCustomLongToastNormal(this, "text");
//        ToastUtils.showCustomLongToastWithImageResourceId(this, "image and text", R.mipmap.ic_launcher);

        findViewById(R.id.btnMakeCrash).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //make crash
                String s = null;
                Log.d("seokho", String.valueOf(s.equals("any string")));
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        TrackingReport.getInstance().setTrackingReport(AppTrackerManager.TRACK_SCREEN + TAG);
        AppTrackerManager.getInstance().createScreenAccessTracker(this);
        AppTrackerManager.getInstance().sendScreenAccessReport();
    }
}

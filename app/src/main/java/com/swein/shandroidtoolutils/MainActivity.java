package com.swein.shandroidtoolutils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.swein.framework.module.aspect.tracker.analytics.report.annotation.GAExceptionTrace;
import com.swein.framework.tools.util.activity.ActivityUtils;
import com.swein.tabhostandtablayout.activity.TabHostActivity;

import java.util.List;

import static com.swein.framework.module.appinstallinfo.install.checker.AppInstallChecker.checkAppInstallInfoJSONObject;


public class MainActivity extends AppCompatActivity {

    @GAExceptionTrace
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        checkAppInstallInfoJSONObject(this);

//        ActivityUtils.startNewActivityWithoutFinish( this, DelegateExampleActivity.class );
//        ActivityUtils.startNewActivityWithoutFinish( this, VideoViewActivity.class );
//        ActivityUtils.startNewActivityWithoutFinish( this, RecyclerViewListActivity.class );
//        ActivityUtils.startNewActivityWithoutFinish( this, RecyclerViewRandomActivity.class );
        ActivityUtils.startNewActivityWithoutFinish( this, TabHostActivity.class );

        testViewReport();
        testEventReport();


//        testExceptionReport();



        try {
            List list = null;
            list.get( 5 );
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }


    }

    private void testViewReport() {

    }

    private void testEventReport() {

    }


//    private void testExceptionReport(){
//
//        try {
//            List list = null;
//            list.get( 5 );
//        }
//        catch ( Exception e ) {
//
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}

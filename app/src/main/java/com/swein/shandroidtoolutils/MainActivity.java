package com.swein.shandroidtoolutils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.swein.framework.tools.util.activity.ActivityUtils;
import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.tabhostandtablayout.activity.TabHostActivity;

import java.util.List;

import static com.swein.framework.module.appinstallinfo.install.checker.AppInstallChecker.checkAppInstallInfoJSONObject;


public class MainActivity extends AppCompatActivity {

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

        try {
            String[] strings = new String[] {"1", "2"};
            ILog.iLogDebug( this, strings[5] );
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }

        try {
            List list = null;
            list.get( 5 );
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }

        try {
            int one = 1;
            int zero = 0;
            int result = one / zero;
            ILog.iLogDebug( this, result );
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}

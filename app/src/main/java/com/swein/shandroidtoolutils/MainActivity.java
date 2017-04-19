package com.swein.shandroidtoolutils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.Button;
import android.widget.ImageView;

import com.swein.activity.animation.StartActivity;
import com.swein.framework.tools.util.activity.ActivityUtils;
import com.swein.framework.tools.util.date.DateUtil;
import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.framework.tools.util.thread.ThreadUtils;
import com.swein.framework.tools.util.views.ViewUtils;

import static com.swein.framework.module.appinstallinfo.install.checker.AppInstallChecker.checkAppInstallInfoJSONObject;


public class MainActivity extends AppCompatActivity {

    private ImageView imageViewMain1;
    private ImageView imageViewMain2;
    private Button    buttonMain;

    private ViewOutlineProvider viewOutlineProvider1;
    private ViewOutlineProvider viewOutlineProvider2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ThreadUtils.startThread( new Runnable() {
            @Override
            public void run() {
                checkAppInstallInfoJSONObject(getApplicationContext());
            }
        } );

//        ActivityUtils.startNewActivityWithoutFinish( this, DelegateExampleActivity.class );
//        ActivityUtils.startNewActivityWithoutFinish( this, VideoViewActivity.class );
//        ActivityUtils.startNewActivityWithoutFinish( this, RecyclerViewListActivity.class );
//        ActivityUtils.startNewActivityWithoutFinish( this, RecyclerViewRandomActivity.class );
//        ActivityUtils.startNewActivityWithoutFinish( this, TabHostActivity.class );

        imageViewMain1 = (ImageView) findViewById( R.id.imageViewMain1 );
        imageViewMain2 = (ImageView) findViewById( R.id.imageViewMain2 );
        buttonMain = (Button) findViewById( R.id.buttonMain );

        ILog.iLogDebug( this.getClass().getSimpleName(), DateUtil.getCurrentDateFromFastDateFormat( "yyyy-MM-dd HH:mm:ss:SSS") );
        ILog.iLogDebug( this.getClass().getSimpleName(), DateUtil.getCurrentDateFromFastDateFormat( "yyyyMMddHHmmssSSS") );


        ViewUtils.setViewDepth( imageViewMain1, 300 );
        ViewUtils.setViewDepth( imageViewMain2, 100 );

        ViewUtils.setViewRoundRect( imageViewMain1, true );
        ViewUtils.setViewCircle( imageViewMain2, true, 200, 200 );

        buttonMain.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {

                ActivityUtils.startNewActivityWithoutFinish( MainActivity.this, StartActivity.class );

            }
        } );

        //        try {
//            String[] strings = new String[] {"1", "2"};
//            ILog.iLogDebug( this, strings[5] );
//        }
//        catch ( Exception e ) {
//            e.printStackTrace();
//        }
//
//        try {
//            List list = null;
//            list.get( 5 );
//        }
//        catch ( Exception e ) {
//            e.printStackTrace();
//        }
//
//        try {
//            int one = 1;
//            int zero = 0;
//            int result = one / zero;
//            ILog.iLogDebug( this, result );
//        }
//        catch ( Exception e ) {
//            e.printStackTrace();
//        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}

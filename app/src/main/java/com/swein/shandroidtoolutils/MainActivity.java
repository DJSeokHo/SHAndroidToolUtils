package com.swein.shandroidtoolutils;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.Button;
import android.widget.ImageView;

import com.swein.activity.animation.StartActivity;
import com.swein.camera.custom.activity.CustomCameraActivity;
import com.swein.framework.module.dragmenu.view.activity.DMMainActivity;
import com.swein.framework.module.googleanalytics.aop.monitor.processtimer.TimerTrace;
import com.swein.framework.tools.util.activity.ActivityUtils;
import com.swein.framework.tools.util.date.DateUtil;
import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.framework.tools.util.device.DeviceInfoUtils;
import com.swein.framework.tools.util.thread.ThreadUtils;
import com.swein.framework.tools.util.views.ViewUtils;
import com.swein.pattern.strategy.otaku.DirtyBoy;
import com.swein.pattern.strategy.otaku.DrunkBoy;
import com.swein.pattern.strategy.otaku.live.drink.impl.OtakuDrink;
import com.swein.pattern.strategy.otaku.otaku.OTAKU;

import static com.swein.framework.module.appinstallinfo.install.checker.AppInstallChecker.checkAppInstallInfoJSONObject;


public class MainActivity extends AppCompatActivity {

    private ImageView imageViewMain1;
    private ImageView imageViewMain2;
    private Button buttonMain;
    private Button buttonCamera;

    private ViewOutlineProvider viewOutlineProvider1;
    private ViewOutlineProvider viewOutlineProvider2;

    @TimerTrace
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // full screen
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT )
        {
            getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                    | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY );
        }

        DeviceInfoUtils.initDeviceScreenDisplayMetricsPixels(this);

        ThreadUtils.startThread(new Runnable() {
            @Override
            public void run() {
                checkAppInstallInfoJSONObject(getApplicationContext());
            }
        });

//                ActivityUtils.startNewActivityWithoutFinish( this, DelegateExampleActivity.class );
        //        ActivityUtils.startNewActivityWithoutFinish( this, VideoViewActivity.class );
//                ActivityUtils.startNewActivityWithoutFinish( this, RecyclerViewListActivity.class );
//                ActivityUtils.startNewActivityWithoutFinish( this, RecyclerViewRandomActivity.class );
//                ActivityUtils.startNewActivityWithoutFinish( this, EspressoTestExampleActivity.class );
//                ActivityUtils.startNewActivityWithoutFinish( this, TabHostActivity.class );
//        ActivityUtils.startNewActivityWithoutFinish(this, ToolbarActivity.class);
//        ActivityUtils.startNewActivityWithoutFinish(this, HandlerExampleActivity.class);
//        ActivityUtils.startNewActivityWithoutFinish(this, BlockGameTZFEActivity.class);
//        ActivityUtils.startNewActivityWithoutFinish(this, DevicePolicyManagerActivity.class);
//        ActivityUtils.startNewActivityWithoutFinish(this, NaverTranslationActivity.class);
//        ActivityUtils.startNewActivityWithoutFinish(this, UserLoginActivity.class);
        ActivityUtils.startNewActivityWithoutFinish(this, DMMainActivity.class);
//        ActivityUtils.startNewActivityWithoutFinish(this, RxJava2Activity.class);


        imageViewMain1 = (ImageView) findViewById(R.id.imageViewMain1);
        imageViewMain2 = (ImageView) findViewById(R.id.imageViewMain2);
        buttonMain = (Button) findViewById(R.id.buttonMain);
        buttonCamera = (Button) findViewById(R.id.buttonCamera);

        ILog.iLogDebug(this.getClass().getSimpleName(), DateUtil.getCurrentDateFromFastDateFormat("yyyy-MM-dd HH:mm:ss:SSS"));
        ILog.iLogDebug(this.getClass().getSimpleName(), DateUtil.getCurrentDateFromFastDateFormat("yyyyMMddHHmmssSSS"));

        ViewUtils.setViewDepth(imageViewMain1, 300);
        ViewUtils.setViewDepth(imageViewMain2, 100);

        ViewUtils.setViewRoundRect(imageViewMain1, true);
        ViewUtils.setViewCircle(imageViewMain2, true, 200, 200);

        buttonMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ActivityUtils.startNewActivityWithoutFinish(MainActivity.this, StartActivity.class);

            }
        });

        buttonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                ActivityUtils.startNewActivityWithoutFinish(DMMainActivity.this, SystemCameraActivity.class);
                ActivityUtils.startNewActivityWithoutFinish(MainActivity.this, CustomCameraActivity.class);
//                ActivityUtils.startNewActivityWithoutFinish(DMMainActivity.this, TouchActivity.class);
//                ActivityUtils.startNewActivityWithoutFinish(DMMainActivity.this, AdvanceCameraActivity.class);

            }
        });


        OTAKU dirtyBoy = new DirtyBoy("油腻宅男");

        dirtyBoy.work();
        dirtyBoy.goShopping();
        dirtyBoy.play();

        DrunkBoy drunkBoy = new DrunkBoy("惜酒宅男");

        drunkBoy.work();
        drunkBoy.goShopping();
        drunkBoy.wantDrunk(new OtakuDrink());
        drunkBoy.play();

//        Calculator calculator = new Calculator();
//
//        calculator.setOperate("+");
//        ILog.iLogDebug(DMMainActivity.class.getSimpleName(), calculator.calculateResultWithTowNumber("1", "2"));
//
//        calculator.setOperate("-");
//        ILog.iLogDebug(DMMainActivity.class.getSimpleName(), calculator.calculateResultWithTowNumber("1", "2"));
//
//        calculator.setOperate("*");
//        ILog.iLogDebug(DMMainActivity.class.getSimpleName(), calculator.calculateResultWithTowNumber("1", "2"));
//
//        calculator.setOperate("/");
//        ILog.iLogDebug(DMMainActivity.class.getSimpleName(), calculator.calculateResultWithTowNumber("1", "2"));
//
//        ILog.iLogDebug( DMMainActivity.class.getSimpleName(), GpsUtils.isGPSTurnOn( this ) );
//        GpsUtils.turnOnGPS( this );
//
//        ILog.iLogDebug( DMMainActivity.class.getSimpleName(), NetWorkUtils.isNetworkConnected( this ));
//        ILog.iLogDebug( DMMainActivity.class.getSimpleName(), NetWorkUtils.isWifiConnected( this ));
//        ILog.iLogDebug( DMMainActivity.class.getSimpleName(), NetWorkUtils.isMobileConnected( this ));
//        ILog.iLogDebug( DMMainActivity.class.getSimpleName(), NetWorkUtils.getConnectedType( this ));
//        ILog.iLogDebug( DMMainActivity.class.getSimpleName(), NetWorkUtils.getNetWorkType( this ));

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

package com.swein.shandroidtoolutils;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.swein.framework.module.gcmpush.activity.GoogleCloudMessageActivity;
import com.swein.framework.module.googleanalytics.aop.monitor.processtimer.TimerTrace;
import com.swein.framework.template.mode.mvc.view.activity.SHDemoMVCActivity;
import com.swein.framework.tools.location.SHLocation;
import com.swein.framework.tools.picasso.SHPicasso;
import com.swein.framework.tools.util.activity.ActivityUtil;
import com.swein.framework.tools.util.animation.AnimationUtil;
import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.framework.tools.util.device.DeviceInfoUtil;
import com.swein.framework.tools.util.serializalbe.SerializableUtil;
import com.swein.framework.tools.util.thread.ThreadUtil;
import com.swein.framework.tools.util.toast.ToastUtil;
import com.swein.framework.tools.volley.SHVolley;

import java.io.Serializable;

import static com.swein.framework.module.appinstallinfo.install.checker.AppInstallChecker.checkAppInstallInfoJSONObject;


public class MainActivity extends Activity {

    private ImageView imageViewMain1;
    private ImageView imageViewMain2;

    private ViewOutlineProvider viewOutlineProvider1;
    private ViewOutlineProvider viewOutlineProvider2;

    private final static int REQUEST_READ_PHONE_STATE = 998;

    boolean closeFlag = false;


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

        DeviceInfoUtil.initDeviceScreenDisplayMetricsPixels(this);

        ThreadUtil.startThread(new Runnable() {
            @Override
            public void run() {
                checkAppInstallInfoJSONObject(getApplicationContext());
            }
        });





//                ActivityUtil.startNewActivityWithoutFinish( this, DelegateExampleActivity.class );
        //        ActivityUtil.startNewActivityWithoutFinish( this, VideoViewActivity.class );
//                ActivityUtil.startNewActivityWithoutFinish( this, RecyclerViewListActivity.class );
//                ActivityUtil.startNewActivityWithoutFinish( this, EspressoTestExampleActivity.class );
//                ActivityUtil.startNewActivityWithoutFinish( this, TabHostActivity.class );
//        ActivityUtil.startNewActivityWithoutFinish(this, ToolbarActivity.class);
//        ActivityUtil.startNewActivityWithoutFinish(this, HandlerExampleActivity.class);
//        ActivityUtil.startNewActivityWithoutFinish(this, BlockGameTZFEActivity.class);
//        ActivityUtil.startNewActivityWithoutFinish(this, DevicePolicyManagerActivity.class);
//        ActivityUtil.startNewActivityWithoutFinish(this, NaverTranslationActivity.class);
//        ActivityUtil.startNewActivityWithoutFinish(this, UserLoginActivity.class);
//        ActivityUtil.startNewActivityWithoutFinish(this, DMMainActivity.class); DragMenu
//        ActivityUtil.startNewActivityWithoutFinish(this, RxJava2Activity.class);

//        ActivityUtil.startNewActivityWithoutFinish(this, JustActivity.class);
//        ActivityUtil.startNewActivityWithoutFinish(this, SHRecyclerViewActivity.class);


        int permissionCheck = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE);


        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
        }
        else {
            //TODO
        }



        TestObject testObject = new TestObject();
        testObject.name = "haha";

        try {
            byte[] bytes = SerializableUtil.serializeObjectToBytes(testObject);

            Object object = SerializableUtil.antiSerializeBytesToObject(bytes);

            TestObject t = (TestObject) object;

            ILog.iLogDebug("haha", t.name);

        }
        catch (Exception e) {
            e.printStackTrace();
        }

//        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, QRConstants.CAMERA_PERMISSION);
//        }
//        else {
//            ActivityUtil.startNewActivityWithoutFinish(this, SHQRCodeScannerActivity.class);
//        }

        ActivityUtil.startNewActivityWithoutFinish(this, GoogleCloudMessageActivity.class);

//        ActivityUtil.startNewActivityWithoutFinish(this, DatePickerActivity.class);

        SHVolley shVolley = new SHVolley(this);
        shVolley.requestUrlGet("https://m.baidu.com/", new SHVolley.SHVolleyDelegate() {
            @Override
            public void onResponse(String response) {

            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        imageViewMain1 = (ImageView) findViewById(R.id.imageViewMain1);


        imageViewMain2 = (ImageView) findViewById(R.id.imageViewMain2);


        SHPicasso.getInstance().loadImage(this, "http://img3.imgtn.bdimg.com/it/u=1332377433,2524957434&fm=27&gp=0.jpg", imageViewMain2, false, 0);

        imageViewMain2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                ObjectAnimator.ofFloat(
//                        imageViewMain2,
//                        "translationX",
//                        300
//                ).setDuration(300).start();

                AnimationUtil.setViewToPositionY(imageViewMain2, 0, 600, null);

                /**
                 * translationX, translationY
                 * rotation, rotationX, rotationY
                 * scaleX, scaleY
                 * pivotX, pivotY
                 * x, y
                 * alpha
                 */
            }
        });


        new SHLocation().getLocation(this, new SHLocation.SHLocationDelegate() {
            @Override
            public void onLocation(double longitude, double latitude, long time) {

            }
        });

        ActivityUtil.startNewActivityWithoutFinish(this, SHDemoMVCActivity.class);

//        ActivityUtil.startNewActivityWithoutFinish(this, SHSlidingTabViewPagerContainerActivity.class);
//        ActivityUtil.startNewActivityWithoutFinish(this, SHCardViewActivity.class);
//        ActivityUtil.startNewActivityWithoutFinish(this, SHTabHostActivity.class);
//        ActivityUtil.startNewActivityWithoutFinish(this, SHTabSlidingHostActivity.class);
//        ActivityUtil.startNewActivityWithoutFinish(this, SHViewPagerFragmentActivity.class);

//        SoundEffect.getInstance().initSoundEffect(this);
//        SoundEffect.getInstance().addResources(1, R.raw.sound_click);
//
//        imageViewMain1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SoundEffect.getInstance().play(MainActivity.this, 1);
//            }
//        });


//        imageViewMain1 = (ImageView) findViewById(R.id.imageViewMain1);
//        imageViewMain2 = (ImageView) findViewById(R.id.imageViewMain2);
//        buttonMain = (Button) findViewById(R.id.buttonMain);
//        buttonCamera = (Button) findViewById(R.id.buttonCamera);
//
//        ILog.iLogDebug(this.getClass().getSimpleName(), DateUtil.getCurrentDateFromFastDateFormat("yyyy-MM-dd HH:mm:ss:SSS"));
//        ILog.iLogDebug(this.getClass().getSimpleName(), DateUtil.getCurrentDateFromFastDateFormat("yyyyMMddHHmmssSSS"));
//
//        ViewUtil.setViewDepth(imageViewMain1, 300);
//        ViewUtil.setViewDepth(imageViewMain2, 100);
//
//        ViewUtil.setViewRoundRect(imageViewMain1, true);
//        ViewUtil.setViewCircle(imageViewMain2, true, 200, 200);
//
//        buttonMain.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                ActivityUtil.startNewActivityWithoutFinish(MainActivity.this, StartActivity.class);
//
//            }
//        });
//
//        buttonCamera.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
////                ActivityUtil.startNewActivityWithoutFinish(DMMainActivity.this, SystemCameraActivity.class);
//                ActivityUtil.startNewActivityWithoutFinish(MainActivity.this, CustomCameraActivity.class);
////                ActivityUtil.startNewActivityWithoutFinish(DMMainActivity.this, TouchActivity.class);
////                ActivityUtil.startNewActivityWithoutFinish(DMMainActivity.this, AdvanceCameraActivity.class);
//
//            }
//        });
//
//
//        OTAKU dirtyBoy = new DirtyBoy("油腻宅男");
//
//        dirtyBoy.work();
//        dirtyBoy.goShopping();
//        dirtyBoy.play();
//
//        DrunkBoy drunkBoy = new DrunkBoy("惜酒宅男");
//
//        drunkBoy.work();
//        drunkBoy.goShopping();
//        drunkBoy.wantDrunk(new OtakuDrink());
//        drunkBoy.play();


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
//        ILog.iLogDebug( DMMainActivity.class.getSimpleName(), GpsUtil.isGPSTurnOn( this ) );
//        GpsUtil.turnOnGPS( this );
//
//        ILog.iLogDebug( DMMainActivity.class.getSimpleName(), NetWorkUtil.isNetworkConnected( this ));
//        ILog.iLogDebug( DMMainActivity.class.getSimpleName(), NetWorkUtil.isWifiConnected( this ));
//        ILog.iLogDebug( DMMainActivity.class.getSimpleName(), NetWorkUtil.isMobileConnected( this ));
//        ILog.iLogDebug( DMMainActivity.class.getSimpleName(), NetWorkUtil.getConnectedType( this ));
//        ILog.iLogDebug( DMMainActivity.class.getSimpleName(), NetWorkUtil.getNetWorkType( this ));

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
    protected void onSaveInstanceState(Bundle outState) {



        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {



        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_READ_PHONE_STATE:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    //TODO
                }
                break;

            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    protected void onResume() {

        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (event.getKeyCode() != KeyEvent.KEYCODE_BACK) {
            return super.onKeyDown( keyCode, event );
        }

        if( closeFlag == false ){
            ToastUtil.showCustomShortToastNormal(this, "뒤로 버튼을 한번 더 누르면 종료됩니다.");
            closeFlag = true;

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    closeFlag = false;
                }
            }, 3000);
        }
        else{
            finish();
        }

        return false;

    }

}

class TestObject implements Serializable {

    public final static long serialID = 1000000001L;

    public String name;

}
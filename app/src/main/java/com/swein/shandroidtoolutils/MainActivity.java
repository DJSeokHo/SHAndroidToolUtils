package com.swein.shandroidtoolutils;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.volley.VolleyError;
import com.swein.framework.module.camera.custom.camera1.preview.surfaceview.FakeCameraOnePreview;
import com.swein.framework.module.googleanalytics.aop.monitor.processtimer.TimerTrace;
import com.swein.framework.module.location.SHLocation;
import com.swein.framework.module.location.geo.SHGeoCoder;
import com.swein.framework.module.locationapi.LocationAPI;
import com.swein.framework.module.permissions.Permissions;
import com.swein.framework.tools.util.animation.AnimationUtil;
import com.swein.framework.tools.util.appinfo.AppInfoUtil;
import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.framework.tools.util.device.DeviceInfoUtil;
import com.swein.framework.tools.util.picasso.SHPicasso;
import com.swein.framework.tools.util.serializalbe.SerializableUtil;
import com.swein.framework.tools.util.shortcut.ShortCutUtil;
import com.swein.framework.tools.util.thread.ThreadUtil;
import com.swein.framework.tools.util.toast.ToastUtil;
import com.swein.framework.tools.util.volley.SHVolley;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.List;


public class MainActivity extends Activity {

    private final static String TAG = "MainActivity";

    private ImageView imageViewMain1;
    private ImageView imageViewMain2;

    private Button buttonCamera;
    private Button buttonCreateShortCut;
    private Button buttonCrash;

    private Button buttonLocation;

    private RelativeLayout relativeLayoutFakeCameraOnePreview;

    private ViewOutlineProvider viewOutlineProvider1;
    private ViewOutlineProvider viewOutlineProvider2;


    private final static int REQUEST_READ_PHONE_STATE = 998;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE
    };


    boolean closeFlag = false;

    @TimerTrace
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            String content = bundle.getString("shortcutContentKey");
            ILog.iLogDebug("MainActivity", content);
        }

//        // full screen
//        WindowUtil.fullScreen(this);

        DeviceInfoUtil.initDeviceScreenDisplayMetricsPixels(this);

//        ThreadUtil.startThread(new Runnable() {
//
//            @Override
//            public void run() {
//                checkAppInstallInfoJSONObject(getApplicationContext());
//            }
//        });

        relativeLayoutFakeCameraOnePreview = findViewById(R.id.rl_fake);
        relativeLayoutFakeCameraOnePreview.addView(new FakeCameraOnePreview(this));

        buttonLocation = findViewById(R.id.buttonLocation);
        buttonLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Permissions.getInstance().requestPermission(MainActivity.this);
                SHLocation.getInstance().init(MainActivity.this);

                SHLocation.getInstance().requestLocation(new SHLocation.SHLocationDelegate() {
                    @Override
                    public void onLocation(double longitude, double latitude, long time) {
                        try {

                            List<Address> addressList = new SHGeoCoder(MainActivity.this).getFromLocation(latitude, longitude, 2);
                            for(Address address : addressList) {
                                ILog.iLogDebug(TAG, address.toString());
                            }

                            SHLocation.getInstance().clear();
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }, false);


                LocationAPI.getInstance().requestLocation(MainActivity.this, new LocationAPI.LocationAPIDelegate() {
                    @Override
                    public void onLocation(Location location) {
                        try {
                            List<Address> addressList = new SHGeoCoder(MainActivity.this).getFromLocation(location.getLatitude(), location.getLongitude(), 2);
                            for(Address address : addressList) {
                                ILog.iLogDebug(TAG, address.toString());
                            }
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        buttonCrash = findViewById(R.id.buttonCrash);
        buttonCrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List l = null;
                l.get(5).toString();
            }
        });

        buttonCamera = findViewById(R.id.btn_camera);
        buttonCamera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                ActivityUtil.startNewActivityWithoutFinish(MainActivity.this, CameraOneActivity.class);

            }
        });

        buttonCreateShortCut = findViewById(R.id.buttonCreateShortCut);
        buttonCreateShortCut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                    you can get bundle at onCreate of target class

                    Bundle bundle = getIntent().getExtras();
                    if(bundle != null) {
                        String content = bundle.getString("shortcutContentKey");
                        ILog.iLogDebug("MainActivity", content);
                    }
                */
                Bundle bundle = new Bundle();
                bundle.putString("shortcutContentKey", "from short cut");

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                    ShortCutUtil.addShortcut(MainActivity.this, MainActivity.class, bundle, "shortcut id", "haha", R.drawable.circle);
                }
                else {

                    ShortCutUtil.addShortcut(MainActivity.this, MainActivity.class, bundle, "haha", R.drawable.btn_close);
                }
            }
        });

        boolean push = AppInfoUtil.isPushNotificationEnable(this);
        if(!push) {
            AppInfoUtil.moveToAppPushSetting(this);
        }
        ILog.iLogDebug(TAG, push);


//                ActivityUtil.startNewActivityWithoutFinish(this, DelegateExampleActivity.class);
        //        ActivityUtil.startNewActivityWithoutFinish(this, VideoViewActivity.class);
//                ActivityUtil.startNewActivityWithoutFinish(this, RecyclerViewListActivity.class);
//                ActivityUtil.startNewActivityWithoutFinish(this, TabHostActivity.class);
//        ActivityUtil.startNewActivityWithoutFinish(this, ToolbarActivity.class);
//        ActivityUtil.startNewActivityWithoutFinish(this, HandlerExampleActivity.class);
//        ActivityUtil.startNewActivityWithoutFinish(this, BlockGameTZFEActivity.class);

//        ActivityUtil.startNewActivityWithoutFinish(this, NaverTranslationActivity.class);
//        ActivityUtil.startNewActivityWithoutFinish(this, UserLoginActivity.class);
//        ActivityUtil.startNewActivityWithoutFinish(this, DMMainActivity.class); DragMenu
//        ActivityUtil.startNewActivityWithoutFinish(this, RxJava2Activity.class);

//        ActivityUtil.startNewActivityWithoutFinish(this, JustActivity.class);
//        ActivityUtil.startNewActivityWithoutFinish(this, SHRecyclerViewActivity.class);
//        ActivityUtil.startNewActivityWithoutFinish(this, SHDoubleScrollActivity.class);
//        ActivityUtil.startNewActivityWithoutFinish(this, ScreenShotActivity.class);
//        ActivityUtil.startNewActivityWithoutFinish(this, EasyScreenRecordingActivity.class);
//        ActivityUtil.startNewActivityWithoutFinish(this, ScreenRecordingActivity.class);
//        ActivityUtil.startNewActivityWithoutFinish(this, ExceptionReportDemoActivity.class);


        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_READ_PHONE_STATE);
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
////            ActivityUtil.startNewActivityWithoutFinish(this, QRCodeScannerLiteActivity.class);
//        }

//        ActivityUtil.startNewActivityWithoutFinish(this, PhoneCallRecorderDemoActivity.class);


//        ActivityUtil.startNewActivityWithoutFinish(this, GoogleCloudMessageActivity.class);
//        ActivityUtil.startNewActivityWithoutFinish(this, FirebaseCloudMessage.class);
//        ActivityUtil.startNewActivityWithoutFinish(this, NoticeNotificationActivity.class);
//        ActivityUtil.startNewActivityWithoutFinish(this, VideoSplashActivity.class);

//        ActivityUtil.startNewActivityWithoutFinish(this, DatePickerActivity.class);

//        ActivityUtil.startNewActivityWithoutFinish(this, AppAnalysisReportDemoActivity.class);
//        ActivityUtil.startNewActivityWithoutFinish(this, BottomNavigateDemoActivity.class);
//        ActivityUtil.startNewActivityWithoutFinish(this, AppAnalysisExampleLoginActivity.class);
//        ActivityUtil.startNewActivityWithoutFinish(this, TransitionViewTemplateActivity.class);
//        ActivityUtil.startNewActivityWithoutFinish(this, SHSlidingTabViewPagerContainerActivity.class);
//        ActivityUtil.startNewActivityWithoutFinish(this, WebViewTemplateActivity.class);
//        ActivityUtil.startNewActivityWithoutFinish(this, CameraColorFilterActivity.class);
//        ActivityUtil.startNewActivityWithoutFinish(this, NestedScrollWithFixedBarActivity.class);
//        ActivityUtil.startNewActivityWithoutFinish(MainActivity.this, HandlerThreadTemplateActivity.class);
//        ActivityUtil.startNewActivityWithoutFinish(MainActivity.this, SystemCameraDemoActivity.class);
//        ActivityUtil.startNewActivityWithoutFinish(MainActivity.this, CameraTwoActivity.class);
//        ActivityUtil.startNewActivityWithoutFinish(MainActivity.this, TabCustomSlidingLayoutActivity.class);
//        ActivityUtil.startNewActivityWithoutFinish(MainActivity.this, CustomViewControllerActivity.class);
//        ActivityUtil.startNewActivityWithoutFinish(MainActivity.this, CustomCalenderActivity.class);
//        ActivityUtil.startNewActivityWithoutFinish(MainActivity.this, MVCDemoActivity.class);
//        ActivityUtil.startNewActivityWithoutFinish(MainActivity.this, MVPDemoActivity.class);



        SHVolley.getInstance().requestUrlGet(this, "https://m.baidu.com/", new SHVolley.SHVolleyDelegate() {
            @Override
            public void onResponse(String response) {

            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        imageViewMain1 = findViewById(R.id.imageViewMain1);

        ThreadUtil.startThread(new Runnable() {
            @Override
            public void run() {
                try {

//                    String imageUrl= "http://bmtonnoffcompany.xcache.kinxcdn.com/kinxcdn-thumbnail&application=onnoffcompany_trans&streamname=SuxIAR";
//                    URL url = new URL(imageUrl);
//                    HttpURLConnection connection  = (HttpURLConnection) url.openConnection();
//
//                    InputStream is = connection.getInputStream();
//                    Bitmap img = BitmapFactory.decodeStream(is);


                    URL url = new URL("http://bmtonnoffcompany.xcache.kinxcdn.com/kinxcdn-thumbnail&application=onnoffcompany_trans&streamname=SuxIAR");
                    Object content = url.getContent();

                    InputStream is = (InputStream) content;
                    Drawable d = Drawable.createFromStream(is, "src");


                    ThreadUtil.startUIThread(0, new Runnable() {
                        @Override
                        public void run() {
//                            imageViewMain1.setImageBitmap(img);
                            imageViewMain1.setImageDrawable(d);
                        }
                    });

                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

//        SHPicasso.getInstance().loadImage(this, "http://bmtonnoffcompany.xcache.kinxcdn.com/kinxcdn-thumbnail&application=onnoffcompany_trans&streamname=SuxIAR", imageViewMain1);

        imageViewMain2 = findViewById(R.id.imageViewMain2);


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

                /*
                 * translationX, translationY
                 * rotation, rotationX, rotationY
                 * scaleX, scaleY
                 * pivotX, pivotY
                 * x, y
                 * alpha
                 */
            }
        });


//        ActivityUtil.startNewActivityWithoutFinish(this, SHDemoMVCActivity.class);

//        ActivityUtil.startNewActivityWithoutFinish(this, SHSlidingTabViewPagerContainerActivity.class);
//        ActivityUtil.startNewActivityWithoutFinish(this, SHCardViewActivity.class);
//        ActivityUtil.startNewActivityWithoutFinish(this, SHTabHostActivity.class);
//        ActivityUtil.startNewActivityWithoutFinish(this, OKHttpDemoActivity.class);
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


//        ActivityUtil.startNewActivityWithoutFinish(this, DeviceStorageScannerActivity.class);


//        NotificationUIUtil.sendNotification(this, 0,
//                "title", "sub text", "message",
//                true, true,
//                null, "sh", "sh", "sh");



//        AsyncUtil.getInstance().run(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    for(int i = 0; i < 5; i++) {
//                        ILog.iLogDebug("???", i);
//                        Thread.sleep(1000);
//                    }
//                }
//                catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Runnable() {
//            @Override
//            public void run() {
//                ToastUtil.showShortToastNormal(MainActivity.this, "hahaha");
//            }
//        });



//        ThreadUtil.startThread(new Runnable() {
//
//            @Override
//            public void run() {
//                SHSQLiteController shsqLiteController = new SHSQLiteController(getApplicationContext());
//
//                for(int i = 0; i < 20; i++) {
//                    shsqLiteController.insert(UUID.randomUUID().toString(), String.valueOf(i));
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                List<SHSQLiteController.DataModel> list = shsqLiteController.getAllData();
//                for(SHSQLiteController.DataModel dataModel : list) {
//                    ILog.iLogDebug("db", dataModel.toString());
//                }
//
//                shsqLiteController.deleteDatabase();
//
//                List<SHSQLiteController.DataModel> list1 = shsqLiteController.getAllData();
//                for(SHSQLiteController.DataModel dataModel : list1) {
//                    ILog.iLogDebug("db", dataModel.toString());
//                }
//            }
//        });

//        ILog.iLogDebug(TAG, "reg p1 " + RegularExpressionUtil.isMatchPhoneNumberLength("13985888888"));
//        ILog.iLogDebug(TAG, "reg p2 " + RegularExpressionUtil.isMatchKoreanPhoneNumberLength("010-5506-0960"));
//        ILog.iLogDebug(TAG, "reg p3 " + RegularExpressionUtil.isMatchKoreanPhoneNumberLength("01055060960"));
//        ILog.iLogDebug(TAG, "reg p4 " + RegularExpressionUtil.isMatchKoreanPhoneNumberLength("02-2222-3333"));
//        ILog.iLogDebug(TAG, "reg p5 " + RegularExpressionUtil.isMatchKoreanPhoneNumberLength("0212341234"));
//        ILog.iLogDebug(TAG, "reg p6 " + RegularExpressionUtil.isMatchKoreanPhoneNumberLength("0702341234"));
//        ILog.iLogDebug(TAG, "reg p6 " + RegularExpressionUtil.isMatchKoreanPhoneNumberLength("070-234-1234"));
//        ILog.iLogDebug(TAG, "reg p7 " + RegularExpressionUtil.isMatchKoreanPhoneNumberLength("13985888888"));
//        ILog.iLogDebug(TAG, "reg p8 " + RegularExpressionUtil.isMatchKoreanPhoneNumberLength("010-5506"));
//        ILog.iLogDebug(TAG, "reg p9 " + RegularExpressionUtil.isMatchKoreanPhoneNumberLength("0105506"));
//
//        ILog.iLogDebug(TAG, "reg p11 " + RegularExpressionUtil.isMatchEmail("djseokho"));
//        ILog.iLogDebug(TAG, "reg p12 " + RegularExpressionUtil.isMatchEmail("djseokho@.com"));
//        ILog.iLogDebug(TAG, "reg p13 " + RegularExpressionUtil.isMatchEmail("djseokho@gmail.com"));
//        ILog.iLogDebug(TAG, "reg p14 " + RegularExpressionUtil.isMatchEmail("djseokho@.co"));
//        ILog.iLogDebug(TAG, "reg p15 " + RegularExpressionUtil.isMatchEmail("djseokho@vip.qq.com"));
//        ILog.iLogDebug(TAG, "reg p16 " + RegularExpressionUtil.isMatchEmail("@gmail.com"));

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

        if(!closeFlag){
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
            SHVolley.getInstance().close();
            finish();
        }

        return false;

    }

}

class TestObject implements Serializable {

    public final static long serialID = 1000000001L;

    public String name;

}
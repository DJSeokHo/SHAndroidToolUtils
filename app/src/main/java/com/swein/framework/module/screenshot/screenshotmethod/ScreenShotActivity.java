package com.swein.framework.module.screenshot.screenshotmethod;

import android.Manifest;
import android.app.AppOpsManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.swein.shandroidtoolutils.R;

public class ScreenShotActivity extends AppCompatActivity implements View.OnClickListener {


    private static final int MY_PERMISSIONS_REQUEST_PACKAGE_USAGE_STATS = 1101;

    private Button btn_start;
    private Button btn_stop;
    private Button btn_open;

    private Intent intent;
    private boolean myServiceStart;
    private ServiceConnection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_shot);

        initUI();
        permissionOperation();

    }

    private void ScreenShotsCaptureServiceBindOperate() {

        intent = new Intent();
        intent.setAction("com.remarkablesoft.test.MY_BIND_SERVICE");
        intent.setPackage(this.getPackageName());

        this.myServiceStart = false;

        connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

                System.out.println("===Setvice Connected===");

            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {

                System.out.println("===Setvice Disconnected===");

            }
        };
    }

    private void BindService() {
        if (!myServiceStart) {

            this.bindService(intent, connection, Service.BIND_AUTO_CREATE);
            myServiceStart = true;

        }
    }

    private void UnBindService() {

        if (myServiceStart) {

            this.unbindService(connection);
            myServiceStart = false;

        }
    }

    private void getRuntimePermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_PACKAGE_USAGE_STATS);

        }

    }

    private void permissionOperation() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (!checkPermission()) {

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("do you want to open access permission");

                builder.setTitle("Access Request");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                        System.out.println("Dialog Dismissed");
                        startActivityForResult(
                                new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS),
                                MY_PERMISSIONS_REQUEST_PACKAGE_USAGE_STATS);

                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Access Permission is Not Open", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.create().show();
            }
        }

    }

    private void initUI() {

        btn_start = findViewById(R.id.btn_start);
        btn_stop = findViewById(R.id.btn_stop);
        btn_open = findViewById(R.id.btn_open);

        btn_start.setOnClickListener(this);
        btn_stop.setOnClickListener(this);
        btn_open.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == MY_PERMISSIONS_REQUEST_PACKAGE_USAGE_STATS) {
            if (checkPermission()) {

                Toast.makeText(this, "Access Permission is Opened", Toast.LENGTH_SHORT).show();

            } else {

                Toast.makeText(this, "Access Permission is Not Open", Toast.LENGTH_SHORT).show();

            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btn_start:

                if (null == connection) {
                    Toast.makeText(this, "click open first", Toast.LENGTH_SHORT).show();
                    return;
                }

                BindService();

                break;

            case R.id.btn_stop:

                UnBindService();

                break;


            case R.id.btn_open:
                ScreenShotsCaptureServiceBindOperate();
                getRuntimePermission();
                btn_start.setClickable(true);
                break;

            default:

                break;
        }
    }


    private boolean checkPermission() {
        AppOpsManager appOps = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
        int mode = 0;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {

            mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, android.os.Process.myUid(), getPackageName());

        }
        return mode == AppOpsManager.MODE_ALLOWED;
    }

}

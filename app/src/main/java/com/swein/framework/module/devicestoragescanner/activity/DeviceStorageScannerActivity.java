package com.swein.framework.module.devicestoragescanner.activity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.widget.TextView;

import com.swein.framework.module.devicestoragescanner.data.StorageInfoData;
import com.swein.framework.module.devicestoragescanner.tool.StorageTool;
import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.shandroidtoolutils.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * document reference:
 * http://grepcode.com/file/repository.grepcode.com/java/ext/com.google.android/android/4.4_r1/android/os/Environment.java
 * https://github.com/android/platform_packages_apps_settings/tree/master/src/com/android/settings
 */
public class DeviceStorageScannerActivity extends Activity {

    private final static String TAG = "DeviceStorageScannerActivity";

    private TextView textView;

    UsbManager mUsbManager = null;
    IntentFilter filterAttached_and_Detached = null;

    private static final String ACTION_USB_PERMISSION = "tw.g35gtwcms.android.test.list_usb_otg.USB_PERMISSION";

    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            if (UsbManager.ACTION_USB_DEVICE_DETACHED.equals(action)) {
                synchronized (this) {
                    UsbDevice device = (UsbDevice)intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);

                    if(device != null){
                        //
                        ILog.iLogDebug(TAG,"DEATTCHED-" + device);
                    }
                }
            }

            if (UsbManager.ACTION_USB_DEVICE_ATTACHED.equals(action)) {
                synchronized (this) {
                    UsbDevice device = (UsbDevice)intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {

                        if(device != null){

                            ILog.iLogDebug(TAG,"ATTACHED-" + device);
                        }
                    }
                    else {
                        PendingIntent mPermissionIntent;
                        mPermissionIntent = PendingIntent.getBroadcast(DeviceStorageScannerActivity.this, 0, new Intent(ACTION_USB_PERMISSION), PendingIntent.FLAG_ONE_SHOT);
                        mUsbManager.requestPermission(device, mPermissionIntent);

                    }

                }
            }

            if (ACTION_USB_PERMISSION.equals(action)) {
                synchronized (this) {
                    UsbDevice device = (UsbDevice)intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {

                        if(device != null){
                            //
                            ILog.iLogDebug(TAG,"PERMISSION-" + device);
                        }
                    }

                }
            }

        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_storage_scanner);

        textView = findViewById(R.id.textView);

        ArrayList<StorageInfoData> storageData = new ArrayList<>();
        storageData.addAll(StorageTool.getStorageData(this));
        storageData.addAll(StorageTool.getStorageInfoListThatCannotBeFoundByStorageManager(this));

        StringBuffer stringBuffer = new StringBuffer();

        for(int i = 0; i < storageData.size(); i++) {
            stringBuffer.append(storageData.get(i).toString());
            stringBuffer.append("\n\n");
        }

        textView.setText(stringBuffer.toString());

        mUsbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
        //
        filterAttached_and_Detached = new IntentFilter(UsbManager.ACTION_USB_ACCESSORY_DETACHED);
        filterAttached_and_Detached.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        filterAttached_and_Detached.addAction(ACTION_USB_PERMISSION);
        //
        registerReceiver(mUsbReceiver, filterAttached_and_Detached);
        //

        HashMap<String, UsbDevice> deviceList = mUsbManager.getDeviceList();
        ILog.iLogDebug(TAG, deviceList.size() + " USB device(s) found");
        Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();

        while(deviceIterator.hasNext()){
            UsbDevice device = deviceIterator.next();
            ILog.iLogDebug(TAG,"" + device);
        }


    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mUsbReceiver);
        super.onDestroy();
    }

}

package com.swein.framework.module.devicestoragescanner.tool;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import androidx.core.os.EnvironmentCompat;

import com.swein.framework.module.devicestoragescanner.constants.DSSConstants;
import com.swein.framework.module.devicestoragescanner.data.StorageInfoData;
import com.swein.framework.module.devicestoragescanner.data.MountInfo;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class StorageTool {

    private static final String GET_VOLUME_LIST_METHOD = "getVolumeList";
    private static final String GET_STORAGE_VOLUME_METHOD = "android.os.storage.StorageVolume";
    private static final String GET_GET_PATH_METHOD = "getPath";
    private static final String GET_IS_REMOVEABLE_METHOD = "isRemovable";

    // getState() is added after kitkat
    private static final String GET_GET_STATE_METHOD = "getState";

    /**
     *
     * get storage device(card) info list
     *
     * @param context context
     * @return storage device(card) info list of current device
     */
    public static ArrayList<StorageInfoData> getStorageData(Context context) {

        final StorageManager storageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);

        try {

            //get method object that has "getVolumeList()" method from StorageManager
            final Method getVolumeList = storageManager.getClass().getMethod(GET_VOLUME_LIST_METHOD);

            //get object of StorageVolume class
            final Class<?> storageVolumeClazz = Class.forName(GET_STORAGE_VOLUME_METHOD);

            //get some method from StorageVolume
            final Method getPath = storageVolumeClazz.getMethod(GET_GET_PATH_METHOD);
            Method isRemovable = storageVolumeClazz.getMethod(GET_IS_REMOVEABLE_METHOD);

            Method getState = null;

            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {

                try {
                    getState = storageVolumeClazz.getMethod(GET_GET_STATE_METHOD);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

            }

            /*
                getVolumeList(who)
                here who is StorageManager
             */
            final Object invokeVolumeList = getVolumeList.invoke(storageManager);

            final int length = Array.getLength(invokeVolumeList);

            ArrayList<StorageInfoData> list = new ArrayList<>();

            for (int i = 0; i < length; i++) {
                Object storageVolume = Array.get(invokeVolumeList, i);
                String path = (String) getPath.invoke(storageVolume);
                boolean removable = (Boolean) isRemovable.invoke(storageVolume);

                String state;

                if (getState != null) {

                    state = (String) getState.invoke(storageVolume);
                }
                else {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

                        state = Environment.getStorageState(new File(path));

                    }
                    else {

                        if (removable) {

                            state = EnvironmentCompat.getStorageState(new File(path));

                        }
                        else {
                            // storage that can not be removed
                            state = Environment.MEDIA_MOUNTED;
                        }
                    }
                }

                long totalSize = 0;
                long usableSize = 0;

                if (Environment.MEDIA_MOUNTED.equals(state)) {
                    totalSize = getTotalSize(path);
                    usableSize = getUsableSize(path);
                }

                StorageInfoData storageInfoData = new StorageInfoData();
                storageInfoData.usableSize = usableSize;
                storageInfoData.totalSize = totalSize;
                storageInfoData.mounted = state;
                storageInfoData.path = path;
                storageInfoData.name = path.replace("/storage/", "");
                storageInfoData.removable = removable;

                list.add(storageInfoData);
            }


            StorageVolume[] volumes = (StorageVolume[]) invokeVolumeList;

            for (int i = 0; i < volumes.length; i++) {

                list.get(i).description = volumes[i].toString();

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                    if(volumes[i].toString().toLowerCase().contains(DSSConstants.SD_CARD_KEY)) {
                        list.get(i).type = DSSConstants.STORAGE_TYPE.SD_CARD;
                    }
                    else if(volumes[i].toString().toLowerCase().contains(DSSConstants.USB_KEY)) {
                        list.get(i).type = DSSConstants.STORAGE_TYPE.USB;
                    }
                    else {
                        list.get(i).type = DSSConstants.STORAGE_TYPE.LOCAL;
                    }
                }
                else {
                    String[] attr = volumes[i].toString().split(" ");

                    for(int j = 0; j < attr.length; j++) {
                        if(attr[j].contains(DSSConstants.SUB_SYSTEM_KEY)) {
                            String system = attr[j].replace(DSSConstants.SUB_SYSTEM_KEY + "=", "");
                            if(system.toLowerCase().trim().equals(DSSConstants.SD_CARD_KEY)) {
                                list.get(i).type = DSSConstants.STORAGE_TYPE.SD_CARD;
                            }
                            else if(system.toLowerCase().trim().equals(DSSConstants.USB_KEY)) {
                                list.get(i).type = DSSConstants.STORAGE_TYPE.USB;
                            }
                            else {
                                list.get(i).type = DSSConstants.STORAGE_TYPE.LOCAL;
                            }
                        }
                        // for LG phone
                        else if(attr[j].contains("mDescription")) {
                            String system = attr[j].replace("mDescription=", "");
                            if(system.toLowerCase().contains(DSSConstants.SD_CARD_KEY)) {
                                list.get(i).type = DSSConstants.STORAGE_TYPE.SD_CARD;
                            }

                            if(system.toLowerCase().contains(DSSConstants.USB_KEY)) {
                                list.get(i).type = DSSConstants.STORAGE_TYPE.USB;
                            }
                        }

                        // for android 5.0
                        if(attr[j].contains("mUuid") && !attr[j].contains("null")) {
                            list.get(i).name = attr[j].replace("mUuid=", "");
                        }
                    }
                }
            }

            return list;

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     *
     * get total size of storage device
     *
     * @param path
     * @return
     */
    private static long getTotalSize(String path) {
        try {
            StatFs statFs = new StatFs(path);
            long blockSize;
            long blockCountLong;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                blockSize = statFs.getBlockSizeLong();
                blockCountLong = statFs.getBlockCountLong();
            }
            else {
                blockSize = statFs.getBlockSize();
                blockCountLong = statFs.getBlockCount();
            }
            return blockSize * blockCountLong;
        }
        catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    /**
     * LG G5 can not find usb device info by Storage Manager
     * so need use this
     * @param context
     * @return
     */
    public static ArrayList<StorageInfoData> getStorageInfoListThatCannotBeFoundByStorageManager(Context context) {

        UsbManager usbManager = (UsbManager)context.getSystemService(Context.USB_SERVICE);
        HashMap<String, UsbDevice> deviceList = usbManager.getDeviceList();

        if(deviceList == null || 0 == deviceList.size()) {
            return new ArrayList<>();
        }

        ArrayList<StorageInfoData> tempList = getStorageData(context);
        ArrayList<StorageInfoData> removeList = new ArrayList<>();
        for(StorageInfoData s : tempList) {
            if(DSSConstants.STORAGE_STATE_UN_MOUNTED.equals(s.mounted) || !s.removable) {
                removeList.add(s);
            }
        }

        tempList.removeAll(removeList);

        MountedDeviceManager.getInstance().load(context);
        ArrayList<MountInfo> mountInfos =  MountedDeviceManager.getInstance().getMountInfos();

        ArrayList<MountInfo> removeMountList = new ArrayList<>();

        for(MountInfo mi : mountInfos) {

            if(!Arrays.asList(mi.values).contains("sdcardfs")) {
                removeMountList.add(mi);
            }
        }

        mountInfos.removeAll(removeMountList);

        List<HashMap<String, String>> mountUsbName = new ArrayList<>();

        for(MountInfo m : mountInfos) {

            for(int j = 0; j < m.values.length; j++) {

                String value = m.values[j];

                if(value.contains("/storage/") && !value.contains("emulated")) {

                    HashMap<String, String> hashMap = new HashMap<>();

                    String name = value.replace("/storage/", "");
                    hashMap.put("name", name);
                    hashMap.put("path", value);
                    mountUsbName.add(hashMap);
                }
            }
        }

        ArrayList<StorageInfoData> mountedList = new ArrayList<>();

        for(HashMap<String, String> m : mountUsbName) {
            StorageInfoData storageInfoData = new StorageInfoData();
            storageInfoData.name = m.get("name");
            storageInfoData.type = DSSConstants.STORAGE_TYPE.USB;
            storageInfoData.removable = true;
            storageInfoData.mounted = DSSConstants.STORAGE_STATE_MOUNTED;
            storageInfoData.path = m.get("path");
            mountedList.add(storageInfoData);
        }

        return getDifferenceSet(tempList, mountedList);
    }

    private static ArrayList<StorageInfoData> getDifferenceSet(ArrayList<StorageInfoData> list1, ArrayList<StorageInfoData> list2) {

        ArrayList<StorageInfoData> storageInfoDataList = new ArrayList<>();

        for(StorageInfoData m : list2) {
            boolean duplicate = false;

            for(StorageInfoData t : list1) {

                if(m.name.equals(t.name)) {
                    duplicate = true;
                    break;
                }
            }

            if(!duplicate) {
                storageInfoDataList.add(m);
            }
        }

        return storageInfoDataList;
    }


    public static boolean isCurrentStorageUSB(Context context, String storageName) {

        List<StorageInfoData> list = new ArrayList<StorageInfoData>();
        list.addAll(getStorageData(context));
        list.addAll(getStorageInfoListThatCannotBeFoundByStorageManager(context));

        if(0 == list.size()) {
            return false;
        }

        for(StorageInfoData storageInfoData : list) {
            if(storageInfoData.name.equals(storageName) && DSSConstants.STORAGE_STATE_MOUNTED.equals(storageInfoData.mounted) && storageInfoData.removable) {
                if(DSSConstants.STORAGE_TYPE.USB == storageInfoData.type) {
                    return true;
                }
            }
        }

        return false;
    }


    /**
     *
     * get usable size of storage device
     *
     * @param path
     * @return
     */
    private static long getUsableSize(String path) {

        try {

            StatFs statFs = new StatFs(path);
            long blockSize;
            long availableBlocks;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                blockSize = statFs.getBlockSizeLong();
                availableBlocks = statFs.getAvailableBlocksLong();
            }
            else {
                blockSize = statFs.getBlockSize();
                availableBlocks = statFs.getAvailableBlocks();
            }

            return availableBlocks * blockSize;
        }
        catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private static String calculateSpace(long space) {
        if (space <= 0) {
            return "0";
        }

        double gbValue = (double) space / DSSConstants.SH_GB;

        if (gbValue >= 1) {
            return String.format("%.2fGB", gbValue);
        }
        else {

            double mbValue = (double) space / DSSConstants.SH_MB;

            if (mbValue >= 1) {
                return String.format("%.2fMB", mbValue);
            }
            else {
                return String.format("%.2fKB", (double) space / DSSConstants.SH_KB);
            }
        }
    }

}

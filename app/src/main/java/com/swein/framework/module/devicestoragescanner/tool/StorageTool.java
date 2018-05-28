package com.swein.framework.module.devicestoragescanner.tool;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.support.v4.os.EnvironmentCompat;

import com.swein.framework.module.devicestoragescanner.constants.DSSConstants;
import com.swein.framework.module.devicestoragescanner.data.StorageInfoData;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class StorageTool {

    private static final String TAG = "StorageTool";
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
                storageInfoData.removable = removable;

                list.add(storageInfoData);
            }


            StorageVolume[] volumes = (StorageVolume[]) invokeVolumeList;
            for (int i = 0; i < volumes.length; i++) {
                list.get(i).description = volumes[i].toString();
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

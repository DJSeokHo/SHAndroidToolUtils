package com.swein.framework.module.devicestoragescanner.tool;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Environment;

import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.framework.module.devicestoragescanner.data.MountInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class MountedDeviceManager {

    private static final String TAG = "MountedDeviceManager";
    private static final String MOUNT_FILE_PATH = "/proc/mounts";
    private static final MountedDeviceManager instance = new MountedDeviceManager();

    private MountedDeviceManager() {
    }

    public static MountedDeviceManager getInstance() {
        return instance;
    }

    private ArrayList<MountInfo> mountInfos;
    private ArrayList<String> externalFilesDirs;

    public ArrayList<MountInfo> getMountInfos() {
        return mountInfos;
    }

    public void load(Context context) {
        readMountsFile();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            getExternalFilesDir(context);

            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {

                updateTypesKitkat();

            }
            else {

                updateTypesAfterKitkat();

            }
        }
        else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {

            updateTypesBeforeKitkat();

        }
    }

    private void updateTypesBeforeKitkat() {

        for (MountInfo info : mountInfos) {

            if (!info.isExternalStorage()) {
                info.setType(MountInfo.MountType.UNKNOWN);
                continue;
            }

            if (info.getRealPath().toLowerCase().contains("usb") || info.getRealPath().toLowerCase().contains("udisk")) {
                info.setType(MountInfo.MountType.USB);
            }
            else {
                info.setType(MountInfo.MountType.SDCARD);
            }

        }
    }


    private void readMountsFile() {

        mountInfos = new ArrayList<>();
        File mountFile = new File(MOUNT_FILE_PATH);
        FileInputStream inputStream = null;
        Scanner scanner = null;

        try {
            inputStream = new FileInputStream(mountFile);
            scanner = new Scanner(inputStream);
            StringBuilder stringBuilder = new StringBuilder();
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                stringBuilder.append(line);
                stringBuilder.append("\n");
                mountInfos.add(MountInfo.createMountInfoFromReadLine(line));
            }

        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finally {

            scanner.close();

            try {
                inputStream.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        findRealPath();
    }

    private void findRealPath() {

        boolean isRedirect;
        for (int i = 0; i < mountInfos.size(); ++i) {

            isRedirect = false;

            for (int j = 0; j < mountInfos.size(); ++j) {

                if (i == j) {
                    continue;
                }

                if (mountInfos.get(i).values[1].compareTo(mountInfos.get(j).values[0]) == 0) {
                    mountInfos.get(i).setLastPathWithComponent(mountInfos.get(j).values[1]);
                    isRedirect = true;
                }

            }
            if (!isRedirect) {
                mountInfos.get(i).setLastPathWithComponent(mountInfos.get(i).values[1]);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void getExternalFilesDir(Context context) {

        externalFilesDirs = new ArrayList<>();
        File[] files = context.getExternalFilesDirs(null);

        for (File file : files) {

            if (file != null) {

                externalFilesDirs.add(file.getAbsolutePath());

            }

        }
    }

    private void updateTypesKitkat() {

        boolean isSdCard;

        for (MountInfo info : mountInfos) {

            isSdCard = false;

            if (info.getRealPath().startsWith(getInternalPath())) {
                info.setType(MountInfo.MountType.UNKNOWN);
                continue;
            }

            for (String path : externalFilesDirs) {
                if (path.startsWith(getInternalPath())) {
                    continue;
                }

                if (path.startsWith(info.values[1])) {
                    isSdCard = true;
                    findSdCardPath(info);
                }

                if (path.startsWith(info.getRealPath())) {
                    isSdCard = true;
                    info.setType(MountInfo.MountType.SDCARD);
                    info.setLastPath(path);
                }
            }

            if (info.isExternalStorage() && !isSdCard) {
                findUsbPath(info);
            }

            if (info.isTypeNull()) {
                info.setType(MountInfo.MountType.UNKNOWN);
            }
        }
    }

    private void updateTypesAfterKitkat() {

        boolean isSdCard;

        for (MountInfo info : mountInfos) {
            isSdCard = false;
            if (!info.isExternalStorage()) {
                info.setType(MountInfo.MountType.UNKNOWN);
                continue;
            }

            for (String path : externalFilesDirs) {

                ILog.iLogDebug(TAG, "==== path : " + path);

                if (path.startsWith(info.getRealPath())) {

                    isSdCard = true;
                    info.setType(MountInfo.MountType.SDCARD);

                }
            }

            if (!isSdCard) {
                info.setType(MountInfo.MountType.USB);
            }

        }
    }

    private String getInternalPath() {
        return Environment.getExternalStorageDirectory().getPath();
    }


    private void findUsbPath(MountInfo mountInfo) {
        for (MountInfo info : mountInfos) {

            if (mountInfo.values[0].equals(info.values[0])) {
                continue;
            }

            String name = new File(info.values[1]).getName();
            String finder = new File(mountInfo.values[1]).getName();

            if (name.equals(finder)) {

                mountInfo.setType(MountInfo.MountType.USB);
                mountInfo.setLastPath(info.values[1]);

            }
        }
    }

    private void findSdCardPath(MountInfo mountInfo) {

        for (MountInfo info : mountInfos) {

            String name = new File(info.values[1]).getName();
            String finder = new File(mountInfo.values[1]).getName();

            if (name.equals(finder)) {

                info.setType(MountInfo.MountType.SDCARD);
                info.setLastPath(mountInfo.values[1]);

            }
        }

    }


}

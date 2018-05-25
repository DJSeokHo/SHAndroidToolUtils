package com.swein.framework.module.devicestoragescanner.activity;

import android.app.Activity;
import android.os.Bundle;

import com.swein.framework.module.devicestoragescanner.data.StorageInfoData;
import com.swein.framework.module.devicestoragescanner.tool.StorageTool;
import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.shandroidtoolutils.R;

import java.util.ArrayList;

/**
 * document:
 * http://grepcode.com/file/repository.grepcode.com/java/ext/com.google.android/android/4.4_r1/android/os/Environment.java
 * https://github.com/android/platform_packages_apps_settings/tree/master/src/com/android/settings
 */
public class DeviceStorageScannerActivity extends Activity {

    private final static String TAG = "DeviceStorageScannerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_storage_scanner);

        ArrayList<StorageInfoData> storageData = StorageTool.getStorageData(this);

        for(int i = 0; i < storageData.size(); i++) {
            ILog.iLogDebug(TAG, storageData.get(i).toString());
        }
    }

//
//    @Override
//    public void onOpenExplorer(String url) {
//        try {
//            if (!TextUtils.isEmpty(url)) {
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                final String testFilePath = getTestDataPath(url);
//                final File file = new File(testFilePath);
//                if (file.exists()) {
//                    Uri uri = Uri.parse(testFilePath);
//                    intent.setDataAndType(uri, "text/plain");
//                    startActivity(Intent.createChooser(intent, "use system explorer"));
//                } else {
//                    writeStringToFile(testFilePath, file);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void onWriteTest(String url) {
//        String filePath = getTestDataPath(url);
//        final File testFile = new File(filePath);
//        writeStringToFile(filePath, testFile);
//    }
//
//    @TargetApi(Build.VERSION_CODES.KITKAT)
//    private void getFilesDirs() {
//        try {
//            final File[] dirs = getExternalFilesDirs(null);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void writeStringToFile(String filePath, File testFile) {
//        FileOutputStream fileOutputStream = null;
//        try {
//            if (!testFile.exists()) {
//                final boolean createSuccess = testFile.createNewFile();
//                final String notice = createSuccess ? "create " + filePath + "success!" : "create" + filePath + "failed!";
//                showSnack(notice);
//
//                if (createSuccess) {
//                    fileOutputStream = new FileOutputStream(testFile);
//                    fileOutputStream.write("test test ".getBytes());
//                }
//            } else {
//                fileOutputStream = new FileOutputStream(testFile, true);
//                fileOutputStream.write("+1".getBytes());
//                showSnack("add success");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            showSnack(e.getMessage());
//        } finally {
//            if (fileOutputStream != null) {
//                try {
//                    fileOutputStream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//    @NonNull
//    private String getTestDataPath(String url) {
//        if (Build.VERSION.SDK_INT >= 19) {
//            getFilesDirs();
//        }
//        final String dir = url + "/Android/data/" + getPackageName() + "/";
//        final File file = new File(dir);
//        if (!file.exists()) {
//            file.mkdirs();
//        }
//        return dir + "test.txt";
//    }
//
//    private void showSnack(String notice) {
//        if (notice != null) {
//            Snackbar.make(mRecyclerView, notice, Snackbar.LENGTH_LONG).show();
//        }
//    }
//
//    @Override
//    public void onDeleteTest(String url) {
//        final File testFilePath = new File(getTestDataPath(url));
//        if (testFilePath.exists()) {
//            final boolean delete = testFilePath.delete();
//            showSnack(delete ? "delete success！" : "delete failed");
//        } else {
//            showSnack(getTestDataPath(url) + " file not exists！");
//        }
//    }
}

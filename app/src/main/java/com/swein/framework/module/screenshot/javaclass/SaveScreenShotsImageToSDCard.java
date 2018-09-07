package com.swein.framework.module.screenshot.javaclass;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;

import com.swein.framework.tools.util.debug.log.ILog;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

/**
 * Created by seokho on 8/11/16.
 */
public class SaveScreenShotsImageToSDCard {


    private final static String TAG = "SaveScreenShotsImageToSDCard";

    private Context context;

    public SaveScreenShotsImageToSDCard(Context context) {
        this.context = context;
    }

    public void ShowAlertDialog(Context mContext, String path, String dir, String appName) {

        ImageView img = new ImageView(mContext);

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Test");
        builder.setMessage("Dir is " + dir + "\n\n" + "Path is : " + path + "\n\n" + "AppName is : " + appName);
        builder.setView(img);
        builder.setPositiveButton("OK", null);

        AlertDialog dialog = builder.create();
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);//use alert.
        dialog.show();

    }

    public void SaveScreenShots(String path) {

        File imgFile = new File(path);
        if (imgFile.exists()) {

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getPath());

            File myDir = new File("/sdcard/Save_Images/");
            myDir.mkdirs();
            Random generator = new Random();
            int n = 10000;
            n = generator.nextInt(n);
            String fname = "Image-" + n + ".png";
            ILog.iLogDebug(TAG, fname + "is writing");
            File file = new File(myDir, fname);
            if (file.exists()) file.delete();

            try {
                FileOutputStream out = new FileOutputStream(file);
                myBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                out.flush();
                out.close();
                ILog.iLogDebug(TAG, "save successed");
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

}

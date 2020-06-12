package com.swein.framework.module.imageselector.demo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.swein.framework.module.imageselector.ImageFolderItemBean;
import com.swein.framework.module.imageselector.ImageItemBean;
import com.swein.framework.module.imageselector.imageselectorwrapper.ImageSelectorWrapper;
import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.shandroidtoolutils.R;

import java.util.List;

public class ImageSelectorActivity extends AppCompatActivity {

    private final static String TAG = "ImageSelectorActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_selector);

        findViewById(R.id.button).setOnClickListener(view -> {
            ImageSelectorWrapper.getInstance().scanImageFile(this, new ImageSelectorWrapper.ImageSelectorWrapperDelegate() {
                @Override
                public void onSuccess(List<ImageFolderItemBean> imageFolderItemBeans, List<ImageItemBean> imageItemBeanList) {
                    ILog.iLogDebug(TAG, imageFolderItemBeans.size());
                    ILog.iLogDebug(TAG, imageItemBeanList.size());
                    ILog.iLogDebug(TAG, "finish");
                }

                @Override
                public void onError() {

                }
            });
        });
    }

}
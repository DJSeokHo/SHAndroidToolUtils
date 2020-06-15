package com.swein.framework.module.imageselector.demo;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.swein.framework.module.imageselector.selector.ImageSelectorViewHolder;
import com.swein.shandroidtoolutils.R;

public class ImageSelectorActivity extends AppCompatActivity {

    private final static String TAG = "ImageSelectorActivity";

    private FrameLayout frameLayoutContainer;
    private ImageSelectorViewHolder imageSelectorViewHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_selector);

        frameLayoutContainer = findViewById(R.id.frameLayoutContainer);

        imageSelectorViewHolder = new ImageSelectorViewHolder(this, 10);
        frameLayoutContainer.addView(imageSelectorViewHolder.getView());

        imageSelectorViewHolder.imageSelectorViewHolderDelegate = new ImageSelectorViewHolder.ImageSelectorViewHolderDelegate() {
            @Override
            public void onInitFinish() {
                imageSelectorViewHolder.reload();
            }
        };
    }
}
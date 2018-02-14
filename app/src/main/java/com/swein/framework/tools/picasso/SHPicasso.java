package com.swein.framework.tools.picasso;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by seokho on 01/02/2018.
 */

public class SHPicasso {

    private SHPicasso() {}

    private static SHPicasso instance = new SHPicasso();

    public static SHPicasso getInstance() {
        return instance;
    }

    public void loadImage(Context context, String url, ImageView imageView, boolean placeHolder, int resource) {
        if(placeHolder) {
            Picasso.with(context).load(url).placeholder(resource).error(resource).into(imageView);
            return;
        }

        Picasso.with(context).load(url).into(imageView);
    }

    public void loadImage(Context context, int imageResource, ImageView imageView, boolean placeHolder, int resource) {
        if(placeHolder) {
            Picasso.with(context).load(imageResource).placeholder(resource).error(resource).into(imageView);
            return;
        }

        Picasso.with(context).load(imageResource).into(imageView);
    }

    public void loadImageCustomSize(Context context, String url, ImageView imageView, int width, int height, boolean placeHolder, int resource) {
        if(placeHolder) {
            Picasso.with(context).load(url).placeholder(resource).error(resource).resize(width, height).centerCrop().into(imageView);
            return;
        }
        Picasso.with(context).load(url).resize(width, height).centerCrop().into(imageView);
    }
}

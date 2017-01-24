package com.swein.data.cache.volley;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by seokho on 24/01/2017.
 */

public class BitmapCache implements ImageLoader.ImageCache {
    public LruCache<String, Bitmap> cache;

    public int max = 10 * 1024 * 1024;

    public BitmapCache() {
        cache = new LruCache<String, Bitmap>(max){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }
        };
    }

    @Override
    public Bitmap getBitmap(String url) {
        return cache.get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        cache.put(url, bitmap);
    }
}

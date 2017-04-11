package com.swein.surfaceview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * SurfaceView Template
 * Created by seokho on 11/04/2017.
 */

public class SurfaceViewTemplate extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private SurfaceHolder surfaceHolder;
    private Canvas        canvas;

    // thread flag
    private boolean isDrawing;

    public SurfaceViewTemplate( Context context ) {
        super( context );
        initView();
    }

    public SurfaceViewTemplate( Context context,  AttributeSet attrs ) {
        super(context, attrs);
        initView();
    }

    public SurfaceViewTemplate( Context context,  AttributeSet attrs, int defStyle ) {
        super(context, attrs, defStyle);
        initView();
    }

    @Override
    public void surfaceCreated( SurfaceHolder holder ) {
        isDrawing = true;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged( SurfaceHolder holder, int format, int width, int height ) {

    }

    @Override
    public void surfaceDestroyed( SurfaceHolder holder ) {
        isDrawing = false;
    }

    @Override
    public void run() {

        while ( isDrawing ) {
            draw();
        }

    }

    private void initView() {
        surfaceHolder = getHolder();
        surfaceHolder.addCallback( this );
        setFocusable( true );
        setFocusableInTouchMode( true );
        this.setKeepScreenOn( true );

//        surfaceHolder.setFormat( PixelFormat.OPAQUE );
    }

    private void draw() {
        try {
            canvas = surfaceHolder.lockCanvas();

            //draw something here

        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
        finally {
            if(canvas != null) {
                surfaceHolder.unlockCanvasAndPost( canvas );
            }
        }
    }
}

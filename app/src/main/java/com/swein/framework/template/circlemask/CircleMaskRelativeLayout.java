package com.swein.framework.template.circlemask;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 *
 * Created by seokho on 14/08/2017.
 */

public class CircleMaskRelativeLayout extends RelativeLayout {

    Path path;

    public CircleMaskRelativeLayout( Context context ) {
        super( context );
    }

    public CircleMaskRelativeLayout( Context context, AttributeSet attrs ) {
        super( context, attrs );
    }

    public CircleMaskRelativeLayout( Context context, AttributeSet attrs, int defStyleAttr ) {
        super( context, attrs, defStyleAttr );
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CircleMaskRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes ) {
        super( context, attrs, defStyleAttr, defStyleRes );
    }

    @Override
    protected void dispatchDraw(Canvas canvas){

        if(path == null) {
            path = new Path(  );
            int w = getWidth();
            int h = getWidth();
            path.addRoundRect( new RectF( 0, 0, getWidth(), getHeight()), w * 0.5f, h * 0.5f, Path.Direction.CW);
        }

        canvas.clipPath(path);
        super.dispatchDraw(canvas);
    }

}

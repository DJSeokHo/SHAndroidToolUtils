package com.swein.recycleview.random.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * Created by seokho on 06/03/2017.
 */

public class CustomViewClass extends TextView {

    private Context context;
    private Paint   paint;

    public CustomViewClass( Context context ) {
        super( context );
        this.context = context;
    }

    public CustomViewClass(Context context, AttributeSet attrs) {
        super(context, attrs);

        //初始化画笔
        initPaint();
        setLayerType( View.LAYER_TYPE_SOFTWARE, paint); //4.0以上关闭硬件加速，否则虚线不显示
    }

    private void initPaint() {
        paint = new Paint();
        paint.setAntiAlias(true); //设置抗锯齿的效果
//        paint.setStyle(Paint.Style.STROKE); //设置画笔样式为描边
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(3);  //设置笔刷的粗细度
        paint.setColor( Color.RED); //设置画笔的颜色
        paint.setPathEffect(new DashPathEffect( new float[]{5,5,5,5}, 1)); //设置画笔的路径效果为虚线效果
    }

    @Override
    protected void onDraw( Canvas canvas ) {
        super.onDraw( canvas );

//        canvas.drawLine(0, 0, 100, 0, paint);
//        canvas.drawCircle(50, 50, 50, paint);

        RectF rect = new RectF( 0, 0, 80, 60);
        canvas.drawRect(rect, paint);
    }
}

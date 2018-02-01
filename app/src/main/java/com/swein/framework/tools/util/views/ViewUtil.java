package com.swein.framework.tools.util.views;

import android.content.Context;
import android.graphics.Outline;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.swein.shandroidtoolutils.R;

/**
 * Created by seokho on 25/12/2016.
 */

public class ViewUtil {

    public static View viewLayoutInflater(int resourceId, ViewGroup parent, boolean attachToRoot) {
        return LayoutInflater.from(parent.getContext()).inflate(resourceId, parent, attachToRoot);
    }

    public static void viewSetClickListener( View view, View.OnClickListener onClickListener ) {
        view.setOnClickListener( onClickListener );
    }

    public static void shakeView( Context context, View view ) {
        Animation shake = AnimationUtils.loadAnimation( context, R.anim.shake );
        view.startAnimation( shake );
    }

    public static void setViewDepth( View view, float depthDP ) {
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ) {
            view.setTranslationZ( depthDP );
        }
    }

    public static void setViewRoundRect( View view, boolean canClipToOutline ) {
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ) {
            ViewOutlineProvider viewOutlineProvider = new ViewOutlineProvider() {
                @Override
                public void getOutline( View view, Outline outline ) {
                    if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ) {
                        outline.setRoundRect( 0, 0, view.getWidth(), view.getHeight(), 30 );
                    }
                }
            };

            view.setClipToOutline( canClipToOutline );
            view.setOutlineProvider( viewOutlineProvider );
        }
    }

    public static void setViewCircle( View view, boolean canClipToOutline, final int width, final int height ) {

        if(0 == width || height == 0) {
            return;
        }

        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ) {
            ViewOutlineProvider viewOutlineProvider = new ViewOutlineProvider() {
                @Override
                public void getOutline( View view, Outline outline ) {
                    if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ) {

                        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                        layoutParams.width = width;
                        layoutParams.height = height;
                        view.setLayoutParams(layoutParams);

                        //if is image view, than warp_content is not working, must set width, height dp, best is 80dp ~ 100dp
                        //and use for clip background better than clip src
                        outline.setOval(0, 0, view.getWidth(), view.getHeight());
                    }
                }
            };

            view.setClipToOutline( canClipToOutline );
            view.setOutlineProvider( viewOutlineProvider );
        }
    }

}

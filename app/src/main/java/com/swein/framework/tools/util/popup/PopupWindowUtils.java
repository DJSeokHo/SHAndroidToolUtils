package com.swein.framework.tools.util.popup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;

import com.swein.framework.tools.util.toast.ToastUtils;
import com.swein.shandroidtoolutils.R;

/**
 * Created by seokho on 07/12/2016.
 */

public class PopupWindowUtils {


    /**
     * this method can not use at create() of any class because this method will called after activity shown
     * @param context
     * @param layoutInflater
     * @param viewResource
     * @param popupWindowWidth
     * @param popupWindowHeight
     * @param gravity
     * @param popupWindowShowAtX
     * @param popupWindowShowAtY
     * @param parnet
     */
    public static void createPopupWindowWithView(final Context context, LayoutInflater layoutInflater, int viewResource, int popupWindowWidth, int popupWindowHeight, int gravity, int popupWindowShowAtX, int popupWindowShowAtY, View parnet) {

        View root = layoutInflater.inflate(viewResource, null);

        final PopupWindow popupWindow = new PopupWindow(root, popupWindowWidth, popupWindowHeight);

        Button button = (Button) root.findViewById(R.id.popupWindowViewButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showCustomShortToastNormal(context, "OK");
                popupWindow.dismiss();
            }
        });

        popupWindow.showAtLocation(parnet, gravity, popupWindowShowAtX, popupWindowShowAtY);
    }

}

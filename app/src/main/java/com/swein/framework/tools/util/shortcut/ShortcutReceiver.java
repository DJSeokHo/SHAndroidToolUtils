package com.swein.framework.tools.util.shortcut;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.swein.framework.tools.util.toast.ToastUtil;

/**
 * in AndroidManifest.xml
 *
 * <receiver
 *             android:name="com.swein.framework.tools.util.shortcut.ShortcutReceiver"
 *             android:exported="false" />
 */
public class ShortcutReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        ToastUtil.showShortToastNormal(context, "바로가기 추가되었습니다.");
    }
}

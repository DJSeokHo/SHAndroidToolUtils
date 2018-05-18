package com.swein.framework.module.mdmcustom.receiver;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;


import com.swein.framework.tools.eventsplitshot.eventcenter.EventCenter;
import com.swein.framework.tools.eventsplitshot.subject.ESSArrows;
import com.swein.framework.tools.util.toast.ToastUtil;

public class SHMDMReceiver extends DeviceAdminReceiver {

    @Override
    public void onEnabled(Context context, Intent intent) {
        ToastUtil.showShortToastNormal(context, "출근중 핸드폰 일부 기능 제한 가능합니다");
        EventCenter.getInstance().sendEvent(ESSArrows.ESS_DEVICE_MANAGER_USABLE, this, null);
    }

    @Override
    public void onDisabled(Context context, Intent intent) {
        ToastUtil.showShortToastNormal(context, "핸드폰 기능 제한 해제되었습니다");
        EventCenter.getInstance().sendEvent(ESSArrows.ESS_DEVICE_MANAGER_DISABLE, this, null);
    }

    
    @Override
    public CharSequence onDisableRequested(Context context, Intent intent) {
        return "device manager 'activate canceled'，feature is not supported";
    }

}

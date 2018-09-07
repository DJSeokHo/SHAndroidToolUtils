package com.swein.framework.module.easyscreenrecord.data.local;

import android.content.Intent;

/**
 * Created by seokho on 03/02/2017.
 */

public class IntentData {

    private Intent screenRecordIntent;

    public void setScreenRecordIntent(Intent screenRecordIntent) {
        this.screenRecordIntent = screenRecordIntent;
    }

    public Intent getScreenRecordIntent() {
        return this.screenRecordIntent;
    }

}

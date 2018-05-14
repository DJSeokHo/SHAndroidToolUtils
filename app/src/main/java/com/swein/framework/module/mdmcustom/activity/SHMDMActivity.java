package com.swein.framework.module.mdmcustom.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.iid.FirebaseInstanceId;
import com.swein.framework.module.mdmcustom.fragment.SHMDMFragment;
import com.swein.framework.tools.util.activity.ActivityUtil;
import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.framework.tools.util.theme.ThemeUtil;
import com.swein.shandroidtoolutils.R;

public class SHMDMActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeUtil.setWindowStatusBarColorResource(this, R.color.color5);
        setContentView(R.layout.activity_sh_mdm);

        GoogleApiAvailability.getInstance().makeGooglePlayServicesAvailable(this);

        // Get token
        String token = FirebaseInstanceId.getInstance().getToken();

        ILog.iLogDebug(getClass().getSimpleName(), token);

        if(savedInstanceState == null) {

            ActivityUtil.addFragment(this, R.id.container, new SHMDMFragment(), false);

        }

    }
}

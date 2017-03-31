package com.swein.framework.module.spacemanage;

import android.app.Activity;
import android.os.Bundle;

/**
 * block user clear data
 *
 * Created by seokho on 30/03/2017.
 *
 *
 * in your AndroidManifest.xml:
 *
     <application
         android:name=".xxxxxxx"
         ...
         ...
         ...
         android:manageSpaceActivity="your_package_name.ManageSpaceActivity"
         ...
         ...>
 *
 *
 * it's done
 */

public class ManageSpaceActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        finish();

    }
}

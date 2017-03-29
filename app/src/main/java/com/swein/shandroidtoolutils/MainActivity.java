package com.swein.shandroidtoolutils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.swein.framework.module.userinfo.install.checker.UsageInstallChecker;
import com.swein.recycleview.random.activity.RecyclerViewRandomActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        UsageInstallChecker usageInstallChecker= new UsageInstallChecker( this );

        usageInstallChecker.checkUsageInfoJSONObject();

//        startActivity(new Intent(this, DelegateExampleActivity.class));
//        startActivity(new Intent(this, VideoViewActivity.class));
//        startActivity(new Intent(this, RecyclerViewListActivity.class));
        startActivity(new Intent(this, RecyclerViewRandomActivity.class));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();



    }
}

package com.swein.framework.template.customviewcontroller.demo;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.fragment.app.FragmentActivity;

import com.swein.framework.template.customviewcontroller.NavigationViewController;
import com.swein.shandroidtoolutils.R;

public class CustomViewControllerActivity extends FragmentActivity {

    private FrameLayout frameLayoutNavigation;

    private NavigationViewController navigationViewController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view_controller);

        frameLayoutNavigation = findViewById(R.id.frameLayoutNavigation);

        navigationViewController = new NavigationViewController(this);
        navigationViewController.setTitle("haha");
        frameLayoutNavigation.addView(navigationViewController);

    }

    @Override
    public void onBackPressed() {

        if(navigationViewController != null) {

            frameLayoutNavigation.removeView(navigationViewController);
            navigationViewController = null;

            return;
        }

        finish();
    }
}

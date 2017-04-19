package com.swein.activity.animation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.swein.framework.tools.util.activity.ActivityUtils;
import com.swein.shandroidtoolutils.R;

public class StartActivity extends AppCompatActivity {

    private Button buttonStartActivity;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_start );


        buttonStartActivity = (Button)findViewById( R.id.buttonStartActivity );

        buttonStartActivity.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                ActivityUtils.startActivityWithTransitionAnimationWithFinish(StartActivity.this, EndActivity.class);
            }
        } );

    }
}

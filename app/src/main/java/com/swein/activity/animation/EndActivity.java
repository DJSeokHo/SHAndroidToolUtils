package com.swein.activity.animation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.swein.framework.tools.util.activity.ActivityUtils;
import com.swein.shandroidtoolutils.R;

public class EndActivity extends AppCompatActivity {

    private Button buttonEndActivity;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_end );

        ActivityUtils.tagetActivitySetEnterTransitionAnimationFade( this );

        buttonEndActivity = (Button)findViewById( R.id.buttonEndActivity );

        buttonEndActivity.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                ActivityUtils.startNewActivityWithFinish( EndActivity.this, StartActivity.class);
            }
        } );

    }
}

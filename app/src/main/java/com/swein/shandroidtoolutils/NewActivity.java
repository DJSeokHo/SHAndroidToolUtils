package com.swein.shandroidtoolutils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.swein.data.singleton.key.KeyData;
import com.swein.data.singleton.request.RequestData;
import com.swein.framework.tools.util.activity.ActivityUtils;

public class NewActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.setActivityResultStringWithFinish(NewActivity.this, "get activity result", RequestData.ACTIVITY_RESULT_CODE);
            }
        });
    }
}

package com.swein.framework.module.datastore.demo;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.swein.framework.module.datastore.DataStoreManager;
import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.shandroidtoolutils.R;

public class DataStoreDemoActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_store_demo);

        textView = findViewById(R.id.textView);

        DataStoreManager.instance.init(this);

        findViewById(R.id.buttonSave).setOnClickListener(view -> {
            ILog.iLogDebug("???", "save");

            DataStoreManager.instance.saveValue("123", "aaaaaa");

        });

        findViewById(R.id.buttonLoad).setOnClickListener(view -> {
            ILog.iLogDebug("???", "load");

            DataStoreManager.instance.getValue("123", value -> {
                textView.setText((String) value);
            });

        });
    }

}
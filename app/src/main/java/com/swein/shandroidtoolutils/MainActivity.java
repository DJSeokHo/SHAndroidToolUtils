package com.swein.shandroidtoolutils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ImageView imageViewRequest, imageViewLoader;
    TextView textView;
    Button btnReload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageViewRequest = (ImageView) findViewById(R.id.imageViewRequest);
        imageViewLoader = (ImageView) findViewById(R.id.imageViewLoader);
        textView = (TextView) findViewById(R.id.textView);
        btnReload = (Button) findViewById(R.id.btnReload);



        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

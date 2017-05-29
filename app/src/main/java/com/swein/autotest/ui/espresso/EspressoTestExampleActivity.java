package com.swein.autotest.ui.espresso;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.swein.shandroidtoolutils.R;

public class EspressoTestExampleActivity extends AppCompatActivity {

    private EditText editText1;
    private EditText editText2;
    private Button calculateButton;
    private TextView textViewResult;
    private Button buttonWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espresso_test_example);
        editText1 = (EditText) findViewById(R.id.editText1);
        editText2 = (EditText) findViewById(R.id.editText2);
        calculateButton = (Button) findViewById(R.id.calculateButton);
        textViewResult = (TextView) findViewById(R.id.textViewResult);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String string1 = editText1.getText().toString().trim();
                if (string1 == null) {
                    Toast.makeText(EspressoTestExampleActivity.this, "input number 1", Toast.LENGTH_SHORT).show();
                    return;
                }

                String string2 = editText2.getText().toString().trim();

                if (string2 == null) {
                    Toast.makeText(EspressoTestExampleActivity.this, "input number 2", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    int num1 = Integer.parseInt(string1);
                    int num2 = Integer.parseInt(string2);
                    textViewResult.setText("result：" + (num1 + num2));
                }
                catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });
        buttonWebView = (Button) findViewById(R.id.buttonWebView);
        buttonWebView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EspressoTestExampleActivity.this, EspressoTestWebViewActivity.class);
                intent.putExtra(EspressoTestWebViewActivity.EXTRA_URL, "http://m.baidu.com");
                startActivity(intent);
            }
        });
    }
}

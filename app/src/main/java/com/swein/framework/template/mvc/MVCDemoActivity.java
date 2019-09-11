package com.swein.framework.template.mvc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.swein.framework.template.mvc.bean.User;
import com.swein.framework.template.mvc.model.MVCModel;
import com.swein.shandroidtoolutils.R;

/**
 * in MVC
 * activity is the controller
 */
public class MVCDemoActivity extends AppCompatActivity {

    private TextView textView;
    private Button button;

    private MVCModel mvcModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvcdemo);

        findView();
        initMVCModel();
        setListener();

    }

    private void initMVCModel() {
        mvcModel = new MVCModel();
    }

    private void findView() {
        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);
    }

    private void setListener() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mvcModel.getUser(new MVCModel.MVCModelDelegate() {
                    @Override
                    public void onSuccess(User user) {
                        setText(user.name);
                    }

                    @Override
                    public void onFailed() {
                        setText("None");
                    }
                });
            }
        });
    }

    private void setText(String text) {
        textView.setText(text);
    }
}

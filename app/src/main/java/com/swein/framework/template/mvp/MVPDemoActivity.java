package com.swein.framework.template.mvp;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.swein.framework.template.mvp.presenter.MVPPresenter;
import com.swein.shandroidtoolutils.R;

public class MVPDemoActivity extends Activity implements MVPViewDelegate {

    private TextView textView;
    private Button button;

    private MVPPresenter mvpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvpdemo);
        findView();
        initMVPPresenter();
        setListener();

    }

    private void initMVPPresenter() {
        mvpPresenter = new MVPPresenter(this);
    }

    private void findView() {
        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);
    }

    private void setListener() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mvpPresenter.getUser();
            }
        });
    }

    @Override
    public void setText(String text) {
        textView.setText(text);
    }
}

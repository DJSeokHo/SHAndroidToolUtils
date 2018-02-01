package com.swein.pattern.observer.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.swein.framework.tools.util.activity.ActivityUtil;
import com.swein.pattern.observer.fragment.JustFragment;
import com.swein.shandroidtoolutils.R;

public class JustActivity extends FragmentActivity {


    private Button buttonOpenFragment;
    private TextView textViewContent;

    private JustFragment justFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_just);

        justFragment = new JustFragment();

        findView();
        setBehavior();
    }

    private void findView(){
        buttonOpenFragment = (Button)findViewById(R.id.buttonOpenFragment);
        textViewContent = (TextView)findViewById(R.id.textViewContent);
    }

    private void setView(String content){
        textViewContent.setText(content);
    }

    private void setBehavior(){
        buttonOpenFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtil.addFragment(JustActivity.this, R.id.containerJust, justFragment, false);
            }
        });
    }

    private void removeFragment(){
        ActivityUtil.removeFragment(this, justFragment, false);
    }

}

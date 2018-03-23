package com.swein.framework.template.mode.mvc.view.activity.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.swein.framework.template.mode.mvc.controller.SHDemoMVCController;
import com.swein.shandroidtoolutils.R;

/**
 * 处理页面布局和数据显示
 */
public class SHDemoMVCFragment extends Fragment {


    private SHDemoMVCController shDemoMVCController;

    private EditText etID;
    private EditText etPS;

    private Button btnLogin;

    private TextView tvResult;

    public SHDemoMVCFragment() {
        shDemoMVCController = new SHDemoMVCController(new SHDemoMVCController.SHDemoMVCControllerDelegate() {

            @Override
            public void onResult(String result) {
                tvResult.setText(result);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_shdemo_mvc, container, false);

        initView(rootView);

        return rootView;
    }

    private void initView(View view) {
        etID = (EditText) view.findViewById(R.id.etID);
        etPS = (EditText) view.findViewById(R.id.etPS);

        tvResult = (TextView) view.findViewById(R.id.tvResult);

        btnLogin = (Button) view.findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shDemoMVCController.login(etID.getText().toString().trim(), etPS.getText().toString().trim());
            }
        });
    }



}

package com.swein.mvp.example.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.swein.framework.tools.util.toast.ToastUtils;
import com.swein.mvp.example.model.User;
import com.swein.mvp.example.presenter.UserLoginPresenter;
import com.swein.mvp.example.view.interfaces.LoginViewInterface;
import com.swein.shandroidtoolutils.R;

/**
 * Created by seokho on 27/07/2017.
 */

public class UserLoginActivity extends Activity implements LoginViewInterface{

    private EditText editTextUserName;
    private EditText editTextUserPassword;
    private Button buttonLogin;
    private Button buttonClear;
    private ProgressBar progressBar;

    private UserLoginPresenter mUserLoginPresenter = new UserLoginPresenter(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_login);

        initViews();
    }

    private void initViews() {
        editTextUserName = (EditText) findViewById(R.id.editTextUserName);
        editTextUserPassword = (EditText) findViewById(R.id.editTextUserPassword);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonClear = (Button) findViewById(R.id.buttonClear);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserLoginPresenter.login();
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserLoginPresenter.clear();
            }
        });
    }

    @Override
    public String getUserName() {
        return editTextUserName.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        return editTextUserPassword.getText().toString().trim();
    }

    @Override
    public void clearUserName() {
        editTextUserName.setText("");
    }

    @Override
    public void clearPassword() {
        editTextUserPassword.setText("");
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void toMainActivity(User user) {
        ToastUtils.showShortToastNormal(this, user.getUserName() + " login success , to MainActivity");
    }

    @Override
    public void showFailedError() {
        ToastUtils.showShortToastNormal(this, "login failed");
    }
}

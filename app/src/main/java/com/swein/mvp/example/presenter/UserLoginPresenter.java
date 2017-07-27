package com.swein.mvp.example.presenter;

import android.os.Handler;

import com.swein.mvp.example.model.User;
import com.swein.mvp.example.model.operate.UserBehavior;
import com.swein.mvp.example.model.operate.interfaces.LoginListener;
import com.swein.mvp.example.view.interfaces.LoginViewInterface;

/**
 * Created by seokho on 27/07/2017.
 */

public class UserLoginPresenter {

    private UserBehavior userBehavior;
    private LoginViewInterface loginViewInterface;
    private Handler mHandler = new Handler();

    public UserLoginPresenter(LoginViewInterface loginViewInterface)
    {
        this.loginViewInterface = loginViewInterface;
        this.userBehavior = new UserBehavior();
    }

    public void login()
    {
        loginViewInterface.showLoading();
        userBehavior.login(loginViewInterface.getUserName(), loginViewInterface.getPassword(), new LoginListener()
        {
            @Override
            public void loginSuccess(final User user)
            {
                //需要在UI线程执行
                mHandler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        loginViewInterface.toMainActivity(user);
                        loginViewInterface.hideLoading();
                    }
                });

            }

            @Override
            public void loginFailed()
            {
                //需要在UI线程执行
                mHandler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        loginViewInterface.showFailedError();
                        loginViewInterface.hideLoading();
                    }
                });

            }
        });
    }

    public void clear()
    {
        loginViewInterface.clearUserName();
        loginViewInterface.clearPassword();
    }

}

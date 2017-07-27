package com.swein.mvp.example.model.operate;

import com.swein.mvp.example.model.User;
import com.swein.mvp.example.model.operate.interfaces.LoginListener;
import com.swein.mvp.example.model.operate.interfaces.UserBehaviorInterface;

/**
 * Created by seokho on 27/07/2017.
 */

public class UserBehavior implements UserBehaviorInterface {

    @Override
    public void login(final String userName, final String userPassword, final LoginListener loginListener) {

        new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    Thread.sleep(2000);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }

                //login success
                if (userName.equals("seokho") && userPassword.equals("seokho"))
                {
                    User user = new User();
                    user.setUserName(userName);
                    user.setUserPassword(userPassword);
                    loginListener.loginSuccess(user);
                } else
                {
                    loginListener.loginFailed();
                }
            }
        }.start();

    }
}

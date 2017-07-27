package com.swein.mvp.example.model.operate.interfaces;

import com.swein.mvp.example.model.User;

/**
 * Created by seokho on 27/07/2017.
 */

public interface LoginListener {

    void loginSuccess(User user);

    void loginFailed();

}

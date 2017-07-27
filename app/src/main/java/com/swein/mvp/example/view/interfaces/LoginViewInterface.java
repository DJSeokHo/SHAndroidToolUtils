package com.swein.mvp.example.view.interfaces;

import com.swein.mvp.example.model.User;

/**
 * Created by seokho on 27/07/2017.
 */

public interface LoginViewInterface {
    String getUserName();

    String getPassword();

    void clearUserName();

    void clearPassword();

    void showLoading();

    void hideLoading();

    void toMainActivity(User user);

    void showFailedError();
}

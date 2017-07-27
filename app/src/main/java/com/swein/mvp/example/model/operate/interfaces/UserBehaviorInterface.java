package com.swein.mvp.example.model.operate.interfaces;

/**
 * Created by seokho on 27/07/2017.
 */

public interface UserBehaviorInterface {

    void login(String userName, String userPassword, LoginListener loginListener);

}

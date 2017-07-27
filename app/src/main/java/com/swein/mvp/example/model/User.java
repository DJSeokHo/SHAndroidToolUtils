package com.swein.mvp.example.model;

/**
 * Created by seokho on 27/07/2017.
 */

public class User {

    private String userName;
    private String userPassword;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getUserPassword() {
        return this.userPassword;
    }

}

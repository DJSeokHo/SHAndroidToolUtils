package com.swein.framework.template.mvp.model;

import com.swein.framework.template.mvc.bean.User;

import java.util.Random;

public class MVPModel {

    public interface MVCModelDelegate {
        void onSuccess(User user);
        void onFailed();
    }

    public void getUser(com.swein.framework.template.mvc.model.MVCModel.MVCModelDelegate mvcModelDelegate) {

        if(new Random().nextBoolean()) {
            User user = new User();
            user.name = "user";
            mvcModelDelegate.onSuccess(user);
        }
        else {
            mvcModelDelegate.onFailed();
        }
    }

}

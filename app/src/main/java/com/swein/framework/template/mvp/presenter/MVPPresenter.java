package com.swein.framework.template.mvp.presenter;

import com.swein.framework.template.mvc.bean.User;
import com.swein.framework.template.mvc.model.MVCModel;
import com.swein.framework.template.mvp.MVPViewDelegate;
import com.swein.framework.template.mvp.model.MVPModel;

public class MVPPresenter {

    private MVPViewDelegate mvpViewDelegate;
    private MVPModel mvpModel;

    public MVPPresenter(MVPViewDelegate mvpViewDelegate) {
        this.mvpViewDelegate = mvpViewDelegate;
        initMVCModel();
    }

    private void initMVCModel() {
        mvpModel = new MVPModel();
    }

    public void getUser() {
        mvpModel.getUser(new MVCModel.MVCModelDelegate() {
            @Override
            public void onSuccess(User user) {
                mvpViewDelegate.setText(user.name);
            }

            @Override
            public void onFailed() {
                mvpViewDelegate.setText("None");
            }
        });
    }
}

package com.swein.framework.template.mode.mvc.controller;

import com.swein.framework.template.mode.mvc.model.SHDemoMVCModel;

/**
 *
 * 翻译用户的输入，操作模型和视图
 *
 * Created by seokho on 2018/3/23.
 */

public class SHDemoMVCController {

    public interface SHDemoMVCControllerDelegate {
        void onResult(String result);
    }

    private SHDemoMVCControllerDelegate shDemoMVCControllerDelegate;

    public SHDemoMVCController(SHDemoMVCControllerDelegate shDemoMVCControllerDelegate) {
        this.shDemoMVCControllerDelegate = shDemoMVCControllerDelegate;
    }

    public void login(String id, String ps) {

        SHDemoMVCModel shDemoMVCModel = new SHDemoMVCModel(new SHDemoMVCModel.SHDemoMVCModelDelegate() {
            @Override
            public void onResult(String result) {

                shDemoMVCControllerDelegate.onResult(result);
            }
        });

        shDemoMVCModel.setId(id);
        shDemoMVCModel.setPs(ps);
        shDemoMVCModel.login();
    }

}

package com.swein.framework.template.mode.mvc.model;

import org.apache.commons.lang3.StringUtils;

/**
 *
 * Model , 专门处理业务逻辑
 *
 * Created by seokho on 2018/3/23.
 */
public class SHDemoMVCModel {

    private String id;
    private String ps;
    private String result;

    private SHDemoMVCModelDelegate shDemoMVCModelDelegate;

    public interface SHDemoMVCModelDelegate {
        void onResult(String result);
    }

    public SHDemoMVCModel(SHDemoMVCModelDelegate shDemoMVCModelDelegate) {
        this.shDemoMVCModelDelegate = shDemoMVCModelDelegate;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPs(String ps) {
        this.ps = ps;
    }

    public void login() {
        if(!StringUtils.isEmpty(id) && ps.equals("123")) {
            result = "OK";
        }
        else {
            result = "ERROR";
        }

        shDemoMVCModelDelegate.onResult(result);
    }

}

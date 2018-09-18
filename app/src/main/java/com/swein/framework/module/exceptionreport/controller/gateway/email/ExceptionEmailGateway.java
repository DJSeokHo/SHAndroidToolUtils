package com.swein.framework.module.exceptionreport.controller.gateway.email;

import android.content.Context;

import com.swein.framework.module.exceptionreport.controller.gateway.ExceptionGatewayDelegate;
import com.swein.framework.module.exceptionreport.model.ExceptionModel;
import com.swein.framework.tools.util.email.EmailUtil;

public class ExceptionEmailGateway implements ExceptionGatewayDelegate {

    private final static String TAG = "ExceptionEmailGateway";

    private ExceptionModel exceptionModel;

    private Context context;

    public ExceptionEmailGateway(Context context) {
        this.context = context;
    }

    @Override
    public void setReport(ExceptionModel exceptionModel) {
        this.exceptionModel = exceptionModel;
    }

    @Override
    public void sendReport() {
        EmailUtil.mailTo(context, "djseokho@gmail.com", "app name 오류보고", exceptionModel.toReport());
    }
}

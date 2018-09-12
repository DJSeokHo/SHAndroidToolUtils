package com.swein.framework.module.exceptionreport.controller.gateway.webapi;

import com.swein.framework.module.exceptionreport.controller.gateway.ExceptionGatewayDelegate;
import com.swein.framework.module.exceptionreport.model.ExceptionModel;
import com.swein.framework.tools.util.debug.log.ILog;

public class ExceptionWebAPIGateway implements ExceptionGatewayDelegate {

    private final static String TAG = "ExceptionWebAPIGateway";

    private ExceptionModel exceptionModel;

    @Override
    public void setReport(ExceptionModel exceptionModel) {
        this.exceptionModel = exceptionModel;
    }

    @Override
    public void sendReport() {
        ILog.iLogError(TAG, exceptionModel.toReport());
    }
}

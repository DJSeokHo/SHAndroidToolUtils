package com.swein.framework.module.exceptionreport.controller.gateway;

import com.swein.framework.module.exceptionreport.model.ExceptionModel;

public interface ExceptionGatewayDelegate {

    void setReport(ExceptionModel exceptionModel);
    void sendReport();

}

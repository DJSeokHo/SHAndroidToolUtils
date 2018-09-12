package com.swein.framework.module.exceptionreport.controller;

import android.content.Context;

import com.swein.framework.module.exceptionreport.constants.EConstants;
import com.swein.framework.module.exceptionreport.controller.gateway.ExceptionGatewayDelegate;
import com.swein.framework.module.exceptionreport.controller.gateway.email.ExceptionEmailGateway;
import com.swein.framework.module.exceptionreport.controller.gateway.webapi.ExceptionWebAPIGateway;
import com.swein.framework.module.exceptionreport.model.ExceptionModel;
import com.swein.framework.tools.util.appinfo.AppInfoUtil;
import com.swein.framework.tools.util.date.DateUtil;
import com.swein.framework.tools.util.device.DeviceInfoUtil;

import java.util.Arrays;

public class ExceptionReportController {

    private final static String TAG = "ExceptionReportController";

    private ExceptionGatewayDelegate exceptionGatewayDelegate;
    private Context context;

    public ExceptionReportController(Context context, EConstants.REPORT_WAY reportWay) {

        this.context = context;

        switch (reportWay) {

            case EMAIL:
                exceptionGatewayDelegate = new ExceptionEmailGateway(this.context);
                break;

            case WEB_API:
                exceptionGatewayDelegate = new ExceptionWebAPIGateway();
                break;
        }
    }

    public void setReport(Exception e) {

        ExceptionModel exceptionModel = new ExceptionModel();

        StackTraceElement stackTraceElement = e.getStackTrace()[0];

        exceptionModel.setAppVersion(String.valueOf(AppInfoUtil.getVersionCode(context)));
        exceptionModel.setPhoneModel(DeviceInfoUtil.getDeviceModel());
        exceptionModel.setClassFileName(stackTraceElement.getFileName());
        exceptionModel.setDateTime(DateUtil.getCurrentDateTimeString());
        exceptionModel.setLineNumber(String.valueOf(stackTraceElement.getLineNumber()));
        exceptionModel.setMethodName(stackTraceElement.getMethodName());
        exceptionModel.setUserID("user01");

        exceptionModel.setExceptionMessage(Arrays.toString(e.getStackTrace()));

        exceptionGatewayDelegate.setReport(exceptionModel);

    }

    public void setReport(String classFileName, String lineNumber, String methodName, String response) {

        ExceptionModel exceptionModel = new ExceptionModel();

        exceptionModel.setAppVersion(String.valueOf(AppInfoUtil.getVersionCode(context)));
        exceptionModel.setPhoneModel(DeviceInfoUtil.getDeviceModel());
        exceptionModel.setClassFileName(classFileName);
        exceptionModel.setDateTime(DateUtil.getCurrentDateTimeString());
        exceptionModel.setLineNumber(lineNumber);
        exceptionModel.setMethodName(methodName);
        exceptionModel.setUserID("user01");

        exceptionModel.setExceptionMessage(response);

        exceptionGatewayDelegate.setReport(exceptionModel);

    }

    public void sendReport() {
        exceptionGatewayDelegate.sendReport();
    }



}

package com.swein.framework.module.appanalysisreport.data.model.dao;

import android.content.Context;

import com.swein.framework.module.appanalysisreport.data.db.deviceuser.DeviceUserDBController;
import com.swein.framework.module.appanalysisreport.data.db.exception.ExceptionDBController;
import com.swein.framework.module.appanalysisreport.data.db.operation.OperationDBController;
import com.swein.framework.module.appanalysisreport.data.model.AppAnalysisData;
import com.swein.framework.module.appanalysisreport.data.model.impl.DeviceUserData;
import com.swein.framework.module.appanalysisreport.data.model.impl.ExceptionData;
import com.swein.framework.module.appanalysisreport.data.model.impl.OperationData;

import java.util.List;

public class AppAnalysisDAO {

    private ExceptionDBController exceptionDBController;
    private OperationDBController operationDBController;
    private DeviceUserDBController deviceUserDBController;


    private AppAnalysisDAO() {}

    private static AppAnalysisDAO instance = new AppAnalysisDAO();

    public static AppAnalysisDAO getInstance() {
        return instance;
    }

    public void init(Context context) {
        exceptionDBController = new ExceptionDBController(context);
        operationDBController = new OperationDBController(context);
        deviceUserDBController = new DeviceUserDBController(context);
    }

    public void insertAppAnalysisIntoDB(AppAnalysisData appAnalysisData) {
        if(appAnalysisData instanceof ExceptionData) {
            ExceptionData exceptionData = (ExceptionData) appAnalysisData;
            exceptionDBController.insert(exceptionData);
        }
        else if(appAnalysisData instanceof OperationData) {
            OperationData operationData = (OperationData) appAnalysisData;
            operationDBController.insert(operationData);
        }
        else if(appAnalysisData instanceof DeviceUserData) {
            DeviceUserData deviceUserData = (DeviceUserData) appAnalysisData;
            deviceUserDBController.insert(deviceUserData);
        }

    }

    public String getLastOperationUUID() {
        return operationDBController.getLastOperationUUID();
    }


    public List<ExceptionData> getExceptionDataListFromDB(int offset, int limit) {
        return exceptionDBController.getData(offset, limit);
    }

    public List<OperationData> getOperationDataListFromDB(int offset, int limit) {
        return operationDBController.getData(offset, limit);
    }
}

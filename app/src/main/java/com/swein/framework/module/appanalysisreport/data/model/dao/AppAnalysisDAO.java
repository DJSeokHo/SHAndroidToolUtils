package com.swein.framework.module.appanalysisreport.data.model.dao;

import android.content.Context;

import com.swein.framework.module.appanalysisreport.data.db.exception.ExceptionDBController;
import com.swein.framework.module.appanalysisreport.data.db.operation.OperationDBController;
import com.swein.framework.module.appanalysisreport.data.model.AppAnalysisData;
import com.swein.framework.module.appanalysisreport.data.model.impl.ExceptionData;
import com.swein.framework.module.appanalysisreport.data.model.impl.OperationData;

import java.util.List;

public class AppAnalysisDAO {

    private AppAnalysisDAO() {}

    private static AppAnalysisDAO instance = new AppAnalysisDAO();

    public static AppAnalysisDAO getInstance() {
        return instance;
    }

    public void insertAppAnalysisIntoDB(Context context, AppAnalysisData appAnalysisData) {
        if(appAnalysisData instanceof ExceptionData) {
            ExceptionData exceptionData = (ExceptionData) appAnalysisData;
            ExceptionDBController exceptionDBController = new ExceptionDBController(context);
            exceptionDBController.insert(exceptionData);
        }
        else if(appAnalysisData instanceof OperationData) {
            OperationData operationData = (OperationData) appAnalysisData;
            OperationDBController operationDBController = new OperationDBController(context);
            operationDBController.insert(operationData);
        }

    }

    public List<ExceptionData> getExceptionDataListFromDB(Context context, int offset, int limit) {
        ExceptionDBController exceptionDBController = new ExceptionDBController(context);
        return exceptionDBController.getData(offset, limit);
    }

    public List<OperationData> getOperationDataListFromDB(Context context, int offset, int limit) {
        OperationDBController operationDBController = new OperationDBController(context);
        return operationDBController.getData(offset, limit);
    }
}

package com.swein.framework.module.appanalysisreport.data.model.impl;

import com.swein.framework.module.appanalysisreport.constants.AAConstants;
import com.swein.framework.module.appanalysisreport.data.model.AppAnalysisData;
import com.swein.framework.tools.util.date.DateUtil;
import com.swein.framework.tools.util.uuid.UUIDUtil;

public class OperationData implements AppAnalysisData {

    /* uuid */
    private String uuid = "";

    /* class file name */
    /* 클릭한 뷰 이름 */
    private String location = "";

    /* 시간 */
    private String dateTime = "";

    /* 이벤트 그룹 */
    private String eventGroup = "";

    private AAConstants.OPERATION_TYPE operationType = AAConstants.OPERATION_TYPE.NONE;

    /* uuid relate */
    private String exceptionRelateID = "";


    public OperationData(String location, String eventGroup, AAConstants.OPERATION_TYPE operationType, String exceptionRelateID) {
        this.uuid = UUIDUtil.getUUIDString();
        this.dateTime = DateUtil.getCurrentDateTimeString();
        this.location = location;
        this.eventGroup = eventGroup;
        this.operationType = operationType;
        this.exceptionRelateID = exceptionRelateID;
    }

    public OperationData(String uuid, String location, String dateTime, String eventGroup, AAConstants.OPERATION_TYPE operationType, String exceptionRelateID) {
        this.uuid = uuid;
        this.location = location;
        this.dateTime = dateTime;
        this.eventGroup = eventGroup;
        this.operationType = operationType;
        this.exceptionRelateID = exceptionRelateID;
    }

    public String getUuid() {
        return uuid;
    }

    public String getLocation() {
        return location;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getOperationType() {
        return getOperationTypeString(operationType);
    }

    public String getEventGroup() {
        return eventGroup;
    }

    public String getExceptionRelateID() {
        return exceptionRelateID;
    }

    @Override
    public String toString() {
        return uuid + " " + location + " " + dateTime + " " + getOperationTypeString(operationType) + " " + eventGroup + " " + exceptionRelateID;
    }

    private final static String LOCATION_KEY = "위치: ";
    private final static String DATE_TIME_KEY = "시간: ";
    private final static String OPERATION_TYPE_KEY = "행동 방식: ";
    private final static String EVENT_GROUP_KEY = "이벤트 그룹: ";
    private final static String EXCEPTION_RELATE_ID_KEY = "오류: ";

    @Override
    public String toReport() {

        return LOCATION_KEY + location + "\n" +
                DATE_TIME_KEY + dateTime + "\n" +
                OPERATION_TYPE_KEY + getOperationTypeString(operationType) + "\n" +
                EVENT_GROUP_KEY + eventGroup + "\n" +
                EXCEPTION_RELATE_ID_KEY + exceptionRelateID;

    }

    public static AAConstants.OPERATION_TYPE getOperationType(String operationTypeString) {
        switch (operationTypeString) {
            case AAConstants.OPERATION_C:
                return AAConstants.OPERATION_TYPE.C;

            case AAConstants.OPERATION_LC:
                return AAConstants.OPERATION_TYPE.LC;

            case AAConstants.OPERATION_SU:
                return AAConstants.OPERATION_TYPE.SU;

            case AAConstants.OPERATION_SD:
                return AAConstants.OPERATION_TYPE.SD;

            case AAConstants.OPERATION_NONE:

            default:
                return AAConstants.OPERATION_TYPE.NONE;
        }
    }

    private String getOperationTypeString(AAConstants.OPERATION_TYPE operationType) {
        switch (operationType) {
            case C:
                return AAConstants.OPERATION_C;

            case LC:
                return AAConstants.OPERATION_LC;

            case SD:
                return AAConstants.OPERATION_SD;

            case SU:
                return AAConstants.OPERATION_SU;

            case NONE:

            default:
                return AAConstants.OPERATION_NONE;
        }
    }
}

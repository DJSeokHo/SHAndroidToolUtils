package com.swein.framework.module.appanalysisreport.data.model.impl;

import com.swein.framework.module.appanalysisreport.reportproperty.ReportProperty;
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

    private ReportProperty.OPERATION_TYPE operationType = ReportProperty.OPERATION_TYPE.NONE;

    /* 기타정보 */
    private String note = "";

    public OperationData(String location, String eventGroup, ReportProperty.OPERATION_TYPE operationType, String note) {
        this.uuid = UUIDUtil.getUUIDString();
        this.dateTime = DateUtil.getCurrentDateTimeString();
        this.location = location;
        this.eventGroup = eventGroup;
        this.operationType = operationType;
        this.note = note;
    }

    public OperationData(String uuid, String location, String dateTime, String eventGroup, ReportProperty.OPERATION_TYPE operationType, String note) {
        this.uuid = uuid;
        this.location = location;
        this.dateTime = dateTime;
        this.eventGroup = eventGroup;
        this.operationType = operationType;
        this.note = note;
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

    public String getNote() {
        return note;
    }

    @Override
    public String toString() {
        return uuid + " " + location + " " + dateTime + " " + getOperationTypeString(operationType) + " " + eventGroup + " " + note;
    }

    private final static String LOCATION_KEY = "위치: ";
    private final static String DATE_TIME_KEY = "시간: ";
    private final static String OPERATION_TYPE_KEY = "행동 방식: ";
    private final static String EVENT_GROUP_KEY = "이벤트 그룹: ";
    private final static String NOTE_KEY = "비고: ";

    @Override
    public String toReport() {

        return LOCATION_KEY + location + "\n" +
                DATE_TIME_KEY + dateTime + "\n" +
                OPERATION_TYPE_KEY + getOperationTypeString(operationType) + "\n" +
                EVENT_GROUP_KEY + eventGroup + "\n" +
                NOTE_KEY + note;

    }

    public static ReportProperty.OPERATION_TYPE getOperationType(String operationTypeString) {
        switch (operationTypeString) {
            case ReportProperty.OPERATION_C:
                return ReportProperty.OPERATION_TYPE.C;

            case ReportProperty.OPERATION_LC:
                return ReportProperty.OPERATION_TYPE.LC;

            case ReportProperty.OPERATION_SU:
                return ReportProperty.OPERATION_TYPE.SU;

            case ReportProperty.OPERATION_SD:
                return ReportProperty.OPERATION_TYPE.SD;

            case ReportProperty.OPERATION_NONE:

            default:
                return ReportProperty.OPERATION_TYPE.NONE;
        }
    }

    private String getOperationTypeString(ReportProperty.OPERATION_TYPE operationType) {
        switch (operationType) {
            case C:
                return ReportProperty.OPERATION_C;

            case LC:
                return ReportProperty.OPERATION_LC;

            case SD:
                return ReportProperty.OPERATION_SD;

            case SU:
                return ReportProperty.OPERATION_SU;

            case NONE:

            default:
                return ReportProperty.OPERATION_NONE;
        }
    }
}

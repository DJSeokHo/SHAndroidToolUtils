package com.swein.framework.module.appanalysisreport.data.model.impl;

import com.swein.framework.module.appanalysisreport.constants.AAConstants;
import com.swein.framework.module.appanalysisreport.data.model.AppAnalysisData;

public class OperationData implements AppAnalysisData {

    /* uuid */
    private String uuid = "";

    /* user ID */
    private String userID = "";

    /* class file name */
    private String classFileName = "";

    /* 시간 */
    private String dateTime = "";

    /* 클릭한 뷰 이름 */
    private String viewUIName = "";

    /* 이벤트 그룹 */
    private String eventGroup = "";

    private AAConstants.OPERATION_TYPE operationType = AAConstants.OPERATION_TYPE.NONE;

    public static class Builder {

        /* uuid */
        private String uuid = "";

        /* user ID */
        private String userID = "";

        /* class file name */
        private String classFileName = "";

        /* 시간 */
        private String dateTime = "";

        /* 클릭한 뷰 이름 */
        private String viewUIName = "";

        /* 이벤트 그룹 */
        private String eventGroup = "";

        private AAConstants.OPERATION_TYPE operationType = AAConstants.OPERATION_TYPE.NONE;

        public Builder setUuid(String uuid) {
            this.uuid = uuid;
            return this;
        }

        public Builder setUserID(String userID) {
            this.userID = userID;
            return this;
        }

        public Builder setClassFileName(String classFileName) {
            this.classFileName = classFileName;
            return this;
        }

        public Builder setDateTime(String dateTime) {
            this.dateTime = dateTime;
            return this;
        }

        public Builder setViewUIName(String viewUIName) {
            this.viewUIName = viewUIName;
            return this;
        }

        public Builder setOperationType(AAConstants.OPERATION_TYPE operationType) {
            this.operationType = operationType;
            return this;
        }

        public Builder setEventGroup(String eventGroup) {
            this.eventGroup = eventGroup;
            return this;
        }

        public OperationData build() {
            return new OperationData(this);
        }
    }

    private OperationData(Builder builder) {
        this.uuid = builder.uuid;
        this.userID = builder.userID;
        this.classFileName = builder.classFileName;
        this.dateTime = builder.dateTime;
        this.viewUIName = builder.viewUIName;
        this.operationType = builder.operationType;
        this.eventGroup = builder.eventGroup;
    }

    public String getUuid() {
        return uuid;
    }

    public String getUserID() {
        return userID;
    }

    public String getClassFileName() {
        return classFileName;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getViewUIName() {
        return viewUIName;
    }

    public String getOperationType() {
        return getOperationTypeString(operationType);
    }

    public String getEventGroup() {
        return eventGroup;
    }

    @Override
    public String toString() {
        return uuid + " " + userID + " " + classFileName + " " + dateTime + " " + viewUIName + " " + getOperationTypeString(operationType) + " " + eventGroup;
    }

    private final static String USER_ID_KEY = "사용자 계정: ";
    private final static String CLASS_FILE_KEY = "화면 파일: ";
    private final static String VIEW_UI_NAME_KEY = "터치한 위치: ";
    private final static String DATE_TIME_KEY = "시간: ";
    private final static String OPERATION_TYPE_KEY = "행동 방식: ";
    private final static String EVENT_GROUP_KEY = "이벤트 그룹: ";

    @Override
    public String toReport() {

        return USER_ID_KEY + userID + "\n" +
                CLASS_FILE_KEY + classFileName + "\n" +
                DATE_TIME_KEY + dateTime + "\n" +
                VIEW_UI_NAME_KEY + viewUIName + "\n" +
                OPERATION_TYPE_KEY + getOperationTypeString(operationType) + "\n" +
                EVENT_GROUP_KEY + eventGroup;

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

package com.swein.framework.module.appanalysisreport.data.model.impl;

import com.swein.framework.module.appanalysisreport.data.model.AppAnalysisData;

public class ExceptionData implements AppAnalysisData {

    private String uuid = "";

    /* 사용자 ID */
    private String userID = "";

    /* 시간 */
    private String dateTime = "";

    /* 오류 발생하는 파일명 */
    private String classFileName = "";

    /* 오류 발생하는 메소드명 */
    private String methodName = "";

    /* 오류 발생하는 line 번호 */
    private String lineNumber = "";

    /* 오류 메시지 */
    private String exceptionMessage = "";

    private String eventGroup = "";

    public static class Builder {

        private String uuid = "";

        /* 사용자 ID */
        private String userID = "";

        /* 시간 */
        private String dateTime = "";

        /* 오류 발생하는 파일명 */
        private String classFileName = "";

        /* 오류 발생하는 메소드명 */
        private String methodName = "";

        /* 오류 발생하는 line 번호 */
        private String lineNumber = "";

        /* 오류 메시지 */
        private String exceptionMessage = "";

        private String eventGroup = "";

        public Builder setUuid(String uuid) {
            this.uuid = uuid;
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

        public Builder setExceptionMessage(String exceptionMessage) {
            this.exceptionMessage = exceptionMessage;
            return this;
        }

        public Builder setLineNumber(String lineNumber) {
            this.lineNumber = lineNumber;
            return this;
        }

        public Builder setMethodName(String methodName) {
            this.methodName = methodName;
            return this;
        }

        public Builder setUserID(String userID) {
            this.userID = userID;
            return this;
        }

        public Builder setEventGroup(String eventGroup) {
            this.eventGroup = eventGroup;
            return this;
        }

        public ExceptionData build() {
            return new ExceptionData(this);
        }
    }

    private ExceptionData(Builder builder) {
        this.uuid = builder.uuid;
        this.userID = builder.userID;
        this.dateTime = builder.dateTime;
        this.classFileName = builder.classFileName;
        this.methodName = builder.methodName;
        this.lineNumber = builder.lineNumber;
        this.exceptionMessage = builder.exceptionMessage;
        this.eventGroup = builder.eventGroup;
    }

    public String getClassFileName() {
        return classFileName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getUserID() {
        return userID;
    }

    public String getEventGroup() {
        return eventGroup;
    }

    @Override
    public String toString() {
        return uuid + " " + userID + " " + dateTime + " " + classFileName + " " + methodName + " " + lineNumber + " " + exceptionMessage + " " + eventGroup;
    }

    @Override
    public String toReport() {

        return USER_ID_KEY + userID + "\n" +
                DATE_TIME_KEY + dateTime + "\n" +
                CLASS_FILE_NAME_KEY + classFileName + "\n" +
                METHOD_NAME_KEY + methodName + "\n" +
                LINE_NUMBER_KEY + lineNumber + "\n" +
                MESSAGE_KEY + exceptionMessage + "\n" +
                EVENT_GROUP_KEY + eventGroup;
    }

    private final static String USER_ID_KEY = "사용자 계정: ";
    private final static String DATE_TIME_KEY = "오류 발생 일시: ";
    private final static String CLASS_FILE_NAME_KEY = "오류 발생한 파일: ";
    private final static String METHOD_NAME_KEY = "오류 발생한 메소드: ";
    private final static String LINE_NUMBER_KEY = "오류 발생한 라인: ";
    private final static String MESSAGE_KEY = "오류 발생한 정보: ";
    private final static String EVENT_GROUP_KEY = "이벤트 그룹: ";

}

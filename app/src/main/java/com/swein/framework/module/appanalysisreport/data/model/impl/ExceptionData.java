package com.swein.framework.module.appanalysisreport.data.model.impl;

import com.swein.framework.module.appanalysisreport.data.model.AppAnalysisData;
import com.swein.framework.tools.util.date.DateUtil;
import com.swein.framework.tools.util.uuid.UUIDUtil;

public class ExceptionData implements AppAnalysisData {

    private String uuid = "";

    /* 시간 */
    private String dateTime = "";

    /* 오류 발생하는 파일명 */
    /* 오류 발생하는 메소드명 */
    /* 오류 발생하는 line 번호 */
    private String location = "";

    /* 오류 메시지 */
    private String exceptionMessage = "";

    private String eventGroup = "";

    /* 기타정보 */
    private String note = "";

    public ExceptionData(String location, String exceptionMessage, String eventGroup, String note) {
        this.uuid = UUIDUtil.getUUIDString();
        this.dateTime = DateUtil.getCurrentDateTimeString();
        this.location = location;
        this.exceptionMessage = exceptionMessage;
        this.eventGroup = eventGroup;
        this.note = note;
    }

    public ExceptionData(String uuid, String dateTime, String location, String exceptionMessage, String eventGroup, String note) {
        this.uuid = uuid;
        this.dateTime = dateTime;
        this.location = location;
        this.exceptionMessage = exceptionMessage;
        this.eventGroup = eventGroup;
        this.note = note;
    }

    public String getLocation() {
        return location;
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

    public String getEventGroup() {
        return eventGroup;
    }

    public String getNote() {
        return note;
    }

    @Override
    public String toString() {
        return uuid + " " + dateTime + " " + location + " " + exceptionMessage + " " + eventGroup + " " + note;
    }

    @Override
    public String toReport() {

        return DATE_TIME_KEY + dateTime + "\n" +
                LOCATION_KEY + location + "\n" +
                MESSAGE_KEY + exceptionMessage + "\n" +
                NOTE_KEY + note + "\n" +
                EVENT_GROUP_KEY + eventGroup;
    }

    private final static String DATE_TIME_KEY = "오류 발생 일시: ";
    private final static String LOCATION_KEY = "오류 발생한 위치: ";
    private final static String MESSAGE_KEY = "오류 발생한 정보: ";
    private final static String EVENT_GROUP_KEY = "이벤트 그룹: ";
    private final static String NOTE_KEY = "비고: ";

}

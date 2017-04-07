package com.swein.framework.module.googleanalytics.aop.report.event;

/**
 * Created by seokho on 03/04/2017.
 */

public class EventReport {

    public static String createEventReport(String methodName, String message) {
        StringBuilder result = new StringBuilder();
        result.append("Aspect --> ");
        result.append(methodName);
        result.append(" --> ");
        result.append("[");
        result.append(message);
        result.append("]");

        return result.toString();
    }

}

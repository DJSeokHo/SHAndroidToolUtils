package com.swein.framework.module.aspect.tracker.analytics.report.exception;

/**
 * Created by seokho on 03/04/2017.
 */

public class ExceptionReport {

    public static String createExceptionReport(String methodName, String message) {
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

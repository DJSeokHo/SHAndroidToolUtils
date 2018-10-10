package com.swein.framework.module.appanalysisreport.data.parser;

public class StackTraceParser {

    public static String getClassFileNameFromThrowable(Throwable throwable) {
        return throwable.getStackTrace()[0].getFileName();
    }

    public static String getLineNumberFromThrowable(Throwable throwable) {
        return String.valueOf(throwable.getStackTrace()[0].getLineNumber());
    }

    public static String getMethodNameFromThrowable(Throwable throwable) {
        return throwable.getStackTrace()[0].getMethodName();
    }

}

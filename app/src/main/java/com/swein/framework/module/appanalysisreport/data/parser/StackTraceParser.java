package com.swein.framework.module.appanalysisreport.data.parser;

public class StackTraceParser {

    public static String getLocationFromThrowable(Throwable throwable) {
        String location = "";
        location += "[ " + throwable.getStackTrace()[0].getFileName() + " ]\n";
        location += "[ " + throwable.getStackTrace()[0].getMethodName() + " ]\n";
        location += "[ " + throwable.getStackTrace()[0].getLineNumber() + " ]";
        return location;
    }
}

package com.swein.framework.module.appanalysisreport.data.parser;

public class ReportParser {

    public static String getLocationFromThrowable(Throwable throwable) {
        String location = "";
        location += "[ " + throwable.getStackTrace()[0].getFileName() + " ]\n";
        location += "[ " + throwable.getStackTrace()[0].getMethodName() + " ]\n";
        location += "[ " + throwable.getStackTrace()[0].getLineNumber() + " ]";
        return location;
    }

    public static String getExceptionMessage(Throwable exception) {
        StringBuilder exceptionMessage = new StringBuilder();

        Throwable temp = exception.getCause();

        if(temp == null) {
            /*
                try catch in thread
                exception.getCause() can be null
             */
            temp = exception;
        }

        StackTraceElement[] stackTraceElements = temp.getStackTrace();
        exceptionMessage.append(temp.getMessage()).append("\n");
        for(StackTraceElement stackTraceElement : stackTraceElements) {
            exceptionMessage.append(stackTraceElement.toString()).append("\n");
        }

        return exceptionMessage.toString();
    }
}

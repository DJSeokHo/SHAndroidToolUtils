package com.swein.framework.module.excelfile.manager;

/**
 * Created by seokho on 28/04/2017.
 */

public class ExcelManager {

    private ExcelManager() {}

    private static ExcelManager instance = new ExcelManager();

    public static ExcelManager getInstance() {
        return instance;
    }

    private String filePath, fileName;



}

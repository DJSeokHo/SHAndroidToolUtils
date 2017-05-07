package com.swein.framework.module.excelfile.data;

/**
 * Created by seokho on 04/05/2017.
 */

public class LabelContent {

    public ExcelCell cell;
    public String labelContent;

    public LabelContent(ExcelCell cell, String labelContent) {
        this.cell = cell;
        this.labelContent = labelContent;
    }

}

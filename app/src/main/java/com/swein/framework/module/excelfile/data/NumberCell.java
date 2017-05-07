package com.swein.framework.module.excelfile.data;

/**
 * Created by seokho on 04/05/2017.
 */

public class NumberCell {

    public ExcelCell cell;
    public double numberContent;

    public NumberCell(ExcelCell cell, double numberContent) {
        this.cell = cell;
        this.numberContent = numberContent;
    }

}

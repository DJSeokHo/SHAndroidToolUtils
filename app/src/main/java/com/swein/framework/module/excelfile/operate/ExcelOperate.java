package com.swein.framework.module.excelfile.operate;

import com.swein.framework.module.excelfile.data.ExcelCell;
import com.swein.framework.module.excelfile.data.LabelContent;
import com.swein.framework.module.excelfile.data.NumberCell;
import com.swein.framework.tools.util.debug.log.ILog;

import java.io.File;
import java.io.IOException;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * Created by seokho on 28/04/2017.
 */

public class ExcelOperate {

    final private static String TAG = "ExcelOperate";

    public static File getFile(String filePath, String fileName) {
        return new File(filePath + fileName + ".xls");
    }

    /**
     *
     * @param file
     * @param sheetName
     * @param sheetPage: 0 means first page
     * @return
     * @throws IOException
     */
    public static WritableSheet createSheet(File file, String sheetName, int sheetPage) throws IOException {
        WritableWorkbook book = Workbook.createWorkbook(file);
        WritableSheet sheet = book.createSheet(sheetName, sheetPage);
        return sheet;
    }

    public static void setSheet(WritableSheet sheet, int row, int rowHeight, int col, int colWidth) throws RowsExceededException {
        sheet.setRowView(row, rowHeight);
        sheet.setColumnView(col, colWidth);
    }

    public static void setWritableCellFormatCentre(WritableCellFormat format) throws WriteException {
        format.setAlignment(Alignment.CENTRE);
        format.setVerticalAlignment(VerticalAlignment.CENTRE);
    }

    public static ExcelCell createExcelCell(int row, int col) {
        return new ExcelCell(row, col);
    }


    public static Label createLabel(LabelContent labelContent){
        return new Label(labelContent.cell.row, labelContent.cell.col, labelContent.labelContent);
    }

    public static jxl.write.Number createNumber(NumberCell numberCell) {
        return new jxl.write.Number(numberCell.cell.row, numberCell.cell.col, numberCell.numberContent);
    }

    public static void addLabelIntoSheet(WritableSheet sheet, Label label) throws WriteException {
        sheet.addCell(label);
    }

    public static void addNumberCellIntoSheet(WritableSheet sheet, jxl.write.Number number) throws WriteException {
        sheet.addCell(number);
    }

    public static void addLabelListIntoSheet(WritableSheet sheet, List<Label> labelList) throws WriteException {
        for(Label label : labelList) {
            sheet.addCell(label);
        }
    }

    public static void addNumberListIntoSheet(WritableSheet sheet, List<jxl.write.Number> numberList) throws WriteException {
        for(jxl.write.Number number : numberList) {
            sheet.addCell(number);
        }
    }

    private static void readExcelCell(File file, int sheetPage, ExcelCell excelCell) throws BiffException, IOException {
        Workbook workbook = Workbook.getWorkbook(file);
        Sheet sheet = workbook.getSheet(sheetPage);
        Cell cell = sheet.getCell(excelCell.row, excelCell.col);
        String body = cell.getContents();

        ILog.iLogDebug(TAG, body);

        workbook.close();
    }

    private void updateExcelCell(File file, int sheetPage, LabelContent labelContent) throws IOException, BiffException, WriteException {

        Workbook workbook = Workbook.getWorkbook(file);
        WritableWorkbook writableWorkbook = Workbook.createWorkbook(file, workbook);

        WritableSheet sheet = writableWorkbook.getSheet(sheetPage);
        sheet.addCell(new Label(labelContent.cell.row, labelContent.cell.col, labelContent.labelContent));

        writableWorkbook.close();
        workbook.close();
    }

}

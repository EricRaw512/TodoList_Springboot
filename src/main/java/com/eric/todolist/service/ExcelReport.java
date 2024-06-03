package com.eric.todolist.service;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;


@Service
public class ExcelReport {

    protected XSSFWorkbook workbook;
    protected XSSFSheet sheet;

    public void newReportExcel() {
        workbook = new XSSFWorkbook();
    }

    public void writeTableHeaderExcel(String sheetName, String titleName, String[] headers) {

        sheet = workbook.createSheet(sheetName);
        org.apache.poi.ss.usermodel.Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(12);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);


        createCell(row, 0, titleName, style);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, headers.length - 1));
        font.setFontHeightInPoints((short) 10);

        row = sheet.createRow(1);
        font.setBold(true);
        font.setFontHeight(12);
        style.setFont(font);
        for (int i = 0; i < headers.length; i++) {
            createCell(row, i, headers[i], style);
        }
        
    }

    public void createCell(org.apache.poi.ss.usermodel.Row row, int columnCount, Object value, CellStyle style) {
        org.apache.poi.ss.usermodel.Cell cell = row.createCell(columnCount);
        if (value instanceof Integer integerValue) {
            cell.setCellValue(integerValue);
        } else if (value instanceof Double doubleValue) {
            cell.setCellValue(doubleValue);
        } else if (value instanceof Boolean booleanValue) {
            cell.setCellValue(booleanValue);
        } else if (value instanceof Long longValue) {
            cell.setCellValue(longValue);
        } else {
            cell.setCellValue((String) value);
        }
        
        cell.setCellStyle(style);
        sheet.autoSizeColumn(columnCount);
    }

    public CellStyle getFontContentExcel() {
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(12);
        style.setFont(font);
        return style;
    }
}

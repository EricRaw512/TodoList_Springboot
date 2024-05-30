package com.eric.todolist.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;

import com.eric.todolist.dto.CheckListDTO;

@Service
public class ChecklistToExcelService extends ExcelReport{

    public void writeTableData(List<CheckListDTO> data) {
        CellStyle style = getFontContentExcel();
        int startRow = 2;

        for (CheckListDTO checkListDTO : data) {
            Row row = sheet.createRow(startRow++);
            int columnCount = 0;
            createCell(row, columnCount++, checkListDTO.getId(), style);
            createCell(row, columnCount++, checkListDTO.getName(), style);
        }
    }

    public byte[] exportToExcel(List<CheckListDTO> data) throws IOException {
        newReportExcel();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        String[] headers = new String[]{"ID", "Name"};
        writeTableHeaderExcel("Sheet Checklist", "Report Checklist", headers);
        writeTableData(data);
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();

        return outputStream.toByteArray();
    }
}

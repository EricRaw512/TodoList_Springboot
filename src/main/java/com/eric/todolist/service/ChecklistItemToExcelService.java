package com.eric.todolist.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;

import com.eric.todolist.dto.ChecklistItemDTO;


@Service
public class ChecklistItemToExcelService extends ExcelReport{
    
    public void writeTableData(List<ChecklistItemDTO> data) {
        CellStyle style = getFontContentExcel();
        int startRow = 2;

        for (ChecklistItemDTO checkListItemDTO : data) {
            Row row = sheet.createRow(startRow++);
            int columnCount = 0;
            createCell(row, columnCount++, checkListItemDTO.getId(), style);
            createCell(row, columnCount++, checkListItemDTO.getItemName(), style);
            createCell(row, columnCount, checkListItemDTO.isCompleted(), style);
        }
    }

    public byte[] exportToExcel(List<ChecklistItemDTO> data) throws IOException {
        newReportExcel();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        String[] headers = new String[]{"ID", "Name", "Status"};
        writeTableHeaderExcel("Sheet Checklist Item", "Report Checklist Item", headers);
        writeTableData(data);
        
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();

        return outputStream.toByteArray();
    }
}
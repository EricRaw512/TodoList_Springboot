package com.eric.todolist.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.eric.todolist.dto.CheckListDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChecklistReportService {

    private final ChecklistToExcelService checklistToExcelService;

    public byte[] exportToExcel(List<CheckListDTO> checklists) throws IOException {
        return checklistToExcelService.exportToExcel(checklists);
    }
}

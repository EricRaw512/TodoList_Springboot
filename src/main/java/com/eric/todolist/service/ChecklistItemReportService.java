package com.eric.todolist.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.eric.todolist.dto.ChecklistItemDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChecklistItemReportService {
    
    private final ChecklistItemToExcelService checklistItemToExcelService;

    public byte[] exportToExcel(List<ChecklistItemDTO> checklistsItem) throws IOException {
        return checklistItemToExcelService.exportToExcel(checklistsItem);
    }
}

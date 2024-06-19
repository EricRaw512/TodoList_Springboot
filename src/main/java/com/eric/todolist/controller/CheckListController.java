package com.eric.todolist.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eric.todolist.dto.CheckListDTO;
import com.eric.todolist.exception.ChecklistException;
import com.eric.todolist.mappingstrategy.CustomMappingStrategy;
import com.eric.todolist.security.UserDetail;
import com.eric.todolist.service.CSVService;
import com.eric.todolist.service.ChecklistReportService;
import com.eric.todolist.service.ChecklistService;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/checklist")
public class CheckListController {
    
    private final ChecklistService checklistService;
    private final ChecklistReportService checklistReportService;
    private final CSVService csvService;

    @GetMapping
    public ResponseEntity<List<CheckListDTO>> getAllCheckLists(@AuthenticationPrincipal UserDetail user) {
        List<CheckListDTO> checklists = checklistService.getAllChecklistsByUsername(user);
        return ResponseEntity.ok(checklists);
    }

    @PostMapping
    public ResponseEntity<CheckListDTO> createChecklist(@Valid @RequestBody CheckListDTO checklistDTO, @AuthenticationPrincipal UserDetail user) {
        checklistService.createChecklist(checklistDTO.getName(), user);
        return ResponseEntity.ok(checklistDTO);
    }

    @DeleteMapping("/{checklistId}")
    public ResponseEntity<Void> deleteChecklist(@PathVariable int checklistId, @AuthenticationPrincipal UserDetail user) throws ChecklistException{
        checklistService.deleteChecklist(checklistId, user);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/export/csv")
    public void exportCSV(HttpServletResponse response, @AuthenticationPrincipal UserDetail user) throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, IOException {
    	String filename = "Checklist-list.csv";
    	response.setContentType("text/csv; charset=UTF-8");
    	CustomMappingStrategy<CheckListDTO> mappingStrategy = new CustomMappingStrategy<>();
    	mappingStrategy.setType(CheckListDTO.class);
    	response.setHeader(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"%s\"", filename));
    	csvService.beanToCsv(response.getWriter(), checklistService.getAllChecklistsByUsername(user), mappingStrategy);
    }

    //test

    @GetMapping("/export/excel")
    public ResponseEntity<byte[]> exportExcel(@AuthenticationPrincipal UserDetail user) throws IOException {
        String filename = "Checklist-list.xlsx";
        List<CheckListDTO> checklists = checklistService.getAllChecklistsByUsername(user);
        byte[] excelReport = checklistReportService.exportToExcel(checklists);
        return ResponseEntity.ok()
    		.contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
            .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s", filename))
            .body(excelReport);
    }
}

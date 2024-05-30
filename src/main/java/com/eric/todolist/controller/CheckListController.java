package com.eric.todolist.controller;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.eric.todolist.dto.CheckListDTO;
import com.eric.todolist.exception.ChecklistException;
import com.eric.todolist.security.UserDetail;
import com.eric.todolist.service.ChecklistReportService;
import com.eric.todolist.service.ChecklistService;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/checklist")
public class CheckListController {
    
    private final ChecklistService checklistService;
    private final ChecklistReportService checklistReportService;

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
    public ResponseEntity<Void> deleteChecklist(@PathVariable int checklistId, @AuthenticationPrincipal UserDetail user) {
        try {
            checklistService.deleteChecklist(checklistId, user);
        } catch (ChecklistException e ) {
            throw e;
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/export/csv")
    public ResponseEntity<StreamingResponseBody> exportCSV(@AuthenticationPrincipal UserDetail user) {
        String filename = "Checklist-list.csv";
        StreamingResponseBody stream = outputStream -> {
            List<CheckListDTO> checklists = checklistService.getAllChecklistsByUsername(user);
            try (Writer writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)) {
               new StatefulBeanToCsvBuilder<CheckListDTO>(writer)
                                .build().write(checklists);
            } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
            } 
        };

        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType("test/csv; charset=UTF-8"))
            .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s", filename))
            .header(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, HttpHeaders.CONTENT_DISPOSITION)
            .body(stream);
    }

    @GetMapping("/export/excel")
    public ResponseEntity<byte[]> exportExcel(@AuthenticationPrincipal UserDetail user) throws IOException {
        String filename = "Checklist-list.xls";
        List<CheckListDTO> checklists = checklistService.getAllChecklistsByUsername(user);
        byte[] excelReport = checklistReportService.exportToExcel(checklists);
        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s", filename))
            .body(excelReport);
    }
}

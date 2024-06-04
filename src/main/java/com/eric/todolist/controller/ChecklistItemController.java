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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eric.todolist.dto.ChecklistItemDTO;
import com.eric.todolist.mappingstrategy.CustomMappingStrategy;
import com.eric.todolist.security.UserDetail;
import com.eric.todolist.service.CSVService;
import com.eric.todolist.service.ChecklistItemReportService;
import com.eric.todolist.service.ChecklistItemService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/checklist/{checklistId}/item")
public class ChecklistItemController {
    
    private final ChecklistItemService checklistItemService;
    private final ChecklistItemReportService checklistItemReportService;
    private final CSVService csvService;
    
    @GetMapping
    public ResponseEntity<List<ChecklistItemDTO>> getAllChecklistItems(@PathVariable int checklistId) {
        List<ChecklistItemDTO> checklistItems = checklistItemService.getAllCheckListItems(checklistId);
        return ResponseEntity.ok(checklistItems);
    }

    @PostMapping
    public ResponseEntity<ChecklistItemDTO> createChecklistItem(@PathVariable int checklistId, @Valid @RequestBody ChecklistItemDTO checklistItemDTO, @AuthenticationPrincipal UserDetail user) {
        try {
            ChecklistItemDTO newChecklistItem = checklistItemService.createChecklistItem(checklistId, checklistItemDTO.getItemName(), user);
            return ResponseEntity.ok(newChecklistItem);
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping("/{checklistItemId}")
    public ResponseEntity<ChecklistItemDTO> getChecklistItem(@PathVariable("checklistId") int checklistId, @PathVariable("checklistItemId") int checklistItemId, @AuthenticationPrincipal UserDetail user) {
        try {
            ChecklistItemDTO checklistItem = checklistItemService.findChecklist(checklistId, checklistItemId, user);
            return ResponseEntity.ok(checklistItem);
        } catch (Exception e) {
            throw e;
        }
    }

    @PutMapping("/{checklistItemId}")
    public ResponseEntity<ChecklistItemDTO> updateChecklistItemStatus(@PathVariable("checklistId") int checklistId, @PathVariable("checklistItemId") int checklistItemId, @AuthenticationPrincipal UserDetail user) {
        ChecklistItemDTO checklistItem = checklistItemService.updateCheckListItemStatus(checklistId, checklistItemId, user);
        return ResponseEntity.ok(checklistItem);
    }

    @DeleteMapping("/{checklistItemId}")
    public ResponseEntity<Void> deleteChecklistItem(@PathVariable("checklistId") int checklistId, @PathVariable("checklistItemId") int checklistItemId, @AuthenticationPrincipal UserDetail user) {
        try {
            checklistItemService.deleteCheckListItem(checklistId, checklistItemId, user);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw e;
        }
    }

    @PutMapping("/rename/{checklistItemId}")
    public ResponseEntity<ChecklistItemDTO> renameChecklistItem(@PathVariable("checklistId") int checklistId, @PathVariable("checklistItemId") int checklistItemId, 
                            @Valid @RequestBody ChecklistItemDTO checklistItemDTO, @AuthenticationPrincipal UserDetail user) {
        try {
            ChecklistItemDTO updatedChecklistItem = checklistItemService.updateCheckListItem(checklistId, checklistItemId, checklistItemDTO.getItemName(), user);
            return ResponseEntity.ok(updatedChecklistItem);
        } catch (Exception e) {
            throw e;
        }
    }
    
    @GetMapping("/export/csv")
    public void exportCSV(HttpServletResponse response, @PathVariable int checklistId) throws Exception {
    	String filename = "ChecklistItem-list.csv";
    	response.setContentType("text/csv; charset=UTF-8");
    	CustomMappingStrategy<ChecklistItemDTO> mappingStrategy = new CustomMappingStrategy<>();
    	mappingStrategy.setType(ChecklistItemDTO.class);
    	response.setHeader(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"%s\"", filename));
    	csvService.beanToCsv(response.getWriter(), checklistItemService.getAllCheckListItems(checklistId), mappingStrategy);
    }

    @GetMapping("/export/excel")
    public ResponseEntity<byte[]> exportExcel(@PathVariable int checklistId) throws IOException {
        String filename = "ChecklistItem-list.xls";
        List<ChecklistItemDTO> checklistItems = checklistItemService.getAllCheckListItems(checklistId);
        byte[] excelReport = checklistItemReportService.exportToExcel(checklistItems);
        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s", filename))
            .body(excelReport);
    }
}
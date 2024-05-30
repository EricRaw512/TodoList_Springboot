package com.eric.todolist.controller;

import java.util.List;

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
import com.eric.todolist.security.UserDetail;
import com.eric.todolist.service.ChecklistService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/checklist")
public class CheckListController {
    
    private final ChecklistService checklistService;

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
}

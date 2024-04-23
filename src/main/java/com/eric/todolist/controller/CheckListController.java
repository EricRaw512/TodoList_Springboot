package com.eric.todolist.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eric.todolist.dto.CheckListDTO;
import com.eric.todolist.entity.Checklist;
import com.eric.todolist.service.ChecklistService;
import com.eric.todolist.service.JwtService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/checklist")
public class CheckListController {
    
    private final ChecklistService checklistService;
    private final JwtService jwtService;

    @GetMapping
    public ResponseEntity<List<Checklist>> getAllCheckLists(@RequestHeader("Authorization") String authorization) {
        String username = jwtService.extractUsername(authorization.substring(7));
        if (username == null) {
            return ResponseEntity.badRequest().build();
        }

        List<Checklist> checklists = checklistService.getAllChecklistsByUsername(username);
        if (checklists == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(checklists);
    }

    @PostMapping
    public ResponseEntity<Checklist> createChecklist(@RequestHeader("Authorization") String authorization, @RequestBody CheckListDTO checklistDTO) {
        String username = jwtService.extractUsername(authorization.substring(7));
        if (username == null) {
            return ResponseEntity.badRequest().build();
        }

        Checklist newChecklist = checklistService.createChecklist(checklistDTO.getName(), username);
        return ResponseEntity.ok(newChecklist);
    }

    @DeleteMapping("/{checklistId}")
    public ResponseEntity<Void> deleteChecklist(@RequestHeader("Authorization") String authorization, @PathVariable int checklistId) {
        String username = jwtService.extractUsername(authorization.substring(7));
        if (username == null) {
            return ResponseEntity.badRequest().build();
        }

        checklistService.deleteChecklist(checklistId, username);
        return ResponseEntity.ok().build();
    }
}

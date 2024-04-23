package com.eric.todolist.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eric.todolist.model.Checklist;
import com.eric.todolist.service.ChecklistService;
import com.eric.todolist.service.JwtService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/checklist")
public class CheckListController {
    
    private ChecklistService checklistService;
    private JwtService jwtService;

    @GetMapping
    public ResponseEntity<List<Checklist>> getAllCheckLists(@RequestHeader("Authorization") String authorization) {
        String username = jwtService.extractUsername(authorization);
        if (username == null) {
            return ResponseEntity.badRequest().build();
        }

        List<Checklist> checklists = checklistService.getAllChecklistsByUsername(username);
        if (checklists == null) {
            return ResponseEntity.badRequest().build();
        }
        
        return ResponseEntity.ok(checklists);
    }
}

package com.eric.todolist.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eric.todolist.model.ChecklistItem;
import com.eric.todolist.service.ChecklistItemService;
import com.eric.todolist.service.JwtService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/checklist/{checklistId}/item")
public class ChecklistItemController {
    
    private ChecklistItemService checklistItemService;
    private JwtService jwtService;
    
    @GetMapping
    public ResponseEntity<List<ChecklistItem>> getAllChecklistItems(@RequestHeader("Authorization") String authorization, @PathVariable int checklistId) {
        String username = jwtService.extractUsername(authorization);
        if (username == null) {
            return ResponseEntity.badRequest().build();
        }

        List<ChecklistItem> checklistItems = checklistItemService.getAllCheckListItems(checklistId, username);
        if (checklistItems == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(checklistItems);
    }

    @PostMapping
    public ResponseEntity<ChecklistItem> createChecklistItem(@RequestHeader("Authorization") String authorization, @PathVariable int checklistId, @RequestBody ChecklistItem checklistItem) {
        String username = jwtService.extractUsername(authorization);
        if (username == null) {
            return ResponseEntity.badRequest().build();
        }

        ChecklistItem newChecklistItem = checklistItemService.createChecklistItem(checklistId, checklistItem, username);
        return ResponseEntity.ok(newChecklistItem);
    }

    @GetMapping("/{checklistItemId}")
    public ResponseEntity<ChecklistItem> getChecklistItem(@RequestHeader("Authorization") String authorization, @PathVariable("checklistId") int checklistId, @PathVariable("checklistItemId") int checklistItemId) {
        String username = jwtService.extractUsername(authorization);
        if (username == null) {
            return ResponseEntity.badRequest().build();
        }

        ChecklistItem checklistItem = checklistItemService.getCheckListItem(checklistId, checklistItemId, username);
        if (checklistItem == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(checklistItem);
    }

    @PutMapping("/{checklistItemId}")
    public ResponseEntity<ChecklistItem> updateChecklistItemStatus(@RequestHeader("Authorization") String authorization, @PathVariable("checklistId") int checklistId, @PathVariable("checklistItemId") int checklistItemId) {
        String username = jwtService.extractUsername(authorization);
        if (username == null) {
            return ResponseEntity.badRequest().build();
        }

        ChecklistItem checklistItem = checklistItemService.updateCheckListItem(checklistId, checklistItemId, username);
        if (checklistItem == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(checklistItem);
    }

    @DeleteMapping("/{checklistItemId}")
    public ResponseEntity<Void> deleteChecklistItem(@RequestHeader("Authorization") String authorization, @PathVariable("checklistId") int checklistId, @PathVariable("checklistItemId") int checklistItemId) {
        String username = jwtService.extractUsername(authorization);
        if (username == null) {
            return ResponseEntity.badRequest().build();
        }

        ChecklistItem checklistItem = checklistItemService.deleteCheckListItem(checklistId, checklistItemId, username);
        if (checklistItem == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }

    @PutMapping("/rename/{checklistItemId}")
    public ResponseEntity<ChecklistItem> renameChecklistItem(@RequestHeader("Authorization") String authorization, @PathVariable("checklistId") int checklistId,
                        @PathVariable("checklistItemId") int checklistItemId, @RequestBody ChecklistItem checklistItem) {
        String username = jwtService.extractUsername(authorization);
        if (username == null) {
            return ResponseEntity.badRequest().build();
        }
        
        ChecklistItem updatedChecklistItem = checklistItemService.updateCheckListItem(checklistId, checklistItemId, checklistItem, username);
        if (updatedChecklistItem == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(updatedChecklistItem);
    }
}
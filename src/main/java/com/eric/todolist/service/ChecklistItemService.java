package com.eric.todolist.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.eric.todolist.dao.ChecklistItemRepository;
import com.eric.todolist.model.ChecklistItem;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChecklistItemService {
    
    private ChecklistItemRepository checklistItemRepository;

    public List<ChecklistItem> getAllCheckListItems(int checklistId, String username) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllCheckListItems'");
    }

    public ChecklistItem createChecklistItem(int checklistId, ChecklistItem checklistItem, String username) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createChecklistItem'");
    }

    public ChecklistItem getCheckListItem(int checklistId, int checklistItemId, String username) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCheckListItem'");
    }

    public ChecklistItem updateCheckListItem(int checklistId, int checklistItemId, String username) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateCheckListItem'");
    }

    public ChecklistItem deleteCheckListItem(int checklistId, int checklistItemId, String username) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteCheckListItem'");
    }

    public ChecklistItem updateCheckListItem(int checklistId, int checklistItemId, ChecklistItem checklistItem,
            String username) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateCheckListItem'");
    }

}

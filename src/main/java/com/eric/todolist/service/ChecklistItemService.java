package com.eric.todolist.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.eric.todolist.dao.ChecklistItemRepository;
import com.eric.todolist.dao.ChecklistRepository;
import com.eric.todolist.dao.UserRepository;
import com.eric.todolist.entity.Checklist;
import com.eric.todolist.entity.ChecklistItem;
import com.eric.todolist.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChecklistItemService {
    
    private final UserRepository userRepository;
    private final ChecklistRepository checklistRepository;
    private final ChecklistItemRepository checklistItemRepository;

    public List<ChecklistItem> getAllCheckListItems(int checklistId, String username) {
        if (!checkUserAndChecklistAuth(username, checklistId)) {
            return null;
        }

        return checklistItemRepository.findAllByChecklistId(checklistId);
    }

    public ChecklistItem createChecklistItem(int checklistId, String checklistItemName, String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        Optional<Checklist> checklistOptional = checklistRepository.findById(checklistId);
        if (!checkUserAndChecklistAuth(user, checklistOptional)) {
            return null;
        }

        ChecklistItem checklistItem = new ChecklistItem();
        checklistItem.setItemName(checklistItemName);
        checklistItem.setCompleted(false);
        checklistItem.setChecklist(checklistOptional.get());
        return checklistItemRepository.save(checklistItem);
    }

    public ChecklistItem getCheckListItem(int checklistId, int checklistItemId, String username) {
        if (!checkUserAndChecklistAuth(username, checklistId)) {
            return null;
        }
    
        return checklistItemRepository.findById(checklistItemId).orElse(null);
    }

    public ChecklistItem updateCheckListItemStatus(int checklistId, int checklistItemId, String username) {
        ChecklistItem checklistItem = getCheckListItem(checklistId, checklistItemId, username);
        if (checklistItem == null) {
            return null;
        }

        checklistItem.setCompleted(!checklistItem.isCompleted());
        return checklistItemRepository.save(checklistItem);
    }

    public boolean deleteCheckListItem(int checklistId, int checklistItemId, String username) {
        ChecklistItem checklistItem = getCheckListItem(checklistId, checklistItemId, username);
        if (checklistItem == null) {
            return false;
        }

        checklistItemRepository.deleteById(checklistItemId);
        return checklistItemRepository.findById(checklistItemId).isEmpty();
    }

    public ChecklistItem updateCheckListItem(int checklistId, int checklistItemId, String newItemName, String username) {
        ChecklistItem currentChecklistItem = getCheckListItem(checklistId, checklistItemId, username);
        if (currentChecklistItem == null) {
            return null;
        }

        currentChecklistItem.setItemName(newItemName);
        return checklistItemRepository.save(currentChecklistItem);
    }


    private boolean checkUserAndChecklistAuth(User user, Optional<Checklist> checklistOptional) {
        if (user == null) {
            return false;
        }

        if (checklistOptional.isPresent()) {
            Checklist checklist = checklistOptional.get();
            if (checklist.getUser().equals(user)) {
                return true;
            }
        }

        return false;
    }

    private boolean checkUserAndChecklistAuth(String username, int checklistId) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            return false;
        }

        Optional<Checklist> checklistOptional = checklistRepository.findById(checklistId);
        if (checklistOptional.isPresent()) {
            Checklist checklist = checklistOptional.get();
            if (checklist.getUser().equals(user)) {
                return true;
            }
        } 

        return false;
    }
}

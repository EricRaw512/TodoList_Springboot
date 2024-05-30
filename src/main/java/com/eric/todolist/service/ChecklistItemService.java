package com.eric.todolist.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.eric.todolist.dao.ChecklistItemRepository;
import com.eric.todolist.dao.ChecklistRepository;
import com.eric.todolist.dto.ChecklistItemDTO;
import com.eric.todolist.entity.Checklist;
import com.eric.todolist.entity.ChecklistItem;
import com.eric.todolist.exception.ChecklistException;
import com.eric.todolist.security.UserDetail;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChecklistItemService {
    
    private final ChecklistRepository checklistRepository;
    private final ChecklistItemRepository checklistItemRepository;

    public List<ChecklistItemDTO> getAllCheckListItems(int checklistId) {
        return checklistItemRepository.findAllByChecklistId(checklistId).stream()
                .map(checklistItem -> convertToDto(checklistItem))
                .collect(Collectors.toList());
    }

    public ChecklistItemDTO createChecklistItem(int checklistId, String checklistItemName, UserDetail user) {
        Optional<Checklist> checklistOptional = checklistRepository.findById(checklistId);
        if (!checkUserAndChecklistAuth(user, checklistOptional)) {
            throw new ChecklistException("Checklist doesn't match with the user");
        }

        ChecklistItem checklistItem = new ChecklistItem();
        checklistItem.setItemName(checklistItemName);
        checklistItem.setCompleted(false);
        checklistItem.setChecklist(checklistOptional.get());
        checklistItemRepository.save(checklistItem);
        return convertToDto(checklistItem);
    }

    public ChecklistItemDTO FindChecklist(int checklistId, int checklistItemId, UserDetail user) {
        try {
            ChecklistItem checklistItem = getCheckListItem(checklistId, checklistItemId, user);
            return convertToDto(checklistItem);
        } catch (Exception e) {
            throw e;
        }
    }

    public ChecklistItem getCheckListItem(int checklistId, int checklistItemId, UserDetail user) {
        Optional<Checklist> checklistOptional = checklistRepository.findById(checklistId);
        if (!checkUserAndChecklistAuth(user, checklistOptional)) {
            throw new ChecklistException("Checklist doesn't match with the user");
        }
        
        Optional<ChecklistItem> checklistItem= checklistItemRepository.findById(checklistItemId);
        if (!checklistItem.isPresent()) {
            throw new ChecklistException("Checklist item id " + checklistItemId + " not found");
        }

        return checklistItem.get();
    }

    public ChecklistItemDTO updateCheckListItemStatus(int checklistId, int checklistItemId, UserDetail user) {
        try {
            ChecklistItem checklistItem = getCheckListItem(checklistId, checklistItemId, user);
            checklistItem.setCompleted(!checklistItem.isCompleted());
            checklistItemRepository.save(checklistItem);
            return convertToDto(checklistItem);
        } catch (Exception e) {
            throw e;
        }
    }

    public void deleteCheckListItem(int checklistId, int checklistItemId, UserDetail user) {
        try {
            ChecklistItem checklistItem = getCheckListItem(checklistId, checklistItemId, user);
            checklistItemRepository.deleteById(checklistItem.getId());
        } catch (Exception e) {
            throw e;
        }
    }

    public ChecklistItemDTO updateCheckListItem(int checklistId, int checklistItemId, String newItemName, UserDetail user) {
        try {
            ChecklistItem currentChecklistItem = getCheckListItem(checklistId, checklistItemId, user);
            currentChecklistItem.setItemName(newItemName);
            checklistItemRepository.save(currentChecklistItem);
            return convertToDto(currentChecklistItem);
        } catch (Exception e) {
            throw e;
        }
    }


    private boolean checkUserAndChecklistAuth(UserDetail user, Optional<Checklist> checklistOptional) {
        if (checklistOptional.isPresent()) {
            Checklist checklist = checklistOptional.get();
            if (checklist.getUser().getUsername().equals(user.getUsername())) {
                return true;
            }
        }

        return false;
    }

    private ChecklistItemDTO convertToDto(ChecklistItem checklistItems) {
        return new ChecklistItemDTO(checklistItems.getId(), checklistItems.isCompleted(), checklistItems.getItemName());
    }
}

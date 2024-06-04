package com.eric.todolist.service;

import java.util.List;
import java.util.Optional;

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
                .map(this::convertToDto)
                .toList();
    }

    public ChecklistItemDTO createChecklistItem(int checklistId, String checklistItemName, UserDetail user) throws ChecklistException {
        Optional<Checklist> checklistOptional = checklistRepository.findById(checklistId);
        checkUserAndChecklistAuth(user, checklistOptional, checklistId);

        ChecklistItem checklistItem = new ChecklistItem();
        checklistItem.setItemName(checklistItemName);
        checklistItem.setCompleted(false);
        checklistItem.setChecklist(checklistOptional.get());
        checklistItemRepository.save(checklistItem);
        return convertToDto(checklistItem);
    }

    public ChecklistItemDTO findChecklist(int checklistId, int checklistItemId, UserDetail user) throws ChecklistException {
        ChecklistItem checklistItem = getCheckListItem(checklistId, checklistItemId, user);
        return convertToDto(checklistItem);
    }

    public ChecklistItem getCheckListItem(int checklistId, int checklistItemId, UserDetail user) throws ChecklistException {
        Optional<Checklist> checklistOptional = checklistRepository.findById(checklistId);
        checkUserAndChecklistAuth(user, checklistOptional, checklistId);
        
        Optional<ChecklistItem> checklistItem= checklistItemRepository.findById(checklistItemId);
        if (!checklistItem.isPresent()) {
            throw new ChecklistException("Checklist item id " + checklistItemId + " not found");
        }

        return checklistItem.get();
    }

    public ChecklistItemDTO updateCheckListItemStatus(int checklistId, int checklistItemId, UserDetail user) throws ChecklistException {
        ChecklistItem checklistItem = getCheckListItem(checklistId, checklistItemId, user);
        checklistItem.setCompleted(!checklistItem.isCompleted());
        checklistItemRepository.save(checklistItem);
        return convertToDto(checklistItem);
    }

    public void deleteCheckListItem(int checklistId, int checklistItemId, UserDetail user) throws ChecklistException {
        ChecklistItem checklistItem = getCheckListItem(checklistId, checklistItemId, user);
        checklistItemRepository.deleteById(checklistItem.getId());
    }

    public ChecklistItemDTO updateCheckListItem(int checklistId, int checklistItemId, String newItemName, UserDetail user) throws ChecklistException {
        ChecklistItem currentChecklistItem = getCheckListItem(checklistId, checklistItemId, user);
        currentChecklistItem.setItemName(newItemName);
        checklistItemRepository.save(currentChecklistItem);
        return convertToDto(currentChecklistItem);
    }


    private void checkUserAndChecklistAuth(UserDetail user, Optional<Checklist> checklistOptional, int checklistId) {
        if (!checklistOptional.isPresent()) {
            throw new ChecklistException("Cannot find the checklist with id " + checklistId);
        }

        Checklist checklist = checklistOptional.get();
        if (!checklist.getUser().getUsername().equals(user.getUsername())) {
            throw new ChecklistException("Checklist doesn't match with the user");
        }
    }

    private ChecklistItemDTO convertToDto(ChecklistItem checklistItems) {
        return new ChecklistItemDTO(checklistItems.getId(), checklistItems.getItemName(), checklistItems.isCompleted());
    }
}

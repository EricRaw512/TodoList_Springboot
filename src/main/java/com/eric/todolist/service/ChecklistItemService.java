package com.eric.todolist.service;

import com.eric.todolist.config.security.UserDetail;
import com.eric.todolist.exception.GlobalException;
import com.eric.todolist.model.dto.request.ChecklistItemFilterRequest;
import com.eric.todolist.model.entity.Checklist;
import com.eric.todolist.model.entity.ChecklistItem;
import com.eric.todolist.repository.ChecklistItemRepository;
import com.eric.todolist.specification.ChecklistItemSpecification;
import com.eric.todolist.util.constant.GlobalMessage;
import com.eric.todolist.util.enums.StatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChecklistItemService {

    private final ChecklistService checklistService;
    private final ChecklistItemRepository checklistItemRepository;
    private final ChecklistItemSpecification checklistItemSpecification;

    public Page<ChecklistItem> getAllCheckListItems(int checklistId, Pageable sanitizedPage, ChecklistItemFilterRequest request) {
        Specification<ChecklistItem> spec = checklistItemSpecification.findByChecklistId(checklistId)
                .and(checklistItemSpecification.searchChecklistItem(request));
        return checklistItemRepository.findAll(spec, sanitizedPage);
    }

    public ChecklistItem createChecklistItem(int checklistId, String checklistItemName, UserDetail user) {
        Checklist checklist = checkIfChecklistExist(checklistId);
        checkUserAndChecklistAuth(user, checklist);
        ChecklistItem checklistItem = new ChecklistItem();
        checklistItem.setItemName(checklistItemName);
        checklistItem.setCompleted(false);
        checklistItem.setChecklist(checklist);
        return checklistItemRepository.save(checklistItem);
    }

    public ChecklistItem getCheckListItem(int checklistId, int checklistItemId, UserDetail user) {
        Checklist checkList = checkIfChecklistExist(checklistId);
        checkUserAndChecklistAuth(user, checkList);
        Optional<ChecklistItem> checklistItem = checklistItemRepository.findById(checklistItemId);
        if (checklistItem.isEmpty()) {
            throw new GlobalException(StatusCode.BAD_REQUEST, String.format(GlobalMessage.ExceptionMessage.CHECKLIST_ITEM_NOT_FOUND, checklistItemId));
        }

        return checklistItem.get();
    }

    public ChecklistItem updateCheckListItemStatus(int checklistId, int checklistItemId, UserDetail user) {
        ChecklistItem checklistItem = getCheckListItem(checklistId, checklistItemId, user);
        checklistItem.setCompleted(!checklistItem.isCompleted());
        return checklistItemRepository.save(checklistItem);
    }

    public void deleteCheckListItem(int checklistId, int checklistItemId, UserDetail user) {
        ChecklistItem checklistItem = getCheckListItem(checklistId, checklistItemId, user);
        checklistItemRepository.delete(checklistItem);
    }

    public ChecklistItem updateCheckListItem(int checklistId, int checklistItemId, String newItemName, UserDetail user) {
        ChecklistItem currentChecklistItem = getCheckListItem(checklistId, checklistItemId, user);
        currentChecklistItem.setItemName(newItemName);
        return checklistItemRepository.save(currentChecklistItem);
    }

    private Checklist checkIfChecklistExist(int checklistId) {
        Optional<Checklist> checklistOptional = checklistService.findById(checklistId);
        if (checklistOptional.isEmpty()) {
            throw new GlobalException(StatusCode.BAD_REQUEST, String.format(GlobalMessage.ExceptionMessage.CHECKLIST_NOT_FOUND, checklistId));
        }

        return checklistOptional.get();
    }

    private void checkUserAndChecklistAuth(UserDetail user, Checklist checklist) {
        if (!checklist.getUsers().getUsername().equals(user.getUsername())) {
            throw new GlobalException(StatusCode.BAD_REQUEST, GlobalMessage.ExceptionMessage.CHECKLIST_NOT_MATCH_WITH_THE_USER);
        }
    }
}

package com.eric.todolist.service;

import com.eric.todolist.config.security.UserDetail;
import com.eric.todolist.exception.GlobalException;
import com.eric.todolist.model.dto.request.ChecklistSearchRequest;
import com.eric.todolist.model.entity.Checklist;
import com.eric.todolist.model.entity.Users;
import com.eric.todolist.repository.ChecklistRepository;
import com.eric.todolist.repository.UserRepository;
import com.eric.todolist.specification.ChecklistSpecification;
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
public class ChecklistService {

    private final ChecklistRepository checklistRepository;
    private final ChecklistSpecification checklistSpecification;
    private final UserRepository userRepository;

    public Page<Checklist> getAllChecklistsByUsername(UserDetail user, Pageable sanitizedPage, ChecklistSearchRequest request) {
        Specification<Checklist> spec = checklistSpecification.searchUser(user.getId())
                .and(checklistSpecification.searchName(request.getSearch()));
        return checklistRepository.findAll(spec, sanitizedPage);
    }

    public void createChecklist(String checklistName, UserDetail userDetail) {
        Optional<Users> user = userRepository.findById(userDetail.getId());
        if (user.isEmpty()) {
            throw new GlobalException(StatusCode.INTERNAL_SERVER_ERROR, GlobalMessage.ExceptionMessage.USER_NOT_FOUND);
        }

        Checklist checklist = new Checklist();
        checklist.setName(checklistName);
        checklist.setUsers(user.get());
        checklistRepository.save(checklist);
    }

    public void deleteChecklist(int checklistId, UserDetail user) {
        Optional<Checklist> checklistOptional = checklistRepository.findById(checklistId);
        if (checklistOptional.isEmpty()) {
            throw new GlobalException(StatusCode.BAD_REQUEST, String.format(GlobalMessage.ExceptionMessage.CHECKLIST_NOT_FOUND, checklistId));
        }

        Checklist checklist = checklistOptional.get();
        if (!checklist.getUsers().getUsername().equals(user.getUsername())) {
            throw new GlobalException(StatusCode.BAD_REQUEST, GlobalMessage.ExceptionMessage.USER_NOT_MATCH);
        }

        checklistRepository.delete(checklist);
    }

    public Optional<Checklist> findById(int checklistId) {
        return checklistRepository.findById(checklistId);
    }
}

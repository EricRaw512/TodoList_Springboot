package com.eric.todolist.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.eric.todolist.dao.ChecklistRepository;
import com.eric.todolist.dao.UserRepository;
import com.eric.todolist.dto.CheckListDTO;
import com.eric.todolist.entity.Checklist;
import com.eric.todolist.entity.User;
import com.eric.todolist.exception.ChecklistException;
import com.eric.todolist.security.UserDetail;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChecklistService {
    
    private final ChecklistRepository checklistRepository;
    private final UserRepository userRepository;

    public List<CheckListDTO> getAllChecklistsByUsername(UserDetail user) {
        return convertToDto(checklistRepository.findAllByUserId(user.getId()));
    }

    public void createChecklist(String checklistName, UserDetail userDetail) {
        Optional<User> user = userRepository.findById(userDetail.getId());
        Checklist checklist = new Checklist();
        checklist.setName(checklistName);
        checklist.setUser(user.get());
        checklistRepository.save(checklist);
    }

    public void deleteChecklist(int checklistId, UserDetail user) {
        Optional<Checklist> checklistOptional = checklistRepository.findById(checklistId);
        if (!checklistOptional.isPresent()) {
            throw new ChecklistException("Cannot fimd checklist with id " + checklistId);
        }
        
        Checklist checklist = checklistOptional.get();
        if (!checklist.getUser().getUsername().equals(user.getUsername())) {
            throw new ChecklistException("User doesn't match with Checklist user");
        }

        checklistRepository.delete(checklist);
    }
    
    private List<CheckListDTO> convertToDto(List<Checklist> allByUserId) {
        return allByUserId.stream()
            .map(checklist -> new CheckListDTO(checklist.getId(), checklist.getName()))
            .toList();
    }
}

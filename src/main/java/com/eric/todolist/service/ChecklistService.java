package com.eric.todolist.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.eric.todolist.dao.ChecklistRepository;
import com.eric.todolist.dto.CheckListDTO;
import com.eric.todolist.entity.Checklist;
import com.eric.todolist.entity.User;
import com.eric.todolist.exception.ChecklistException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChecklistService {
    
    private final ChecklistRepository checklistRepository;

    public List<CheckListDTO> getAllChecklistsByUsername(User user) {
        return convertToDto(checklistRepository.findAllByUserId(user.getId()));
    }

    public void createChecklist(String checklistName, User user) {
        Checklist checklist = new Checklist();
        checklist.setName(checklistName);
        checklist.setUser(user);
        checklistRepository.save(checklist);
    }

    public void deleteChecklist(int checklistId, User user) {
        Optional<Checklist> checklistOptional = checklistRepository.findById(checklistId);
        if (!checklistOptional.isPresent()) {
            throw new ChecklistException("Checklist id " + checklistId + " not found");
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
            .collect(Collectors.toList());
    }
}

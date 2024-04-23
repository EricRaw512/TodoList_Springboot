package com.eric.todolist.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.eric.todolist.dao.ChecklistRepository;
import com.eric.todolist.dao.UserRepository;
import com.eric.todolist.entity.Checklist;
import com.eric.todolist.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChecklistService {
    
    private final UserRepository userRepository;
    private final ChecklistRepository checklistRepository;

    public List<Checklist> getAllChecklistsByUsername(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            return null;
        }

        return checklistRepository.findAllByUserId(user.getId());
    }

    public Checklist createChecklist(String checklistName, String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            return null;
        }
        
        Checklist checklist = new Checklist();
        checklist.setName(checklistName);
        checklist.setUser(user);
        return checklistRepository.save(checklist);
    }

    public void deleteChecklist(int checklistId, String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            return;
            //throw new NameNotFoundException();
        }

        Optional<Checklist> checklistOptional = checklistRepository.findById(checklistId);
        if (checklistOptional.isPresent()) {
            Checklist checklist = checklistOptional.get();
            if (checklist.getUser().equals(user)) {
                checklistRepository.delete(checklist);
            } else {
                return;
                //throw new UnauthorizedAccessException();
            }
        } else {
            return;
            //throw new NotFoundException();
        }
    }
    

}

package com.eric.todolist.service;

import java.util.List;
import java.util.Optional;

import javax.naming.NameNotFoundException;

import org.springframework.stereotype.Service;

import com.eric.todolist.dao.ChecklistRepository;
import com.eric.todolist.dao.UserRepository;
import com.eric.todolist.model.Checklist;
import com.eric.todolist.model.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChecklistService {
    
    private UserRepository userRepository;
    private ChecklistRepository checklistRepository;

    public List<Checklist> getAllChecklistsByUsername(String username) {
        User user = userRepository.findByUserName(username).orElse(null);
        if (user == null) {
            return null;
        }

        return checklistRepository.findAllByUserId(user.getId());
    }

    public Checklist createChecklist(Checklist checklist, String username) {
        User user = userRepository.findByUserName(username).orElse(null);
        if (user == null) {
            return null;
        }
        
        checklist.setUser(user);
        return checklistRepository.save(checklist);
    }

    public void deleteChecklist(int checklistId, String username) {
        User user = userRepository.findByUserName(username).orElse(null);
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

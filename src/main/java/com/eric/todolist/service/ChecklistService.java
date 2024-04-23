package com.eric.todolist.service;

import java.util.List;

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
    

}

package com.eric.todolist.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.eric.todolist.dao.UserRepository;
import com.eric.todolist.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User loadUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public User authenticateUser(String username, String password) {
        User user = loadUserByUsername(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        
        return null;
    }

    public void registerUser(String username, String password) {
        User newUser = new User();
        newUser.setUsername(username);
        String encodedPassword = passwordEncoder.encode(password);
        newUser.setPassword(encodedPassword);
        userRepository.save(newUser);
    }
    
}

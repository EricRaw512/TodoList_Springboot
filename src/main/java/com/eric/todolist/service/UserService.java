package com.eric.todolist.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.eric.todolist.dao.UserRepository;
import com.eric.todolist.model.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService{

    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public User loadUserByUsername(String username) {
        return userRepository.findByUserName(username).orElse(null);
    }

    public User authenticateUser(String username, String password) {
        User user = loadUserByUsername(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        
        return null;
        //throw new UnauthorizedException("Invalid Username or password");
    }

    public void registerUser(String username, String password) {
        //throw badRequestException if user is already exist
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        userRepository.save(newUser);
    }
    
}

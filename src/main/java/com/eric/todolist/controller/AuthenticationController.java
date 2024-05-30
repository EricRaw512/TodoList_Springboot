package com.eric.todolist.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eric.todolist.dto.JwtResponseDto;
import com.eric.todolist.dto.LoginDto;
import com.eric.todolist.dto.RegisterDto;
import com.eric.todolist.entity.User;
import com.eric.todolist.exception.UserException;
import com.eric.todolist.exception.UsernameOrPasswordException;
import com.eric.todolist.service.JwtService;
import com.eric.todolist.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AuthenticationController {
    
    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> login(@Valid @RequestBody LoginDto loginRequest) {
        try {
            User user = userService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());        
            String jwt = jwtService.generateToken(user);
            return ResponseEntity.ok(new JwtResponseDto(jwt));
        } catch (UsernameOrPasswordException e) {
            throw e;
        }
    }

    @PostMapping("register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterDto registerRequest) {
        if (userService.loadUserByUsername(registerRequest.getUsername()).isPresent()) {
            throw new UserException("Username Already Exist");
        }

        userService.registerUser(registerRequest.getUsername(), registerRequest.getPassword());
        return ResponseEntity.ok().body("User Created");
    }
}

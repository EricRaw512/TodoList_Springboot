package com.eric.todolist.controller;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eric.todolist.dto.JwtResponseDto;
import com.eric.todolist.dto.UserDto;
import com.eric.todolist.exception.UserException;
import com.eric.todolist.service.JwtService;
import com.eric.todolist.service.UserService;
import com.eric.todolist.validator.groups.CreateUser;
import com.eric.todolist.validator.groups.LoginUser;

import io.jsonwebtoken.security.InvalidKeyException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AuthenticationController {
    
    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> login(@Validated(LoginUser.class) @RequestBody UserDto userDto) throws InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException {
        userService.authenticateUser(userDto.getUsername(), userDto.getPassword());        
        String jwt = jwtService.generateToken(userDto);
        return ResponseEntity.ok(new JwtResponseDto(jwt));
    }

    @PostMapping("register")
    public ResponseEntity<String> register(@Validated(CreateUser.class) @RequestBody UserDto userDto) {
        if (userService.loadUserByUsername(userDto.getUsername()).isPresent()) {
            throw new UserException("Username Already Exist");
        }

        userService.registerUser(userDto.getUsername(), userDto.getPassword());
        return ResponseEntity.ok().body("User Created");
    }
}	

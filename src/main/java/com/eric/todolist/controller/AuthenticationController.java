package com.eric.todolist.controller;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eric.todolist.dto.JwtResponse;
import com.eric.todolist.dto.UserLoginRequest;
import com.eric.todolist.dto.UserRegistrationRequest;
import com.eric.todolist.model.User;
import com.eric.todolist.service.JwtService;
import com.eric.todolist.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AuthenticationController {
    
    private UserService userService;
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody UserLoginRequest loginRequest) {
        User user = userService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());
        //Sementara Kalau Bisa buat UnauthorizedException
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        
        String jwt = jwtService.generateToken(user);
        return ResponseEntity.ok(new JwtResponse(jwt));
    }

    @PostMapping("register")
    public ResponseEntity<Void> register(@RequestBody UserRegistrationRequest registerRequest) {
        if (userService.loadUserByUsername(registerRequest.getUsername()) != null) {
            //Sementara, Kalau bisa Buat return Response bahwa User uda ada
            return ResponseEntity.badRequest().build();
        }

        userService.registerUser(registerRequest.getUsername(), registerRequest.getPassword());
        return ResponseEntity.ok().build();
    }
}

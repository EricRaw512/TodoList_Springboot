package com.eric.todolist.controller;

import java.util.List;

// import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eric.todolist.dto.UserDto;
import com.eric.todolist.exception.UserException;
import com.eric.todolist.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    
    private final UserService userService;

    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/{userId}")
    public UserDto updateUserUsername(@PathVariable("userId") int userid, @Valid @RequestBody UserDto userDto) {
        try {
            return userService.updateUserUsername(userid, userDto);
        } catch (UserException e) {
            throw e;
        }
    }
}

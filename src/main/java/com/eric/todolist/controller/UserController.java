package com.eric.todolist.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eric.todolist.dto.UserDto;
import com.eric.todolist.security.UserDetail;
import com.eric.todolist.service.UserService;
import com.eric.todolist.validator.groups.UpdateUser;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    
    private final UserService userService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PreAuthorize("#userid == principal.id or hasRole('ROLE_ADMIN')")
    @PutMapping("/update/{userId}")
    public ResponseEntity<Void> updateUserPassword(@PathVariable("userId") int userid, @Validated(UpdateUser.class) @RequestBody UserDto userDto, @AuthenticationPrincipal UserDetail user) {
        try {
        	userService.updateUserPassword(userid, userDto, user);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw e;
        }
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{userId}")
    public ResponseEntity<Void> updateUserStatus(@PathVariable("userId") int userId) {
    	userService.updateUserStatus(userId);
    	return ResponseEntity.ok().build();
    }
}

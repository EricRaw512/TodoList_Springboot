package com.eric.todolist.controller;

import com.eric.todolist.mapper.GlobalResponseTransform;
import com.eric.todolist.mapper.UserTransform;
import com.eric.todolist.model.dto.request.UserRequest;
import com.eric.todolist.model.entity.Users;
import com.eric.todolist.service.UserService;
import com.eric.todolist.util.PageableUtil;
import com.eric.todolist.util.constant.UsersSortingConstant;
import com.eric.todolist.util.enums.StatusCode;
import com.eric.todolist.util.transform.PageTransform;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final GlobalResponseTransform globalResponseTransform;
    private final UserTransform userTransform;
    private final PageTransform usersPageTransform;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getAllUsers(Pageable pageable) {
        Pageable sanitizedPage = PageableUtil.convertAndFilterSort(pageable, UsersSortingConstant.USERS_SORTING_FIELD);
        Page<Users> usersPage = userService.getAllUsers(sanitizedPage);
        return ResponseEntity.ok(globalResponseTransform.generateResponse(
                LocalDateTime.now(),
                StatusCode.OK,
                usersPageTransform.toPage(
                        usersPage.getNumber(),
                        usersPage.getTotalElements(),
                        usersPage.getPageable().getOffset(),
                        usersPage.getSize(),
                        usersPage.getTotalPages(),
                        userTransform.toUserResponseList(usersPage.getContent())
                )
        ));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(
            value = "/{userId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> updateUserUsername(@PathVariable("userId") int userid, @Valid @RequestBody UserRequest request) {
        Users updatedUser = userService.updateUserUsername(userid, request);
        return ResponseEntity.ok(globalResponseTransform.generateResponse(
                LocalDateTime.now(),
                StatusCode.OK,
                userTransform.toUserResponse(updatedUser)
        ));
    }
}

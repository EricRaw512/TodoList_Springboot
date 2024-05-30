package com.eric.todolist.dto;


import com.eric.todolist.entity.Role;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private int id;

    @NotBlank(message = "username cannot be Empty")
    private String username;

    private Role role;
}

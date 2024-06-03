package com.eric.todolist.dto;


import com.eric.todolist.entity.Role;
import com.eric.todolist.validator.groups.CreateUser;
import com.eric.todolist.validator.groups.LoginUser;
import com.eric.todolist.validator.groups.UpdateUser;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private int id;

    @NotBlank(message = "username cannot be Empty", groups = {CreateUser.class, LoginUser.class})
    private String username;
    
    @NotBlank(message = "Old password cannot be Empty", groups = UpdateUser.class)
    private String oldPassword;
    
    @NotBlank(message = "Password cannot be Empty", groups = {UpdateUser.class, CreateUser.class, LoginUser.class})
    @Size(min = 5, max = 20, groups = {UpdateUser.class, CreateUser.class})
    private String password;

    private Role role;
    
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean enabled;
}

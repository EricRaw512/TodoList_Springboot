package com.eric.todolist.model.dto.request;

import com.eric.todolist.model.enums.Role;
import com.eric.todolist.util.constant.GlobalMessage;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequest {

    private int id;

    @NotBlank(message = GlobalMessage.ExceptionMessage.USERNAME_BLANK)
    private String username;
    private Role role;
}

package com.eric.todolist.model.dto.request;

import com.eric.todolist.util.constant.GlobalMessage;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRequest {
    
    @NotBlank(message = GlobalMessage.ExceptionMessage.USERNAME_BLANK)
    private String username;

    @NotBlank(message = GlobalMessage.ExceptionMessage.PASSWORD_BLANK)
    private String password;
}

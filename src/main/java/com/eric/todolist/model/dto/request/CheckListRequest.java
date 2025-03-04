package com.eric.todolist.model.dto.request;

import com.eric.todolist.util.constant.GlobalMessage;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckListRequest {

    @NotBlank(message = GlobalMessage.ExceptionMessage.NAME_BLANK)
    private String name;
}


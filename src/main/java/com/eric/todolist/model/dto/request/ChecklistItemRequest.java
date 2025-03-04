package com.eric.todolist.model.dto.request;

import com.eric.todolist.util.constant.GlobalMessage;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ChecklistItemRequest {

    @NotBlank(message = GlobalMessage.ExceptionMessage.NAME_BLANK)
    private String itemName;
}

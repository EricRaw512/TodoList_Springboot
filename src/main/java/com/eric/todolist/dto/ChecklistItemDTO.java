package com.eric.todolist.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChecklistItemDTO {
    
    private int id;

    private boolean completed;
    
    @NotBlank(message = "item Name should not be blank")
    private String itemName;
}

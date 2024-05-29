package com.eric.todolist.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckListDTO {
    private int id;

    @NotBlank(message = "name should not be blank")
    private String name;
}


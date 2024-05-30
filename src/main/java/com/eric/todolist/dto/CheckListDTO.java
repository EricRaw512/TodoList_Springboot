package com.eric.todolist.dto;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckListDTO {

    @CsvBindByName(column = "Id")
    @CsvBindByPosition(position = 0)
    private int id;

    @CsvBindByName(column = "Name")
    @CsvBindByPosition(position = 1)
    @NotBlank(message = "name should not be blank")
    private String name;
}


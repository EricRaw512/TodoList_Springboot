package com.eric.todolist.dto;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChecklistItemDTO { 

    @CsvBindByName(column = "Id")
    @CsvBindByPosition(position = 0)
    private int id;
    
    @CsvBindByName(column = "Item Name")
    @CsvBindByPosition(position = 1)
    @NotBlank(message = "item Name should not be blank")
    private String itemName;

    @CsvBindByName(column = "Completed")
    @CsvBindByPosition(position = 2)
    private boolean completed;
}

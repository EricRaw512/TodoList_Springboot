package com.eric.todolist.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Checklist {
    
    private int id;
    private String itemName;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}

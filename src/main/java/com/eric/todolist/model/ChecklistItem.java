package com.eric.todolist.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChecklistItem {
    
    private int id;
    private String itemName;
    private boolean completed;

    @ManyToOne
    @JoinColumn(name = "checklist_id", nullable = false)
    private Checklist checklist;
}

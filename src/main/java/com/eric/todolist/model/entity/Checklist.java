package com.eric.todolist.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "checklist", schema = "public")
public class Checklist {
    
    @Id
    @Column(name = "checklist_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String name;
    
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id", nullable = false)
    private Users users;

    @JsonIgnore
    @OneToMany(mappedBy = "checklist", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<ChecklistItem> checklistItems;
}

    package com.eric.todolist.model.entity;

    import com.fasterxml.jackson.annotation.JsonIgnore;
    import jakarta.persistence.*;
    import lombok.*;

    @Entity
    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    @Table(name = "checklist_item", schema = "public")
    public class ChecklistItem {

        @Id
        @Column(name = "checklist_item_id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        private String itemName;
        private boolean completed;

        @ManyToOne
        @JsonIgnore
        @JoinColumn(name = "checklist_id", nullable = false)
        private Checklist checklist;
    }

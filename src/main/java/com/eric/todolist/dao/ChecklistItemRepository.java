package com.eric.todolist.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eric.todolist.model.ChecklistItem;

public interface ChecklistItemRepository extends JpaRepository<ChecklistItem, Integer>{
    
}

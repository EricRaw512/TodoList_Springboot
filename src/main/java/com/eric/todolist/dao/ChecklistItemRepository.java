package com.eric.todolist.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eric.todolist.entity.ChecklistItem;

public interface ChecklistItemRepository extends JpaRepository<ChecklistItem, Integer>{

    List<ChecklistItem> findAllByChecklistId(int checklistId);
}

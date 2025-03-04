package com.eric.todolist.repository;

import com.eric.todolist.model.entity.ChecklistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ChecklistItemRepository extends JpaRepository<ChecklistItem, Integer>, JpaSpecificationExecutor<ChecklistItem> {

    List<ChecklistItem> findAllByChecklistId(int checklistId);
}

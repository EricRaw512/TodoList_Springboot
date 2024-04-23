package com.eric.todolist.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eric.todolist.model.Checklist;

public interface ChecklistRepository extends JpaRepository<Checklist, Integer>{

    List<Checklist> findAllByUserId(int id);
    
}

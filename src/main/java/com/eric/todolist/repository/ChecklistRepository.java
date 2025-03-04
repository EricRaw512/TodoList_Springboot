package com.eric.todolist.repository;

import com.eric.todolist.model.entity.Checklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ChecklistRepository extends JpaRepository<Checklist, Integer>, JpaSpecificationExecutor<Checklist> {

    List<Checklist> findAllByUsersId(int id);
    
}

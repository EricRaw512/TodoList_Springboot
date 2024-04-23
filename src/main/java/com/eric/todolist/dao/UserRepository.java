package com.eric.todolist.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eric.todolist.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{
    
    Optional<User> findByUsername(String username);
}

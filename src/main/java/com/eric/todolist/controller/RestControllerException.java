package com.eric.todolist.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.eric.todolist.exception.ChecklistException;

@RestControllerAdvice
public class RestControllerException {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodNotValieException(MethodArgumentNotValidException e) {
        StringBuilder strBuilder = new StringBuilder();

        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName;
            try {
                fieldName = ((FieldError) error).getField();

            } catch (ClassCastException ex) {
                fieldName = error.getObjectName();
            }
            String message = error.getDefaultMessage();
            strBuilder.append(String.format("%s: %s /", fieldName, message));
        });

        return new ResponseEntity<>(strBuilder.toString(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ChecklistException.class)
    public ResponseEntity<String> handleChecklistException(ChecklistException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
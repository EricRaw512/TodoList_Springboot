package com.eric.todolist.exception;

public class UserAlreadyExistException extends RuntimeException {
    
    public UserAlreadyExistException(String message) {
        super(message);
    }
}

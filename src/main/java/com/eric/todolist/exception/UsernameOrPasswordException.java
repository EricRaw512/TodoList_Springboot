package com.eric.todolist.exception;

public class UsernameOrPasswordException extends RuntimeException{
    
    public UsernameOrPasswordException(String message) {
        super(message);
    }
    
}

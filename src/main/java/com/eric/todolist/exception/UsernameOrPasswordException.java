package com.eric.todolist.exception;

public class UsernameOrPasswordException extends RuntimeException{
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 7425291821577136917L;

	public UsernameOrPasswordException(String message) {
        super(message);
    }
    
}

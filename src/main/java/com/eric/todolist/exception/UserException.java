package com.eric.todolist.exception;

public class UserException extends RuntimeException {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 8892203441468756195L;

	public UserException(String message) {
        super(message);
    }
}

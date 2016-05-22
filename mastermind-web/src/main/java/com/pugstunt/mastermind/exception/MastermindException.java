package com.pugstunt.mastermind.exception;

public class MastermindException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private String message;
	
	public MastermindException() {
	}
	
	public MastermindException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
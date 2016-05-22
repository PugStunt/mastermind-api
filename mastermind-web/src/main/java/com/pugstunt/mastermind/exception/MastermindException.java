package com.pugstunt.mastermind.exception;

import javax.ws.rs.WebApplicationException;

public class MastermindException extends WebApplicationException {

	private static final long serialVersionUID = 1L;

	private String message;
	
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
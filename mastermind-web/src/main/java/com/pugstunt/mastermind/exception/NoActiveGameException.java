package com.pugstunt.mastermind.exception;

public class NoActiveGameException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NoActiveGameException() {
		super("No active game");
	}
	
}

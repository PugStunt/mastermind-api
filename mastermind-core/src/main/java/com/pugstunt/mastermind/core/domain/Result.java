package com.pugstunt.mastermind.core.domain;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(NON_NULL)
public class Result {

	@JsonProperty("exact")
	private int exact;
	
	@JsonProperty("near")
	private int near;
	
	@JsonProperty("guess")
	private String guess;
	
	public int getExact() {
		return exact;
	}

	public void setExact(int exact) {
		this.exact = exact;
	}

	public int getNear() {
		return near;
	}

	public void setNear(int near) {
		this.near = near;
	}

	public String getGuess() {
		return guess;
	}

	public void setGuess(String guess) {
		this.guess = guess;
	}
	
}
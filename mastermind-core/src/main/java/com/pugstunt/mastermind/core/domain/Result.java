package com.pugstunt.mastermind.core.domain;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(NON_NULL)
public class Result {

	@JsonProperty("exact")
	private Integer exact;
	
	@JsonProperty("near")
	private Integer near;
	
	@JsonProperty("guess")
	private String guess;
	
	public Integer getExact() {
		return exact;
	}

	public void setExact(Integer exact) {
		this.exact = exact;
	}

	public Integer getNear() {
		return near;
	}

	public void setNear(Integer near) {
		this.near = near;
	}

	public String getGuess() {
		return guess;
	}

	public void setGuess(String guess) {
		this.guess = guess;
	}
	
}
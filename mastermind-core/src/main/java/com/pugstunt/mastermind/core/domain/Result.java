package com.pugstunt.mastermind.core.domain;

import java.util.ArrayList;
import java.util.List;

public class Result {

	private int exact;
	
	private int near;
	
	private List<Character> guess = new ArrayList<>();
	
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

	public List<Character> getGuess() {
		return guess;
	}

	public void setGuess(List<Character> guess) {
		this.guess = guess;
	}
	
}
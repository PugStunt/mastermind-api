package com.pugstunt.mastermind.core.entity;

import java.io.Serializable;

public class PastResult implements Serializable {

	private static final long serialVersionUID = -7656840151183583368L;

	private int exact;
	private String guess;
	private int near;

	private PastResult() {
		super();
	}
	
	public int getExact() {
		return exact;
	}

	public String getGuess() {
		return guess;
	}

	public int getNear() {
		return near;
	}

	public static Builder builder() {
		return new Builder();
	}
	
	public static class Builder {
	
		private int exact;
		private String guess;
		private int near;
		
		private Builder() {
			super();
		}
		
		public Builder exact(int value) {
			exact = value;
			return this;
		}
		
		public Builder guess(String value) {
			guess = value;
			return this;
		}
		
		public Builder near(int value) {
			near = value;
			return this;
		}
		
		public PastResult build() {
			PastResult pastResult = new PastResult();
			pastResult.exact = exact;
			pastResult.guess = guess;
			pastResult.near = near;
			return pastResult;
		}
		
	}
	
}
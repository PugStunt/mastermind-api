package com.pugstunt.mastermind.core.domain;

import java.util.ArrayList;
import java.util.List;

public class GameResponse {

	private int codeLength;
	
	private List<Character> colors = new ArrayList<>();
	
	private String furtherInstructions;
	
	private String gameKey;
	
	private List<Character> guess = new ArrayList<>();
	
	private int numGuesses;
	
	private List<Result> pastResults = new ArrayList<>();
	
	private Result result;
	
	private boolean solved;
	
	private float timeTaken;
	
	private String user;

	public int getCodeLength() {
		return codeLength;
	}

	public void setCodeLength(int codeLength) {
		this.codeLength = codeLength;
	}

	public List<Character> getColors() {
		return colors;
	}

	public void setColors(List<Character> colors) {
		this.colors = colors;
	}

	public String getFurtherInstructions() {
		return furtherInstructions;
	}

	public void setFurtherInstructions(String furtherInstructions) {
		this.furtherInstructions = furtherInstructions;
	}

	public String getGameKey() {
		return gameKey;
	}

	public void setGameKey(String gameKey) {
		this.gameKey = gameKey;
	}

	public List<Character> getGuess() {
		return guess;
	}

	public void setGuess(List<Character> guess) {
		this.guess = guess;
	}

	public int getNumGuesses() {
		return numGuesses;
	}

	public void setNumGuesses(int numGuesses) {
		this.numGuesses = numGuesses;
	}

	public List<Result> getPastResults() {
		return pastResults;
	}

	public void setPastResults(List<Result> pastResults) {
		this.pastResults = pastResults;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public boolean isSolved() {
		return solved;
	}

	public void setSolved(boolean solved) {
		this.solved = solved;
	}

	public float getTimeTaken() {
		return timeTaken;
	}

	public void setTimeTaken(float timeTaken) {
		this.timeTaken = timeTaken;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	
	
	
}

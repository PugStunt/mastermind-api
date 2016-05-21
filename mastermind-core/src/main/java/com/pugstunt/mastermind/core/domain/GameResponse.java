package com.pugstunt.mastermind.core.domain;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(NON_NULL)
public class GameResponse {

	@JsonProperty("code_length")
	private Integer codeLength;
	
	@JsonProperty("colors")
	private List<Character> colors;
	
	@JsonProperty("further_instructions")
	private String furtherInstructions;
	
	@JsonProperty("game_key")
	private String gameKey;
	
	@JsonProperty("guess")
	private List<Character> guess;
	
	@JsonProperty("num_guesses")
	private Integer numGuesses;
	
	@JsonProperty("past_results")
	private List<Result> pastResults;
	
	@JsonProperty("result")
	private Object result;
	
	@JsonProperty("solved")
	private Boolean solved;
	
	@JsonProperty("time_taken")
	private Float timeTaken;
	
	@JsonProperty("user")
	private String user;
	
	@JsonProperty("start_time")
	private Date startTime;

	public Integer getCodeLength() {
		return codeLength;
	}

	public void setCodeLength(Integer codeLength) {
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

	public Integer getNumGuesses() {
		return numGuesses;
	}

	public void setNumGuesses(Integer numGuesses) {
		this.numGuesses = numGuesses;
	}

	public List<Result> getPastResults() {
		return pastResults;
	}

	public void setPastResults(List<Result> pastResults) {
		this.pastResults = pastResults;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public Boolean isSolved() {
		return solved;
	}

	public void setSolved(Boolean solved) {
		this.solved = solved;
	}

	public Float getTimeTaken() {
		return timeTaken;
	}

	public void setTimeTaken(Float timeTaken) {
		this.timeTaken = timeTaken;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	public Date getStartTime() {
		return startTime;
	}
}

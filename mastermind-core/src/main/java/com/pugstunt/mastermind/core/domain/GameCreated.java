package com.pugstunt.mastermind.core.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "GameCreated", description = "GameCreated resource representation")
public class GameCreated {

	@ApiModelProperty(value = "List containing all possibles color codes to use on guesses")
	@JsonProperty("colors")
	private List<Character> colors;
	
	@ApiModelProperty(value = "Answer length")
	@JsonProperty("code_length")
	private Integer codeLength;
	
	@ApiModelProperty(value = "Player gameKey")
	@JsonProperty("game_key")
	private String gameKey;
	
	@ApiModelProperty(value = "Number of player guesses")
	@JsonProperty("num_guesses")
	private Integer numGuesses;
	
	@ApiModelProperty(value = "Player results historic")
	@JsonProperty("past_results")
	private List<Result> pastResults;
	
	@ApiModelProperty(value = "Indicates whether the game instance has been solved")
	@JsonProperty("solved")
	private Boolean solved;

	public List<Character> getColors() {
		return colors;
	}

	public void setColors(List<Character> colors) {
		this.colors = colors;
	}

	public Integer getCodeLength() {
		return codeLength;
	}

	public void setCodeLength(Integer codeLength) {
		this.codeLength = codeLength;
	}

	public String getGameKey() {
		return gameKey;
	}

	public void setGameKey(String gameKey) {
		this.gameKey = gameKey;
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

	public Boolean getSolved() {
		return solved;
	}

	public void setSolved(Boolean solved) {
		this.solved = solved;
	}
	
}

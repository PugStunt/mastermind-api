package com.pugstunt.mastermind.core.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "WrongGuess", description = "WrongGuess resource representation")
@Data
public class WrongGuess {

	@ApiModelProperty(value = "Answer length")
	@JsonProperty("code_length")
	private Integer codeLength;
	
	@ApiModelProperty(value = "List containing all possibles color codes to use on guesses")
	@JsonProperty("colors")
	private List<Character> colors;
	
	@ApiModelProperty(value = "Player gameKey")
	@JsonProperty("game_key")
	private String gameKey;
	
	@ApiModelProperty(value = "Wrong guess")
	@JsonProperty("guess")
	private List<Character> guess;
	
	@ApiModelProperty(value = "Number of guesses")
	@JsonProperty("num_guesses")
	private Integer numGuesses;
	
	@ApiModelProperty(value = "Player results historic")
	@JsonProperty("past_results")
	private List<Result> pastResults;
	
	@ApiModelProperty(value = "Result")
	@JsonProperty("result")
	private Object result;

	@ApiModelProperty(value = "Indicates whether the game instance has been solved")
	@JsonProperty("solved")
	private Boolean solved;

}

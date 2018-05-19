package com.pugstunt.mastermind.core.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "GameCreated", description = "GameCreated resource representation")
@Data
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
	
}

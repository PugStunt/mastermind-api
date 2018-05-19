package com.pugstunt.mastermind.core.domain;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "SolvedGame", description = "SolvedGame resource representation")
@Data
@JsonInclude(NON_NULL)
public class SolvedGame {

	@ApiModelProperty(value = "Answer length")
	@JsonProperty("code_length")
	private Integer codeLength;
	
	@ApiModelProperty(value = "List containing all possibles color codes to use on guesses")
	@JsonProperty("colors")
	private List<Character> colors;
	
	@ApiModelProperty(value = "Player gameKey")
	@JsonProperty("game_key")
	private String gameKey;
	
	@ApiModelProperty(value = "Right guess")
	@JsonProperty("guess")
	private List<Character> guess;
	
	@ApiModelProperty(value = "Number of guesses")
	@JsonProperty("num_guesses")
	private Integer numGuesses;
	
	@ApiModelProperty(value = "Player results historic")
	@JsonProperty("past_results")
	private List<Result> pastResults;
	
	@ApiModelProperty(value = "You Win!")
	@JsonProperty("result")
	private Object result;
	
	@ApiModelProperty(value = "Indicates whether the game instance has been solved")
	@JsonProperty("solved")
	private Boolean solved;
	
	@ApiModelProperty(value = "Further instructions")
	@JsonProperty("further_instructions")
	private String furtherInstructions;

	@ApiModelProperty(value = "Time taken to solve the game")
	@JsonProperty("time_taken")
	private Float timeTaken;
	
	@ApiModelProperty(value = "Player name")
	@JsonProperty("user")
	private String user;
	
	@ApiModelProperty(value = "Start time timestamp")
	@JsonProperty("start_time")
	private Date startTime;

}

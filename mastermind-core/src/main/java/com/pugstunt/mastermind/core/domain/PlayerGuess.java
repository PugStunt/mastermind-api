package com.pugstunt.mastermind.core.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "PlayerGuess", description = "PlayerGuess resource representation")
@Data
public class PlayerGuess {

	@ApiModelProperty(value = "Sequence of characters representating player guess")
	@JsonProperty("code")
	private String code;
	
	@ApiModelProperty(value = "Player gameKey", required = true)
	@JsonProperty("game_key")
	private String gameKey;

}
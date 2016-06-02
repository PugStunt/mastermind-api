package com.pugstunt.mastermind.core.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "PlayerGuess", description = "PlayerGuess resource representation")
public class PlayerGuess {

	@ApiModelProperty(value = "Sequence of characters representating player guess")
	@JsonProperty("code")
	private String code;
	
	@ApiModelProperty(value = "Player gameKey", required = true)
	@JsonProperty("game_key")
	private String gameKey;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getGameKey() {
		return gameKey;
	}

	public void setGameKey(String gameKey) {
		this.gameKey = gameKey;
	}
}
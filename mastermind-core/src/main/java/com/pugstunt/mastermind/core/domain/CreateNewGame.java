package com.pugstunt.mastermind.core.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "CreateNewGame", description = "Request resource to start a new game")
public class CreateNewGame {
	
	@ApiModelProperty(value = "Player name", required = true)
	@JsonProperty("user")
	private String user;
	
	public CreateNewGame() {
		super();
	}
	
	public CreateNewGame(String user) {
		this.user = user;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	
}

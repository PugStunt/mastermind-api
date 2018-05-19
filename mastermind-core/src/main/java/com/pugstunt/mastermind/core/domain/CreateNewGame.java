package com.pugstunt.mastermind.core.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@ApiModel(value = "CreateNewGame", description = "Request resource to start a new game")
@Data
@AllArgsConstructor
public class CreateNewGame {
	
	@ApiModelProperty(value = "Player name", required = true)
	@JsonProperty("user")
	private String user;
	
}

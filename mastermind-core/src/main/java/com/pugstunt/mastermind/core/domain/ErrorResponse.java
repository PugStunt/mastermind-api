package com.pugstunt.mastermind.core.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ErrorResponse {
	
	@JsonProperty("status")
	private String status;
	
	@JsonProperty("text")
	private String text;
	
}
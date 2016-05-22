package com.pugstunt.mastermind.core.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NewGameRequest {
	
	@JsonProperty("user")
	private String user;
	
	public NewGameRequest() {
	}
	
	public NewGameRequest(String user) {
		this.user = user;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	
}

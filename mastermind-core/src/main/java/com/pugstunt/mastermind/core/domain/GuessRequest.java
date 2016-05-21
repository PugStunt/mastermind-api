package com.pugstunt.mastermind.core.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GuessRequest {

	@JsonProperty("code")
	private List<Character> code = new ArrayList<>();
	
	@JsonProperty("game_key")
	private String gameKey;

	public List<Character> getCode() {
		return code;
	}

	public void setCode(List<Character> code) {
		this.code = code;
	}

	public String getGameKey() {
		return gameKey;
	}

	public void setGameKey(String gameKey) {
		this.gameKey = gameKey;
	}
}
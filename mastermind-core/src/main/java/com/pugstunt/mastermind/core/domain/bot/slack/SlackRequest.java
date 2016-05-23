package com.pugstunt.mastermind.core.domain.bot.slack;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SlackRequest {
	
	private String token;

	@JsonProperty("type")
	private String type;
	
	@JsonProperty("team")
	private String teamId;

	@JsonProperty("channel")
	private String channelId;

	@JsonProperty("ts")
	private String timestamp;

	@JsonProperty("user")
	private String userId;

	@JsonProperty("text")
	private String text;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getKeyBase() {
		return getUserId() + getChannelId() + getTeamId();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}

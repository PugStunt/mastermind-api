package com.pugstunt.mastermind.core.domain.bot.slack;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SlackRequest {
	
	private String token;

	@JsonProperty("team_id")
	private String teamId;

	@JsonProperty("team_domain")
	private String teamDomain;

	@JsonProperty("channel_id")
	private String channelId;

	@JsonProperty("channel_name")
	private String channelName;

	private String timestamp;

	@JsonProperty("user_id")
	private String userId;

	@JsonProperty("user_name")
	private String username;

	private String text;

	@JsonProperty("trigger_word")
	private String triggerWord;

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

	public String getTeamDomain() {
		return teamDomain;
	}

	public void setTeamDomain(String teamDomain) {
		this.teamDomain = teamDomain;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getText() {
		return text;
	}

	public String getTextWithouTrigger() {
		if(triggerWord != null && text.indexOf(triggerWord) == 0){
			return text.substring(triggerWord.length());
		}
		
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getTriggerWord() {
		return triggerWord;
	}

	public void setTriggerWord(String triggerWord) {
		this.triggerWord = triggerWord;
	}

	public String getKeyBase() {
		return getUserId() + getChannelId() + getTeamId();
	}

}

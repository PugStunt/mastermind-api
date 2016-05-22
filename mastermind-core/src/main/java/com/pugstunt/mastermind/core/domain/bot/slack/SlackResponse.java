package com.pugstunt.mastermind.core.domain.bot.slack;

public class SlackResponse {
	
	private final String text;
	
	public SlackResponse(final String text){
		this.text = text;
	}
	
	public String getText() {
		return text;
	}

}

package com.pugstunt.mastermind.core.domain.bot.slack;

public class SlackResponse {

	private String text;
	
	public static SlackResponse error(String text){
		SlackResponse response = new SlackResponse();		
		response.setText(text);
		return response;
	}

	public static SlackResponse info(String text) {
		SlackResponse response = new SlackResponse();		
		response.text = text;
		return response;
	}

	public static SlackResponse success(String text) {
		SlackResponse response = new SlackResponse();		
		response.text = text;
		return response;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}

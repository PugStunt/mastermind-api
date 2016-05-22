package com.pugstunt.mastermind.core.domain.bot.slack;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SlackResponseAttachment {
	public static final String RED = "danger";
	public static final String ORANGE = "warning";
	public static final String GREEN = "good";
	public static final String BLUE = "#006699";
	
	@JsonProperty("color")
	private String color;
	
	@JsonProperty("title")
	private String title;
	
	@JsonProperty("text")
	private String text;
	
	@JsonProperty("image_url")
	private String imageUrl;

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public static SlackResponseAttachment info(String text){		
		SlackResponseAttachment attachment = new SlackResponseAttachment();
		attachment.setText(text);
		attachment.setColor(BLUE);
		
		return attachment;
	}
	
	public static SlackResponseAttachment error(String text){		
		SlackResponseAttachment attachment = new SlackResponseAttachment();
		attachment.setText(text);
		attachment.setColor(RED);
		
		return attachment;
	}

	public static SlackResponseAttachment success(String text) {		
		SlackResponseAttachment attachment = new SlackResponseAttachment();
		attachment.setText(text);
		attachment.setColor(GREEN);
		
		return attachment;
	}

}

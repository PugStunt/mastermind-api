package com.pugstunt.mastermind.core.domain.bot.slack;

import java.util.ArrayList;
import java.util.List;

public class SlackResponse {
	
	private final List<SlackResponseAttachment> attachments = new ArrayList<>();

	public List<SlackResponseAttachment> getAttachments() {
		return attachments;
	}
	
	public static SlackResponse error(String text){
		SlackResponse response = new SlackResponse();		
		response.getAttachments().add(SlackResponseAttachment.error(text));
		
		return response;
	}

	public static SlackResponse info(String text) {
		SlackResponse response = new SlackResponse();		
		response.getAttachments().add(SlackResponseAttachment.info(text));
		
		return response;
	}

	public static SlackResponse success(String text) {
		SlackResponse response = new SlackResponse();		
		response.getAttachments().add(SlackResponseAttachment.success(text));
		
		return response;
	}

}

package com.pugstunt.mastermind.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.google.common.base.Charsets;
import com.google.inject.Inject;
import com.pugstunt.mastermind.core.domain.bot.slack.SlackRequest;
import com.pugstunt.mastermind.core.domain.bot.slack.SlackResponse;
import com.pugstunt.mastermind.service.bot.slack.handler.SlackHandlerFactory;

public class SlackService {
	
	private final SlackHandlerFactory handlerFactory;
	
	@Inject
	public SlackService(SlackHandlerFactory handlerFactory) {
		this.handlerFactory = handlerFactory;		
	}

	public SlackResponse botSays(SlackRequest request) {
		
		String message = request.getTextWithouTrigger();		
		return handlerFactory.getHandlerFor(message).apply(request);
	}

	public String buildKey(String userId, String channelId, String teamId) {
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			return new String(digest.digest((userId + channelId + teamId).getBytes()), Charsets.UTF_8);
		} catch (NoSuchAlgorithmException ex) {
			throw new RuntimeException(ex);
		}
	}
	
}

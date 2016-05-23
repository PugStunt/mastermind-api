package com.pugstunt.mastermind.service;

import static java.lang.String.valueOf;

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
		
		String message = request.getText();
		
		return handlerFactory.getHandlerFor(valueOf(message)).apply(request);
	}

}

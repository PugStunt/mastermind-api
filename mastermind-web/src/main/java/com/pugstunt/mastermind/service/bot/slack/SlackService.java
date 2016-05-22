package com.pugstunt.mastermind.service.bot.slack;

import com.google.inject.Inject;
import com.pugstunt.mastermind.core.domain.bot.slack.SlackRequest;
import com.pugstunt.mastermind.core.domain.bot.slack.SlackResponse;
import com.pugstunt.mastermind.service.bot.slack.handler.SlackHadlerFactory;

public class SlackService {
	
	private final SlackHadlerFactory handlerFactory;
	
	@Inject
	public SlackService(SlackHadlerFactory handlerFactory) {
		this.handlerFactory = handlerFactory;		
	}

	public SlackResponse botSays(SlackRequest request) {
		
		String message = request.getTextWithouTrigger();		
		return handlerFactory.getHandlerFor(message).apply(request);
	}

}
